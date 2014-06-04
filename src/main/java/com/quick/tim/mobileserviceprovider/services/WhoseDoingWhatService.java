/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;

import com.quick.tim.mobileserviceprovider.entity.Whoisdoingwhat;
import com.quick.tim.mobileserviceprovider.DAO.WhoseDoingWhatDao;
import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.entity.Std;
import com.quick.tim.mobileserviceprovider.entity.Sub;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author suyogn
 */
@Component
public class WhoseDoingWhatService {
    
    @Autowired
    WhoseDoingWhatDao whoseDoingWhatDao;
    
       public List<MasteParmBean> getWhoIsDoingWhat(String forStd, String forDiv, boolean isAdmin,int fetchResultsFrom)
     {
         List<MasteParmBean> whoisdoingwhats = whoseDoingWhatDao.getWhoIsDoingWhat(forStd, forDiv,isAdmin,fetchResultsFrom);
         
         return whoisdoingwhats;
     }

    public void sendWhosDoingWhatNotificationToStudents(JSONObject inputRequest) throws JSONException 
    {
       
            Whoisdoingwhat w = new Whoisdoingwhat();
            w.setUploadId(inputRequest.getInt("uploadId"));
            w.setActivitydate(new Date());
            w.setBywhom(inputRequest.getString("name"));
            w.setFordiv(inputRequest.getString("div"));
            w.setStd(new Std(inputRequest.getString("std")));
            w.setSub(new Sub(inputRequest.getString("sub")));
            w.setTopic(inputRequest.getString("topic"));
            
            String activity=inputRequest.getString("doingwhat");
            
            w.setDoingwhat(activity);
            String displayNotification=GlobalConstants.EMPTY_STRING;
            if(activity.equals(GlobalConstants.going_through))
            {
                  displayNotification=inputRequest.getString("name") + GlobalConstants.space +GlobalConstants.is + GlobalConstants.space + GlobalConstants.going_through + GlobalConstants.space + inputRequest.getString("topic") + GlobalConstants.space + GlobalConstants.topicInformation;
            }
            else
            {
                displayNotification=inputRequest.getString("name") + GlobalConstants.space +GlobalConstants.is + GlobalConstants.space + GlobalConstants.going_through + GlobalConstants.space + activity +  GlobalConstants.space +GlobalConstants.in + GlobalConstants.space+ inputRequest.getString("topic");
            }
            w.setDisplaynotification(displayNotification);
            
            String topicInro= inputRequest.getString("topicIntro");
            if(topicInro.length()>Integer.parseInt(GlobalConstants.getProperty(GlobalConstants.TOPIC_INTRO_LENGTH))) {
                topicInro=topicInro.substring(0, Integer.parseInt(GlobalConstants.getProperty(GlobalConstants.TOPIC_INTRO_LENGTH)))+GlobalConstants.tripple_dots;
            }
            w.setTopicintro(topicInro);
            w.setClassToInvoke(inputRequest.getString("classToInvoke"));
            w.setVideoUrl(inputRequest.getString("videoUrl"));
            
            whoseDoingWhatDao.sendWhosDoingWhatNotificationToStudents(w);
       
    }
}
