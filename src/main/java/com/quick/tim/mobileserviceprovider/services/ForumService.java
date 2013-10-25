/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;

import com.quick.tim.mobileserviceprovider.DAO.ForumDao;
import com.quick.tim.mobileserviceprovider.entity.Whatsnew;
import com.quick.tim.mobileserviceprovider.DAO.WhatsNewDao;
import com.quick.tim.mobileserviceprovider.bean.EventCommentsBean;
import com.quick.tim.mobileserviceprovider.bean.EventLikeBean;
import com.quick.tim.mobileserviceprovider.bean.ForumEventDetailsBean;
import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.entity.*;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
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
       public List<ForumEventDetailsBean> getForumEventDetails(JSONObject inputRequest) throws JSONException
       {
           List<ForumEventDetailsBean> list = forumDao.getForumEventDetails(inputRequest);
           for(ForumEventDetailsBean bean:list)
           {
               bean.setStringImage(new String(Base64.encode(bean.getEventImage())));
           }
           return list;
       }
       
       public List<EventLikeBean> getEventLikesById(JSONObject inputRequest) throws JSONException
       {
           List<EventLikeBean> list = forumDao.getEventLikesById(inputRequest);

           return list;
       }
       
       public List<EventCommentsBean> getEventCommentsById(JSONObject inputRequest) throws JSONException
       {
           List<EventCommentsBean> list = forumDao.getEventCommentsById(inputRequest);

           return list;
       }

    public void saveEventDetails(JSONObject inputRequest) throws JSONException 
    {
        ForumEventDetails event = new ForumEventDetails();
        event.setEventDate(new Date());
        event.setEventOwner(inputRequest.getString("owner"));
        event.setEventDesc(inputRequest.getString("event_desc"));
        event.setEventBody(inputRequest.getString("event_body"));
        String imageStr=inputRequest.getString("image");
        byte[] arr=Base64.decode(imageStr);
        event.setEventImage(arr);
        event.setImageFilename(inputRequest.getString("image_filename"));
                
        forumDao.saveEventDetails(event);
    }
    
    
    public void saveEventLike(JSONObject inputRequest) throws JSONException 
    {
        ForumEventLikes eventLike = new ForumEventLikes();
        eventLike.setName(inputRequest.getString("name"));
        eventLike.setLikeTime(new Date());
        eventLike.setId(new ForumEventLikesId(inputRequest.getInt("event_id"), inputRequest.getString("username")));
                
        forumDao.saveEventLike(eventLike);
    }
}