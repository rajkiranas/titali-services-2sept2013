/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;

import com.quick.tim.mobileserviceprovider.DAO.ForumDao;
import com.quick.tim.mobileserviceprovider.entity.Whatsnew;
import com.quick.tim.mobileserviceprovider.DAO.WhatsNewDao;
import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.entity.ForumEventDetails;
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
public class ForumService 
{
    @Autowired
    ForumDao forumDao;
    
      /** 
       * Send whats new notifications for students after save or edit of topic
       */
       public List<ForumEventDetails> getForumEventDetails(JSONObject inputRequest) throws JSONException
       {
           return forumDao.getForumEventDetails(inputRequest);
       }

    public void saveEventDetails(JSONObject inputRequest) throws JSONException 
    {
        ForumEventDetails event = new ForumEventDetails();
        event.setEventDate(new Date());
        event.setEventOwner(inputRequest.getString("owner"));
        event.setEventDesc(inputRequest.getString("event_desc"));
        event.setEventBody(inputRequest.getString("event_body"));
        byte[] arr=inputRequest.getString("image").getBytes();
        event.setEventImage(arr);
        event.setImageFileName(inputRequest.getString("image_filename"));
                
        forumDao.saveEventDetails(event);
    }
}
