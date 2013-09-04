/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
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
@Path(GlobalConstants.WhatsNewResource)
public class WhatsNewResource {
    
    private static final String sendWhatsNewNotificationToStudents="sendWhatsNewNotificationToStudents";
    
    @Autowired
    private  WhatsNewService whatsNewService;;
    
    
    @Path(sendWhatsNewNotificationToStudents)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void sendWhatsNewNotificationToStudents(JSONObject inputRequest) throws JSONException {

        //System.out.println("userTrack="+inputRequest);

        whatsNewService.sendWhatsNewNotificationToStudents(inputRequest);
            
//     Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();       
//     String json = gson.toJson(uploadList);             
//     
//     JSONObject response =  new JSONObject();
//     response.put(GlobalConstants.QUICKLEARNLIST, json);     
//     return response;
    }

    void deleteWhatsNewNotification(int uploadId) {
        whatsNewService.deleteWhatsNewNotification(uploadId);
    }
    
}
