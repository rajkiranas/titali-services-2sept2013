/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.entity.*;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import com.quick.tim.mobileserviceprovider.services.EmailService;
import com.quick.tim.mobileserviceprovider.services.QuickLearnService;
import com.sun.jersey.api.core.ResourceContext;
import java.util.Date;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author COMPUTER1
 */
@Component
@Path(GlobalConstants.QuickLearnResource)
public class QuickLearnResource {
    @Autowired
    QuickLearnService quickLearnService;
    @Autowired
    EmailService emailService;
    private static final String getVideo = "getVideo";
    private static final String getNotes = "getnotes";
    private static final String getOtherNotes = "getOtherNotes";
    private static final String getPrevQue = "getPrevQue";
    private static final String myShortNotes = "saveMyShortNotes";
    private static final String whatsNewforme = "whatsNewforme";
    private static final String getstudQuickLearnDetails = "quickLearn";
    private static final String saveQuickUploadDetails="saveQuickUploadDetails";
    private static final String getquickLearnByUploadId="getquickLearnByUploadId";
    private static final String deleteTopicByUploadId="deleteTopicByUploadId";
    
    
    @Context
    private ResourceContext resourceContext;

    
    
    
    
    @Path(whatsNewforme)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getwhatsNewforme(JSONObject inputRequest) throws JSONException {


        System.out.println("userTrack=" + inputRequest);
        JSONObject response = new JSONObject();
        //org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        List<MasteParmBean> list = quickLearnService.getWhatsNewForMe(inputRequest.getString("subject"),inputRequest.getInt("fetchResultsFrom"));
        
        Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
        String json = gson.toJson(list);
        response.put(GlobalConstants.WHATSNEW, json);
        
       

        return response;
    }
    
   
    @Path(getstudQuickLearnDetails)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getQuickLearnByID(JSONObject inputRequest) throws JSONException {


        System.out.println("userTrack=" + inputRequest);
        JSONObject response = new JSONObject();
        //org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        List<QuickLearn> list = quickLearnService.getQuickLearnByID(inputRequest.getInt("uploadId"));
        Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
        String json = gson.toJson(list);  
        response.put(GlobalConstants.QUICKLEARNLIST, json);
        response.put(GlobalConstants.MYQUICKNOTEs, getMyQuickNotes(inputRequest.getInt("uploadId"),inputRequest.getString("userName")));
        return response;
    }
   
    
      private String getMyQuickNotes(int uploadId, String userName) {
        return quickLearnService.getMyQuickNotesById(uploadId,userName);
       
    }
      
      
   @Path(myShortNotes)
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public JSONObject  saveMyShortNotes(JSONObject inputRequest) throws JSONException {
        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();
        //inputRequest.getString("notes")
        QuickNotes quickNotes = new QuickNotes();
        quickNotes.setUserNotes(inputRequest.getString("userNotes"));
        quickNotes.setId(new QuickNotesId(inputRequest.getString("userName"), inputRequest.getInt("uploadId")));
        quickLearnService.saveMyShortNotes(quickNotes);        
        response.put(GlobalConstants.STATUS,"updated Successfully");                   
        return response;       
    } 
   
   @Path(saveQuickUploadDetails)
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public JSONObject  saveQuickUploadDetails(JSONObject inputRequest) throws JSONException {
        System.out.println("userTrack saveQuickUploadDetails() ="+inputRequest);
        JSONObject response =  new JSONObject();
        
        try 
        {
            
                //fetching data from input json        
            QuickLearn quickLearn=new QuickLearn(); 
            String uploadId=inputRequest.getString("uploadId");

            //edit upload
            if(!uploadId.equals("null")){
                 quickLearn.setUploadId(Integer.parseInt(uploadId));
            }
    //        // new upload - id not need from max as sequence is applied
    //        else{
    //             quickLearn.setUploadId(getMaxUplaodId());
    //        }

            String uploadedBy=inputRequest.getString("uploadedBy"); 
            String info=quickLearn.getUploadId()+"-"+uploadedBy;
            quickLearn.setStd(getuploadStd(inputRequest));
            quickLearn.setSub(getUploadSub(inputRequest));
            quickLearn.setTopic(inputRequest.getString("topic"));
            quickLearn.setTopicTags(inputRequest.getString("tags"));

            quickLearn.setUploadDate(new Date());
            quickLearn.setLectureNotes(inputRequest.getString("notes"));
            quickLearn.setLectureNotesInformation(inputRequest.getString("topicIntro"));
            quickLearn.setOtherNotes(inputRequest.getString("othernotes"));
            quickLearn.setOtherNotesInformation(uploadedBy);
            quickLearn.setPreviousQuestion(inputRequest.getString("pq"));
            quickLearn.setPreviousQuestionInformation(info);
            quickLearn.setQuiz(inputRequest.getString("quiz"));
            String videoPath= inputRequest.getString("video_path");
            quickLearn.setVideoPath(videoPath);



            //calling service for saving details
            quickLearnService.saveQuickUploadDetails(quickLearn);        

            response.put(GlobalConstants.STATUS,GlobalConstants.YES);
            

            //setting newly sequence generated upload id - so that whats new item id will be same as upload id
            inputRequest.put("uploadId",quickLearn.getUploadId());
            sendWhatsNewNotificationToStudents(inputRequest);

            //sending mail alert only for new topic releases
            if(uploadId.equals("null")){
                sendEmailNotificationToTheClass(quickLearn);
                response.put("newlyCreatedUploadId",quickLearn.getUploadId());
            }
           
       } 
        catch (Exception e) 
        {
            e.printStackTrace();
            response.put(GlobalConstants.STATUS,GlobalConstants.NO);
       }
        return response;
    }
   
   private void sendWhatsNewNotificationToStudents(JSONObject inputRequest)
    {
        try 
        {
            //fetch dashboard of the user
         WhatsNewResource resource = resourceContext.getResource(WhatsNewResource.class);
         resource.sendWhatsNewNotificationToStudents(inputRequest);
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
         
    }
   
   
   private Std getuploadStd(JSONObject inputRequest) {
        Std s=new Std();
        try {           
            s.setStd(inputRequest.getString("std"));             
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
         return s;
    }

    private Sub getUploadSub(JSONObject inputRequest) {
        Sub s=new Sub();
        try {           
            s.setSub(inputRequest.getString("sub"));             
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
         return s;
    }
    
    @Path(getquickLearnByUploadId)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getquickLearnByUploadId(JSONObject inputRequest) throws JSONException {


        System.out.println("userTrack=" + inputRequest);
        JSONObject response = new JSONObject();
        //org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        List<MasteParmBean> list = quickLearnService.getquickLearnByUploadId(inputRequest.getInt("uploadId"));
        Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
        String json = gson.toJson(list);  
        response.put(GlobalConstants.QUICKLEARNLIST, json);
       // response.put(GlobalConstants.MYQUICKNOTEs, getMyQuickNotes(inputRequest.getInt("uploadId")));
        return response;
    }
    
    
    private int getMaxUplaodId() {
       return quickLearnService.getMaxUplaodId();
    }
   
    
   @Path(deleteTopicByUploadId)
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public JSONObject  deleteTopicByUploadId(JSONObject inputRequest) throws JSONException {
        System.out.println("userTrack deleteTopicByUploadId() ="+inputRequest);
        JSONObject response =  new JSONObject();
        
        String uploadId=inputRequest.getString("uploadId");
        
        
        //deleting topic by upload id
        if(!uploadId.equals("null")){
             QuickLearn quickLearn=new QuickLearn(); 
             quickLearn.setUploadId(Integer.parseInt(uploadId));
             quickLearnService.deleteTopicByUploadId(quickLearn);
             response.put(GlobalConstants.STATUS,GlobalConstants.YES);
             
             deleteWhatsNewNotification(quickLearn.getUploadId());
             deleteWhoIsDoingWhatNotifications(quickLearn.getUploadId());
        }
        // cannot delete topic
        else{
             response.put(GlobalConstants.STATUS,GlobalConstants.NO);
        }      
        return response;
    }
    
   
    private void deleteWhatsNewNotification(int uploadId) {
        try 
        {
            //fetch dashboard of the user
         WhatsNewResource resource = resourceContext.getResource(WhatsNewResource.class);
         resource.deleteWhatsNewNotification(uploadId);
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
         
        
    }
    
    private void deleteWhoIsDoingWhatNotifications(int uploadId) 
    {
        try 
        {
            //fetch dashboard of the user
         WhatsNewResource resource = resourceContext.getResource(WhatsNewResource.class);
         resource.deleteWhoIsDoingWhatNotifications(uploadId);
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
//    @Path(getVideo)
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public JSONObject getVideo(JSONObject inputRequest) throws JSONException {
//
//
//        System.out.println("userTrack=" + inputRequest);
//        JSONObject response = new JSONObject();
//        //org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
//        List<QuickLearn> list = quickLearnService.getVideoDetailsByID(inputRequest.getInt("upload_id"));
//
//        return response;
//    }
//    
    
//    @Path(getNotes)
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public JSONObject getNotes(JSONObject inputRequest) throws JSONException {
//
//
//       System.out.println("userTrack=" + inputRequest);
//       JSONObject response = new JSONObject();
//       List<QuickLearn> list = quickLearnService.getVideoDetailsByID(inputRequest.getInt("upload_id"));
//
//        return response;
//    }
//    
//    @Path(getOtherNotes)
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public JSONObject getOtherNotes(JSONObject inputRequest) throws JSONException {
//
//
//        System.out.println("userTrack=" + inputRequest);
//       JSONObject response = new JSONObject();
//       List<QuickLearn> list = quickLearnService.getVideoDetailsByID(inputRequest.getInt("upload_id"));
//
//        return response;
//    }
//    
//    @Path(getPrevQue)
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public JSONObject getPrevQue(JSONObject inputRequest) throws JSONException {
//
//         System.out.println("userTrack=" + inputRequest);
//       JSONObject response = new JSONObject();
//       List<QuickLearn> list = quickLearnService.getVideoDetailsByID(inputRequest.getInt("upload_id"));
//
//        return response;
//    }

    private void sendEmailNotificationToTheClass(QuickLearn quickLearn) 
    {
        emailService.sendNewTopicNotificationByMail(quickLearn);
    }
}
