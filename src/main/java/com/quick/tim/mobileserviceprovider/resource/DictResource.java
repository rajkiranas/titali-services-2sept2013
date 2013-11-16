/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quick.tim.mobileserviceprovider.bean.DictWordDetailsBean;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import com.quick.tim.mobileserviceprovider.services.DictService;
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
@Path(GlobalConstants.DictResource)
public class DictResource {
    
    private static final String getDictWordList="getDictWordList";    
    private static final String saveNewWordDetails="saveNewWordDetails";
    private static final String searchWordList="searchWordList";
    
    @Autowired
    private  DictService dictService;;
    
    @Path(getDictWordList)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getDictWordList(JSONObject inputRequest) throws JSONException {

        List<DictWordDetailsBean> eventDetailsList = dictService.getDictWordList(inputRequest);
        
        Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
        String examListJson = gson.toJson(eventDetailsList);  
        
        JSONObject response = new JSONObject();
        response.put(GlobalConstants.dictWordList, examListJson);     
        return response;
    }
    
    
    @Path(saveNewWordDetails)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject saveNewWordDetails(JSONObject inputRequest) throws JSONException 
    {
        JSONObject response = new JSONObject();
        try 
        {
            dictService.saveNewWordDetails(inputRequest);
            response.put(GlobalConstants.STATUS, "Successfully saved word");
            
        } catch (Exception e) 
        {
            e.printStackTrace();
            response.put(GlobalConstants.STATUS, "Could not save word");
        }
        
        return response;
    }
    
    
    @Path(searchWordList)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject searchWordList(JSONObject inputRequest) throws JSONException {

        List<DictWordDetailsBean> eventDetailsList = dictService.searchWordList(inputRequest);
        
        Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
        String examListJson = gson.toJson(eventDetailsList);  
        
        JSONObject response = new JSONObject();
        response.put(GlobalConstants.dictWordList, examListJson);     
        return response;
    }
}
