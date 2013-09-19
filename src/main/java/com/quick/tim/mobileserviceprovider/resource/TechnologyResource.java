/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.bean.NoticeBean;
import com.quick.tim.mobileserviceprovider.bean.UpcomingTechnologyBean;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import com.quick.tim.mobileserviceprovider.services.NoticeBoardService;
import com.quick.tim.mobileserviceprovider.services.QuickService;
import com.quick.tim.mobileserviceprovider.services.TechnologyService;
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
@Path(GlobalConstants.technologyResource)
public class TechnologyResource {
    
    private static final String getAllTechnology="getAllTechnology";
    private static final String saveTechnology="saveTechnology";
    private static final String deleteTechnology ="deleteTechnology";
    
    @Autowired
    private  TechnologyService technologyService;
    
    
    @Path(getAllTechnology)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getAllTechnology(JSONObject inputRequest) throws JSONException {

        JSONObject response =  new JSONObject();
            List<UpcomingTechnologyBean>  technologiesList=  technologyService.getAllTechnologies();
            Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
            String json = gson.toJson(technologiesList);             
            response.put(GlobalConstants.Technologies, json);     
            return response;
    }
    
    @Path(saveTechnology)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject saveTechnology(JSONObject inputRequest) throws JSONException {

        JSONObject response = new JSONObject();


        try {
            System.out.println("userTrack=" + inputRequest);
            technologyService.saveTechnology(inputRequest);
            response.put(GlobalConstants.STATUS, GlobalConstants.YES);


        } catch (Exception e) {
            e.printStackTrace();
            response.put(GlobalConstants.STATUS, GlobalConstants.NO);

        }
        return response;
    }
    
      
    @Path(deleteTechnology)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteTechnology(JSONObject inputRequest) throws JSONException {

        JSONObject response = new JSONObject();


        try {
            System.out.println("userTrack=" + inputRequest);
            technologyService.deleteTechnology(inputRequest);
            response.put(GlobalConstants.STATUS, GlobalConstants.YES);

        } catch (Exception e) {
            e.printStackTrace();
            response.put(GlobalConstants.STATUS, GlobalConstants.NO);

        }
        return response;
    }
    
}
