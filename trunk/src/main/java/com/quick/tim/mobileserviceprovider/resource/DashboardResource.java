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
import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.entity.*;
import com.quick.tim.mobileserviceprovider.services.NoticeBoardService;
import com.quick.tim.mobileserviceprovider.services.WhatsNewService;
import com.quick.tim.mobileserviceprovider.services.WhoseDoingWhatService;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
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
    private static final String getStudentDashboard="getStudentDashboard";
    private static final String getWhatsNewBySub="getWhatsNewBysubject";
    
    @Path(getStudentDashboard)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getStudentDashboard(JSONObject userProfile) throws JSONException {

        JSONObject response = new JSONObject();
        
        List<Whatsnew> list = whatsNewService.getWhatsNewForMe(userProfile.getString("standard"), userProfile.getString("division"));
        //Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();       
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();       
        String json = gson.toJson(list);
        response.put(GlobalConstants.WHATSNEW, json);

        /*
         * whose doing what
         */

        
        List<MasteParmBean> whoisdoingwhats = whoseDoingWhatService.getWhoIsDoingWhat(userProfile.getString("standard"), userProfile.getString("division"));
        Gson Whoisdoingwhat_gson = new Gson();

        String Whoisdoingwhat_json = Whoisdoingwhat_gson.toJson(whoisdoingwhats);
        response.put(GlobalConstants.WHOSEDOINGWHAT, Whoisdoingwhat_json);


      
        List<Notices> noticeses = noticeBoardService.getNotices(userProfile.getString("standard"), userProfile.getString("division"));
        Gson Notices_gson = new Gson();
        String Notices_json = Notices_gson.toJson(noticeses);
        response.put(GlobalConstants.NOTICES, Notices_json);

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
