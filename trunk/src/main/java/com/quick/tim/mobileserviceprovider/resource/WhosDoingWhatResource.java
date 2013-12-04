/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.resource;

import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import com.quick.tim.mobileserviceprovider.services.WhoseDoingWhatService;
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
@Path(GlobalConstants.WhosDoingWhatResource)
public class WhosDoingWhatResource {
    
    private static final String sendWhosDoingWhatNotificationToStudents="sendWhosDoingWhatNotificationToStudents";
    
    @Autowired
    private  WhoseDoingWhatService whoseDoingWhatService;
    
    
    @Path(sendWhosDoingWhatNotificationToStudents)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject sendWhosDoingWhatNotificationToStudents(JSONObject inputRequest){
        JSONObject response = new JSONObject();
        try {
            //System.out.println("userTrack="+inputRequest);

            whoseDoingWhatService.sendWhosDoingWhatNotificationToStudents(inputRequest);
                response.put(GlobalConstants.STATUS, GlobalConstants.YES);
    //     Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
    //     String json = gson.toJson(uploadList);             
    //     
    //     JSONObject response =  new JSONObject();
    //     response.put(GlobalConstants.QUICKLEARNLIST, json);     
    //     return response;
        } catch (JSONException ex) {
            ex.printStackTrace();
            try {
                response.put(GlobalConstants.STATUS, GlobalConstants.NO);
            } catch (JSONException ex1) {
                ex.printStackTrace();
            }
           
        }
        return response;
    }

    /* void deleteWhatsNewNotification(int uploadId) {
        whoseDoingWhatService.deleteWhatsNewNotification(uploadId);
    } */
    
}
