/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author prashantk
 */
package com.quick.tim.mobileserviceprovider.resource;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quick.tim.mobileserviceprovider.bean.DictWordDetailsBean;
import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.entity.*;
import com.quick.tim.mobileserviceprovider.services.NoticeBoardService;
import com.quick.tim.mobileserviceprovider.services.WhatsNewService;
import com.quick.tim.mobileserviceprovider.services.WhoseDoingWhatService;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import com.quick.tim.mobileserviceprovider.services.DictService;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
@Path(GlobalConstants.dashborad_resource)
public class DashboardResource {

    @Autowired
    private  WhatsNewService whatsNewService;
    @Autowired
    WhoseDoingWhatService whoseDoingWhatService;
    @Autowired
    NoticeBoardService noticeBoardService;
    @Autowired
    DictService dictService;
    
    
    private static final String getStudentDashboard="getStudentDashboard";
    private static final String getWhatsNewBySub="getWhatsNewBysubject";
    
    @Path(getStudentDashboard)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getStudentDashboard(JSONObject userProfile) throws JSONException {

        JSONObject response = new JSONObject();
        String std=GlobalConstants.EMPTY_STRING;
        String div=GlobalConstants.EMPTY_STRING;
        boolean isAdmin=false;
        
        if(userProfile.has("standard"))
        {
            std=userProfile.getString("standard");
        }
        else
        {
            isAdmin=true;
        }
        
        if(userProfile.has("division"))
        {
            div=userProfile.getString("division");
        }
        
        /* List<Whatsnew> list = whatsNewService.getWhatsNewForMe(std, div,isAdmin);
        //Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
        Gson gson = new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
        String json = gson.toJson(list);
        response.put(GlobalConstants.WHATSNEW, json); */

        /*
         * whose doing what
         */


        int fetchResultsFrom=userProfile.getInt("fetchResultsFrom");
        List<MasteParmBean> whoisdoingwhats = whoseDoingWhatService.getWhoIsDoingWhat(std, div,isAdmin,fetchResultsFrom);
        Gson Whoisdoingwhat_gson = new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();

        String Whoisdoingwhat_json = Whoisdoingwhat_gson.toJson(whoisdoingwhats);
        response.put(GlobalConstants.WHOSEDOINGWHAT, Whoisdoingwhat_json);


      
        List<Notices> noticeses = noticeBoardService.getNotices(std, div);
        Gson Notices_gson = new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();
        String Notices_json = Notices_gson.toJson(noticeses);
        response.put(GlobalConstants.NOTICES, Notices_json);
        
        
        List<DictWordDetailsBean> wordOfTheDayList =dictService.getWordOfTheDay(userProfile);
        Gson wordOfTheDay_gson = new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();
        String wordOfTheDay_json = wordOfTheDay_gson.toJson(wordOfTheDayList);
        response.put(GlobalConstants.wordOfTheDay, wordOfTheDay_json);

        return response;
    }
    
    
    //=========================suyog  ============================================
    @Path(getWhatsNewBySub)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getWhatsNewBysubject(JSONObject userProfile) throws JSONException {

        JSONObject response = new JSONObject();
        List<MasteParmBean> list = whatsNewService.getWhatsNewForMe(userProfile.getString("subject"));
        Gson gson = new Gson();
        String json = gson.toJson(list);
        response.put(GlobalConstants.WHATSNEW, json);
        
        return response;
    }
}
