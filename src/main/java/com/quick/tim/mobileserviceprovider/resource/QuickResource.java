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
@Path(GlobalConstants.QuickResource)
public class QuickResource {
    
    private static final String getQuickLearnUploadList="getQuickLearnUploadList";
    
    @Autowired
    private  QuickService quickService;;
    
    
    @Path(getQuickLearnUploadList)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getQuickLearnUploadList(JSONObject inputRequest) throws JSONException {


        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();
      
            
            List<MasteParmBean>  uploadList=  quickService.getQuickLearnUploadList(inputRequest);
            Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
            String json = gson.toJson(uploadList);             
            response.put(GlobalConstants.QUICKLEARNLIST, json);     
            return response;
    }
    
}
