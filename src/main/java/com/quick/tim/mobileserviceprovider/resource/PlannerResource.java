/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quick.tim.mobileserviceprovider.bean.AppointmentMstBean;
import com.quick.tim.mobileserviceprovider.bean.DictWordDetailsBean;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import com.quick.tim.mobileserviceprovider.services.DictService;
import com.quick.tim.mobileserviceprovider.services.PlannerService;
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
@Path(GlobalConstants.PlannerResource)
public class PlannerResource {
    
    private static final String getEventList="getEventList";    
    private static final String saveEvent="saveEvent";
    private static final String deleteEvent="deleteEvent";
    private static final String searchWordList="searchWordList";
    
    @Autowired
    private  PlannerService plannerService;;
    
    @Path(getEventList)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getEventList(JSONObject inputRequest) throws JSONException {

        List<AppointmentMstBean> eventDetailsList = plannerService.getEventList(inputRequest);
        
        Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
        String eventListJson = gson.toJson(eventDetailsList);  
        
        JSONObject response = new JSONObject();
        response.put(GlobalConstants.eventList, eventListJson);     
        return response;
    }
    
    
    @Path(saveEvent)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject saveEvent(JSONObject inputRequest) throws JSONException 
    {
        JSONObject response = new JSONObject();
        try 
        {
            plannerService.saveEvent(inputRequest);
            response.put(GlobalConstants.STATUS, "Successfully saved event");
            
        } catch (Exception e) 
        {
            e.printStackTrace();
            response.put(GlobalConstants.STATUS, "Could not save event");
        }
        
        return response;
    }
    
    @Path(deleteEvent)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteEvent(JSONObject inputRequest) throws JSONException 
    {
        JSONObject response = new JSONObject();
        try 
        {
            plannerService.deleteEvent(inputRequest);
            response.put(GlobalConstants.STATUS, "Successfully deleted event");
            
        } catch (Exception e) 
        {
            e.printStackTrace();
            response.put(GlobalConstants.STATUS, "Could not delete event");
        }
        
        return response;
    }
    
    
    @Path(searchWordList)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject searchWordList(JSONObject inputRequest) throws JSONException {

        List<DictWordDetailsBean> eventDetailsList = plannerService.searchWordList(inputRequest);
        
        Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
        String examListJson = gson.toJson(eventDetailsList);  
        
        JSONObject response = new JSONObject();
        response.put(GlobalConstants.dictWordList, examListJson);     
        return response;
    }
}
