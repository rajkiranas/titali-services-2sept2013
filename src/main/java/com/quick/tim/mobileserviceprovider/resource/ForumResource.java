/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quick.tim.mobileserviceprovider.bean.EventCommentsBean;
import com.quick.tim.mobileserviceprovider.bean.EventLikeBean;
import com.quick.tim.mobileserviceprovider.bean.ForumEventDetailsBean;
import com.quick.tim.mobileserviceprovider.entity.ForumEventComments;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import com.quick.tim.mobileserviceprovider.services.EmailService;
import com.quick.tim.mobileserviceprovider.services.ForumService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Sonali Sangle
 */
@Component
@Path(GlobalConstants.ForumResource)
public class ForumResource {
    
    private static final String getForumEventDetails="getForumEventDetails";
    private static final String saveEventDetails="saveEventDetails";
    private static final String saveEventLike="saveEventLike";
    private static final String saveEventComment="saveEventComment";
    private static final String getEventLikesById="getEventLikesById";
    
    @Autowired
    private  ForumService forumService;;
    @Autowired
    private EmailService emailService;
    
    
    @Path(getForumEventDetails)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getForumEventDetails(JSONObject inputRequest) throws JSONException {

        List<ForumEventDetailsBean> eventDetailsList = forumService.getForumEventDetails(inputRequest);
        
        Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
        String examListJson = gson.toJson(eventDetailsList);  
        
        JSONObject response = new JSONObject();
        response.put(GlobalConstants.eventDetailsList, examListJson);     
        return response;
    }
    
    @Path(saveEventDetails)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject saveEventDetails(JSONObject inputRequest) throws JSONException 
    {
        JSONObject response = new JSONObject();
        try 
        {
            forumService.saveEventDetails(inputRequest);
            response.put(GlobalConstants.STATUS, "Successfully posted event");
            emailService.sendNewForumEventNotificationByMailToAll(inputRequest);
            
        } catch (Exception e) 
        {
            e.printStackTrace();
            response.put(GlobalConstants.STATUS, "Could not post event");
        }
        
        return response;
    }
    
    @Path(saveEventLike)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject saveEventLike(JSONObject inputRequest) throws JSONException 
    {
        JSONObject response = new JSONObject();
        try 
        {
            forumService.saveEventLike(inputRequest);
        
            response.put(GlobalConstants.STATUS, "Successfully liked event");     
            
        } catch (Exception e) 
        {
            e.printStackTrace();
            response.put(GlobalConstants.STATUS, "Could not like event");
        }
        
        return response;
    }
    
     @Path(saveEventComment)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject saveEventComment(JSONObject inputRequest) throws JSONException 
    {
        JSONObject response = new JSONObject();
        try 
        {
            ForumEventComments eventComments= forumService.saveEventComment(inputRequest);
        
            response.put(GlobalConstants.STATUS, "Successfully posted comment");     
            
            List<EventCommentsBean> eventCommentsList = forumService.getEventCommentsById(inputRequest);
            emailService.sendCommentPostedMailToRelatedUsers(eventCommentsList,eventComments,inputRequest);
        } catch (Exception e) 
        {
            e.printStackTrace();
            response.put(GlobalConstants.STATUS, "Could not post comment");
        }
        
        return response;
    }
    
    @Path(getEventLikesById)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getEventLikesById(JSONObject inputRequest) throws JSONException {

        //get likes
        List<EventLikeBean> eventLikesList = forumService.getEventLikesById(inputRequest);
        
        Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
        String eventLikeJson = gson.toJson(eventLikesList);
        
        JSONObject response = new JSONObject();
        response.put(GlobalConstants.eventLikes, eventLikeJson);
        
        
        //get comments
        List<EventCommentsBean> eventCommentsList = forumService.getEventCommentsById(inputRequest);
        
        Gson gson2=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
        String eventCommentsJson = gson2.toJson(eventCommentsList);
        
        response.put(GlobalConstants.eventComments, eventCommentsJson);
        
        return response;
    }
}
