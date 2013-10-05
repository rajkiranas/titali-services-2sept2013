/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.entity.ForumEventDetails;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import com.quick.tim.mobileserviceprovider.services.ForumService;
import com.quick.tim.mobileserviceprovider.services.QuickService;
import com.quick.tim.mobileserviceprovider.services.WhatsNewService;
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
    
    @Autowired
    private  ForumService forumService;;
    
    
    @Path(getForumEventDetails)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getForumEventDetails(JSONObject inputRequest) throws JSONException {

        List<ForumEventDetails> eventDetailsList = forumService.getForumEventDetails(inputRequest);
        
        Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
        String examListJson = gson.toJson(eventDetailsList);  
        
        JSONObject response = new JSONObject();
        response.put(GlobalConstants.eventDetailsList, examListJson);     
        return response;
    }
}