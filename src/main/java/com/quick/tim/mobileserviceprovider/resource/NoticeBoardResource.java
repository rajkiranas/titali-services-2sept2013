/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quick.tim.mobileserviceprovider.bean.NoticeBean;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import com.quick.tim.mobileserviceprovider.services.EmailService;
import com.quick.tim.mobileserviceprovider.services.NoticeBoardService;
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
 * @author rajkirans
 */
@Component
@Path(GlobalConstants.noticeBoardResource)
public class NoticeBoardResource {
      private static final String getAllNotices="getAllNotices";
      private static final String saveNotice="saveNotice";
       private static final String deleteNotice ="deleteNotice";
    
    @Autowired
    private  NoticeBoardService noticeBoardService;
    @Autowired
    private EmailService emailService;
    
    
    @Path(getAllNotices)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getAllNotices(JSONObject inputRequest) throws JSONException {


      //  System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();
            List<NoticeBean>  noticeList=  noticeBoardService.getAllNotices();
            Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
            String json = gson.toJson(noticeList);             
            response.put(GlobalConstants.NOTICES, json);     
            return response;
    }
    
    
    
    
    
     
    @Path(saveNotice)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject saveNotice(JSONObject inputRequest) throws JSONException {

        JSONObject response = new JSONObject();


        try {
            System.out.println("userTrack=" + inputRequest);
            noticeBoardService.saveNotice(inputRequest);
            response.put(GlobalConstants.STATUS, GlobalConstants.YES);
            int noticeId=inputRequest.getInt("noticeId");
            if(noticeId==0)
            {
                emailService.sendNewNoticeByMailToAll(inputRequest);
            }


        } catch (Exception e) {
            e.printStackTrace();
            response.put(GlobalConstants.STATUS, GlobalConstants.NO);

        }
        return response;
    }
    
    
    
    
      
    @Path(deleteNotice)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteNotice(JSONObject inputRequest) throws JSONException {

        JSONObject response = new JSONObject();


        try {
            System.out.println("userTrack=" + inputRequest);
            noticeBoardService.deleteNotice(inputRequest);
            response.put(GlobalConstants.STATUS, GlobalConstants.YES);

        } catch (Exception e) {
            e.printStackTrace();
            response.put(GlobalConstants.STATUS, GlobalConstants.NO);

        }
        return response;
    }
    
}
