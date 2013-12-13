/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.resource;

import com.quick.tim.mobileserviceprovider.entity.ForumEventDetails;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import com.quick.tim.mobileserviceprovider.services.WhatsNewService;
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
@Path(GlobalConstants.WhatsNewResource)
public class WhatsNewResource {
    
    private static final String sendWhatsNewNotificationToStudents="sendWhatsNewNotificationToStudents";
    private static final String sendForumNotificationToStudents="sendForumNotificationToStudents";
    
    @Autowired
    private  WhatsNewService whatsNewService;;
    
    
    @Path(sendWhatsNewNotificationToStudents)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void sendWhatsNewNotificationToStudents(JSONObject inputRequest) throws JSONException {

        //System.out.println("userTrack="+inputRequest);

        whatsNewService.sendWhatsNewNotificationToStudents(inputRequest);
            
//     Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
//     String json = gson.toJson(uploadList);             
//     
//     JSONObject response =  new JSONObject();
//     response.put(GlobalConstants.QUICKLEARNLIST, json);     
//     return response;
    }
    
    @Path(sendForumNotificationToStudents)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void sendForumNotificationToStudents(ForumEventDetails event,JSONObject inputRequest) throws JSONException {

        whatsNewService.sendForumNotificationToStudents(event,inputRequest);
            
    }

    void deleteWhatsNewNotification(int uploadId) {
        whatsNewService.deleteWhatsNewNotification(uploadId);
    }

    void deleteWhoIsDoingWhatNotifications(int uploadId) {
        whatsNewService.deleteWhoIsDoingWhatNotifications(uploadId);
    }
    
}
