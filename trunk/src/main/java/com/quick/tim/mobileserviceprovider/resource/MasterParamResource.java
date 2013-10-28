/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.resource;

import com.google.gson.Gson;
import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.bean.QuickLearn;
import com.quick.tim.mobileserviceprovider.entity.Std;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import com.quick.tim.mobileserviceprovider.services.MasterResourceService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author suyogn
 */
@Component
@Path(GlobalConstants.MasterParamResource)
public class MasterParamResource {
    
    
       private static final String getSubjectBystd="getSubjectBystd";
       private static final String standardList="standardList";
       private static final String getDivisionBystd="getDivisionBystd";
       private static final String stdsub="stdsub";
    
    
    @Autowired
    private MasterResourceService masterResourceService;
    
     //private Person person = new Person(1, "Sample", "Person", "sample_person@jerseyrest.com");
    // The @Context annotation allows us to have certain contextual objects
    // injected into this class.
    // UriInfo object allows us to get URI information (no kidding).
    @Context
    UriInfo uriInfo;
    // Another "injected" object. This allows us to use the information that's
    // part of any incoming request.
    // We could, for example, get header information, or the requestor's address.
    @Context
    Request request;
    
    /// ================== SUyog=====================================================
    @Path(stdsub)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getStandardWiseSubject(JSONObject inputRequest) throws JSONException {
        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();
        List<MasteParmBean> list =  masterResourceService.getStandardWisesubjects(inputRequest.getString("standard"));
        Gson gson = new Gson();
        String json = gson.toJson(list);
        response.put(GlobalConstants.STDSUBLIST, json);
        return response;
    }

    
    
    //=========================== sonali============================================================s
   @Path(getSubjectBystd)
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public JSONObject getSubjectBystd(JSONObject inputRequest) throws JSONException {
        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();
       
        String std=String.valueOf(inputRequest.getString("std"));
        List<QuickLearn> subjectList=masterResourceService.getSubjectBystd(std);
        Gson gson = new Gson();   
        String json = gson.toJson(subjectList);  
        response.put(GlobalConstants.subjectList, json);     
        return response;       
    }
   
   
    @Path(standardList)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject standardList(JSONObject inputRequest) throws JSONException {

        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();           
        List<Std>  list=  masterResourceService.getStandardList();
        Gson gson = new Gson();
        String json = gson.toJson(list);  
        response.put(GlobalConstants.STANDARDLIST, json);     
        return response;
    }
    
    
   @Path(getDivisionBystd)
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public JSONObject getDivisionBystd(JSONObject inputRequest) throws JSONException {
        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();
       
        String std=String.valueOf(inputRequest.getString("std"));
        List<QuickLearn> divList=masterResourceService.getDivisionBystd(std);
        Gson gson = new Gson();   
        String json = gson.toJson(divList);  
        response.put(GlobalConstants.divisionList, json);     
        return response;       
    }
    
      //=========================== sonali end============================================================s
    
}
