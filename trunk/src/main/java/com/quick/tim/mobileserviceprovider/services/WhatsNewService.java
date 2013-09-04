/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;

import com.quick.tim.mobileserviceprovider.entity.Whatsnew;
import com.quick.tim.mobileserviceprovider.DAO.WhatsNewDao;
import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.entity.Std;
import com.quick.tim.mobileserviceprovider.entity.Sub;
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
    
    public List<Whatsnew> getWhatsNewForMe(String forStd, String forDiv)
    {
         List<Whatsnew> whatsNewList = whatsNewDao.getWhatsNewForMe(forStd, forDiv);
         System.out.println("whatsNewList="+whatsNewList.get(0).getTopic());
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
            
           String displayNotification=GlobalConstants.EMPTY_STRING;
           
           if(isNewQuickUpload)
           {
               displayNotification=inputRequest.getString("uploadedBy") + GlobalConstants.space +GlobalConstants.has + GlobalConstants.space + GlobalConstants.released + GlobalConstants.space + inputRequest.getString("topic") + GlobalConstants.space + GlobalConstants.topicInformation;
           }
           else
           {
               displayNotification=inputRequest.getString("uploadedBy") + GlobalConstants.space +GlobalConstants.has + GlobalConstants.space + GlobalConstants.updated + GlobalConstants.space + inputRequest.getString("topic") + GlobalConstants.space + GlobalConstants.topicInformation;
           }
           
           Whatsnew wn = new Whatsnew();
           wn.setReleasedate(new Date());
           wn.setItemid(Integer.parseInt(inputRequest.getString("uploadId")));
           wn.setBywhom(inputRequest.getString("uploadedBy"));
           wn.setDisplaynotification(displayNotification);
           
           wn.setStd(new Std(inputRequest.getString("std")));
           wn.setSub(new Sub(inputRequest.getString("sub")));
           wn.setTopic(inputRequest.getString("topic"));
           
           whatsNewDao.sendWhatsNewNotificationToStudents(wn);
           
       } 

    public void deleteWhatsNewNotification(int uploadId) {
       whatsNewDao.deleteWhatsNewNotification(uploadId);
    }
    
}
