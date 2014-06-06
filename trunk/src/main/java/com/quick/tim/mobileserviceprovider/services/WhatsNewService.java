/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;

import com.quick.tim.mobileserviceprovider.entity.Whatsnew;
import com.quick.tim.mobileserviceprovider.DAO.WhatsNewDao;
import com.quick.tim.mobileserviceprovider.DAO.WhoseDoingWhatDao;
import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.entity.ForumEventDetails;
import com.quick.tim.mobileserviceprovider.entity.Std;
import com.quick.tim.mobileserviceprovider.entity.Sub;
import com.quick.tim.mobileserviceprovider.entity.Whoisdoingwhat;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import java.util.Date;
import java.util.List;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author rajkirans
 */
@Component
public class WhatsNewService 
{
    @Autowired
    WhatsNewDao whatsNewDao;
    
    @Autowired
    WhoseDoingWhatDao whoseDoingWhatDao;
    
    public List<Whatsnew> getWhatsNewForMe(String forStd, String forDiv, boolean isAdmin)
    {
         List<Whatsnew> whatsNewList = whatsNewDao.getWhatsNewForMe(forStd, forDiv, isAdmin);
        return whatsNewList;
    }
    
      public List<MasteParmBean> getWhatsNewForMe(String subject) {
         List<MasteParmBean> whatsNewList = whatsNewDao.getWhatsNewForMe(subject);
         return whatsNewList;
    }
      
      /** 
       * Send whats new notifications for students after save or edit of topic
       */
       public void sendWhatsNewNotificationToStudents(JSONObject inputRequest) throws JSONException
       {
           
           boolean isNewQuickUpload=(Boolean)inputRequest.get("isNewQuickUpload");
            System.out.println("###### chk this="+isNewQuickUpload);
            
           String displayNotification;
           
           if(isNewQuickUpload)
           {
               //displayNotification=inputRequest.getString("uploadedBy").toUpperCase() + GlobalConstants.space +GlobalConstants.has + GlobalConstants.space + GlobalConstants.released + GlobalConstants.space + inputRequest.getString("topic") + GlobalConstants.space + GlobalConstants.topicInformation;
               displayNotification=inputRequest.getString("uploadedBy").toUpperCase() + GlobalConstants.space +GlobalConstants.has + GlobalConstants.space + GlobalConstants.shared + GlobalConstants.space + inputRequest.getString("topic");
           }
           else
           {
               //displayNotification=inputRequest.getString("uploadedBy").toUpperCase() + GlobalConstants.space +GlobalConstants.has + GlobalConstants.space + GlobalConstants.updated + GlobalConstants.space + inputRequest.getString("topic") + GlobalConstants.space + GlobalConstants.topicInformation;
               displayNotification=inputRequest.getString("uploadedBy").toUpperCase() + GlobalConstants.space +GlobalConstants.has + GlobalConstants.space + GlobalConstants.updated + GlobalConstants.space + inputRequest.getString("topic");
           }
           
           Whoisdoingwhat wn = new Whoisdoingwhat();
           wn.setActivitydate(new Date());
           wn.setUploadId(Integer.parseInt(inputRequest.getString("uploadId")));
           wn.setBywhom(inputRequest.getString("uploadedBy"));
           wn.setDisplaynotification(displayNotification);
           
           wn.setStd(new Std(inputRequest.getString("std")));
           wn.setSub(new Sub(inputRequest.getString("sub")));
           wn.setTopic(inputRequest.getString("topic"));
           
           String topicInro= inputRequest.getString("topicIntro");
           if(topicInro.length()>Integer.parseInt(GlobalConstants.getProperty(GlobalConstants.TOPIC_INTRO_LENGTH))) {
               topicInro=topicInro.substring(0, Integer.parseInt(GlobalConstants.getProperty(GlobalConstants.TOPIC_INTRO_LENGTH)))+GlobalConstants.tripple_dots;
           }
           wn.setTopicintro(topicInro);
           wn.setClassToInvoke(inputRequest.getString("classToInvoke"));
           String videoPath= inputRequest.getString("video_path");
           wn.setVideoUrl(videoPath);
           //whatsNewDao.sendWhatsNewNotificationToStudents(wn);
           whoseDoingWhatDao.sendWhosDoingWhatNotificationToStudents(wn);
           
       } 
       private static final String Forum="Forum";
       public void sendForumNotificationToStudents(ForumEventDetails event,JSONObject inputRequest) throws JSONException
       {
           
           //boolean isNewQuickUpload=(Boolean)inputRequest.get("isNewQuickUpload");
           //System.out.println("###### chk this="+isNewQuickUpload);
            
           String displayNotification;
           
//           if(isNewQuickUpload)
//           {
//               //displayNotification=inputRequest.getString("uploadedBy").toUpperCase() + GlobalConstants.space +GlobalConstants.has + GlobalConstants.space + GlobalConstants.released + GlobalConstants.space + inputRequest.getString("topic") + GlobalConstants.space + GlobalConstants.topicInformation;
//               displayNotification=event.getEventOwner().toUpperCase() + GlobalConstants.space +GlobalConstants.has + GlobalConstants.space + GlobalConstants.shared + GlobalConstants.space + event.getEventDesc();
//           }
//           else
//           {
//               //displayNotification=inputRequest.getString("uploadedBy").toUpperCase() + GlobalConstants.space +GlobalConstants.has + GlobalConstants.space + GlobalConstants.updated + GlobalConstants.space + inputRequest.getString("topic") + GlobalConstants.space + GlobalConstants.topicInformation;
//               displayNotification=event.getEventOwner().toUpperCase() + GlobalConstants.space +GlobalConstants.has + GlobalConstants.space + GlobalConstants.updated + GlobalConstants.space + inputRequest.getString("topic");
//           }
           
           displayNotification=event.getEventOwner().toUpperCase() + GlobalConstants.space +GlobalConstants.has + GlobalConstants.space + GlobalConstants.shared + GlobalConstants.space + event.getEventDesc();
           
           Whoisdoingwhat wn = new Whoisdoingwhat();
           wn.setActivitydate(new Date());
           wn.setUploadId(event.getEventDetailId());
           wn.setBywhom(event.getEventOwner());
           wn.setDisplaynotification(displayNotification);
           
//           wn.setStd(new Std(inputRequest.getString("std")));
//           wn.setSub(new Sub(inputRequest.getString("sub")));
           wn.setTopic(Forum);
           
           String topicInro= event.getEventBody();
           if(topicInro.length()>Integer.parseInt(GlobalConstants.getProperty(GlobalConstants.TOPIC_INTRO_LENGTH))) {
               topicInro=topicInro.substring(0, Integer.parseInt(GlobalConstants.getProperty(GlobalConstants.TOPIC_INTRO_LENGTH)))+GlobalConstants.tripple_dots;
           }
           wn.setTopicintro(topicInro);
           wn.setClassToInvoke(inputRequest.getString("classToInvoke"));
           
           if(inputRequest.getString("videoUrl")!=null && !inputRequest.getString("videoUrl").equals("null"))
           {
                wn.setVideoUrl(inputRequest.getString("videoUrl"));
           }
           
           whoseDoingWhatDao.sendWhosDoingWhatNotificationToStudents(wn);
           
       } 

    public void deleteWhatsNewNotification(int uploadId) {
       whatsNewDao.deleteWhatsNewNotification(uploadId);
    }

    public void deleteWhoIsDoingWhatNotifications(int uploadId) {
        whatsNewDao.deleteWhoIsDoingWhatNotifications(uploadId);
    }
    
}
