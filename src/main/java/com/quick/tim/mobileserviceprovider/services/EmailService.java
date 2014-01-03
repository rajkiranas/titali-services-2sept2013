/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;


import com.quick.tim.mobileserviceprovider.bean.EventCommentsBean;
import com.quick.tim.mobileserviceprovider.bean.Userprofile;
import com.quick.tim.mobileserviceprovider.entity.ForumEventComments;
import com.quick.tim.mobileserviceprovider.entity.QuickLearn;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import com.quick.tim.mobileserviceprovider.utilities.SendMailSSL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author rajkiran
 */
@Component
public class EmailService {
    @Autowired
    private UserMasterService masterService;
    
    public void sendNewTopicNotificationByMail(QuickLearn quickLearn)
    {
        List<Userprofile> userProfileList = masterService.getStudentUserIdsByClass(quickLearn.getStd().getStd());
        StringBuilder toMailIds=new StringBuilder(GlobalConstants.EMPTY_STRING);
        StringBuilder toMobileNos=new StringBuilder(GlobalConstants.EMPTY_STRING);
        for(Userprofile profile: userProfileList)
        {
            toMailIds.append(profile.getUsername());
            toMailIds.append(GlobalConstants.COMMA);
            
            toMobileNos.append(profile.getMobile());
            toMobileNos.append(GlobalConstants.COMMA);
        }
        toMailIds.deleteCharAt(toMailIds.length()-1);
        String msgBody=GlobalConstants.getProperty(GlobalConstants.NEW_TOPIC_RELEASE_MSG);
        msgBody=msgBody.replaceAll("<topic>", quickLearn.getTopic());
        System.out.println("====="+toMailIds.toString()+" " +"New topic released-"+quickLearn.getTopic() +" "+msgBody);
        //SendMailSSL.sendMail(toMailIds.toString(),"New topic released-"+quickLearn.getTopic(),msgBody);
        
        //
        if(Boolean.valueOf(GlobalConstants.getProperty(GlobalConstants.ENABLE_MOBILE_ALERTS)))
        {
            toMobileNos.deleteCharAt(toMailIds.length()-1);
            //call gateway api
        }
    }
    
    public void sendNewExamNotificationByMail(JSONObject inputRequest) throws JSONException
    {
        List<Userprofile> userProfileList = masterService.getStudentUserIdsByClass(inputRequest.getString("std"));
        StringBuilder toMailIds=new StringBuilder(GlobalConstants.EMPTY_STRING);
        StringBuilder toMobileNos=new StringBuilder(GlobalConstants.EMPTY_STRING);
        for(Userprofile profile: userProfileList)
        {
            toMailIds.append(profile.getUsername());
            toMailIds.append(GlobalConstants.COMMA);
            
            toMobileNos.append(profile.getMobile());
            toMobileNos.append(GlobalConstants.COMMA);
        }
        toMailIds.deleteCharAt(toMailIds.length()-1);
        
        String msgBody=GlobalConstants.getProperty(GlobalConstants.NEW_EXAM_RELEASE_MSG);
        msgBody=msgBody.replaceAll("<teachername>", inputRequest.getString("createdBy"));
        msgBody=msgBody.replaceAll("<exam>", inputRequest.getString("exName"));
        msgBody=msgBody.replaceAll("<topic>", inputRequest.getString("topic"));
        msgBody=msgBody.replaceAll("<stdate>", inputRequest.getString("startDt"));
        msgBody=msgBody.replaceAll("<enddate>", inputRequest.getString("endDt"));
        System.out.println("====="+toMailIds.toString()+" " +"New exam released-"+inputRequest.getString("topic") +" "+msgBody);
        //SendMailSSL.sendMail(toMailIds.toString(),"New exam released on -"+inputRequest.getString("topic"),msgBody);
        
        
        //mobile alerts
        if(Boolean.valueOf(GlobalConstants.getProperty(GlobalConstants.ENABLE_MOBILE_ALERTS)))
        {
            toMobileNos.deleteCharAt(toMailIds.length()-1);
            //call gateway api
        }
    }
    
    public void sendNewTechnologyNotificationByMailToAll(JSONObject inputRequest) throws JSONException
    {
        List<Userprofile> userProfileList = masterService.getAllStudentUserIds();
        StringBuilder toMailIds=new StringBuilder(GlobalConstants.EMPTY_STRING);
        StringBuilder toMobileNos=new StringBuilder(GlobalConstants.EMPTY_STRING);
        for(Userprofile profile: userProfileList)
        {
            toMailIds.append(profile.getUsername());
            toMailIds.append(GlobalConstants.COMMA);
            
            toMobileNos.append(profile.getMobile());
            toMobileNos.append(GlobalConstants.COMMA);
        }
        toMailIds.deleteCharAt(toMailIds.length()-1);
        
        
        String msgBody=GlobalConstants.getProperty(GlobalConstants.NEW_TECHNOLOGY_RELEASE_MSG);
        msgBody=msgBody.replaceAll("<technology>", inputRequest.getString("technologyline"));
        msgBody=msgBody.replaceAll("<bywhom>", inputRequest.getString("bywhom"));
        msgBody=msgBody.replaceAll("<category>", inputRequest.getString("category"));
        
        System.out.println("====="+toMailIds.toString()+" " +"New technology released-"+inputRequest.getString("technologyline") +" "+msgBody);
        //SendMailSSL.sendMail(toMailIds.toString(),"New technology released -"+inputRequest.getString("technologyline"),msgBody);

        
        //mobile alerts
        if(Boolean.valueOf(GlobalConstants.getProperty(GlobalConstants.ENABLE_MOBILE_ALERTS)))
        {
            toMobileNos.deleteCharAt(toMailIds.length()-1);
            //call gateway api
        }
    }
    
    public void sendNewNoticeByMailToAll(JSONObject inputRequest) throws JSONException
    {
        List<Userprofile> userProfileList = masterService.getAllStudentUserIds();
        StringBuilder toMailIds=new StringBuilder(GlobalConstants.EMPTY_STRING);
        StringBuilder toMobileNos=new StringBuilder(GlobalConstants.EMPTY_STRING);
        
        for(Userprofile profile: userProfileList)
        {
            toMailIds.append(profile.getUsername());
            toMailIds.append(GlobalConstants.COMMA);
            
            toMobileNos.append(profile.getMobile());
            toMobileNos.append(GlobalConstants.COMMA);
        }
        
        toMailIds.deleteCharAt(toMailIds.length()-1);
        
        String msgBody=GlobalConstants.getProperty(GlobalConstants.NEW_NOTICE_RELEASE_MSG);
        
        /*
         notices.setBywhom(inputRequest.getString("bywhom"));
        notices.setNoticebody(inputRequest.getString("noticebody"));
        notices.setNoticeline(inputRequest.getString("noticeline"));
        notices.setNoticedate(new Date(inputRequest.getLong("noticedate")));
        
        */
        
        msgBody=msgBody.replaceAll("<noticeline>", inputRequest.getString("noticeline"));
        msgBody=msgBody.replaceAll("<bywhom>", inputRequest.getString("bywhom"));
        
        
        System.out.println("====="+toMailIds.toString()+" " +"New notice -"+inputRequest.getString("noticeline") +" "+msgBody);
        //SendMailSSL.sendMail(toMailIds.toString(),"New notice -"+inputRequest.getString("noticeline"),msgBody);
        
        //mobile alerts
        if(Boolean.valueOf(GlobalConstants.getProperty(GlobalConstants.ENABLE_MOBILE_ALERTS)))
        {
            toMobileNos.deleteCharAt(toMailIds.length()-1);
            //call gateway api
        }
    }
    
    public void sendNewForumEventNotificationByMailToAll(JSONObject inputRequest) throws JSONException
    {
        List<Userprofile> userProfileList = masterService.getAllStudentUserIds();
        StringBuilder toMailIds=new StringBuilder(GlobalConstants.EMPTY_STRING);
        StringBuilder toMobileNos=new StringBuilder(GlobalConstants.EMPTY_STRING);
        
        for(Userprofile profile: userProfileList)
        {
            toMailIds.append(profile.getUsername());
            toMailIds.append(GlobalConstants.COMMA);
            
            toMobileNos.append(profile.getMobile());
            toMobileNos.append(GlobalConstants.COMMA);
        }
        toMailIds.deleteCharAt(toMailIds.length()-1);
        
        String msgBody=GlobalConstants.getProperty(GlobalConstants.NEW_FORUM_EVENT_RELEASE_MSG);
        msgBody=msgBody.replaceAll("<owner>", inputRequest.getString("owner"));
        msgBody=msgBody.replaceAll("<event_desc>", inputRequest.getString("event_desc"));
        
        
        System.out.println("====="+toMailIds.toString()+" " +inputRequest.getString("owner") + " has posted new forum event - "+inputRequest.getString("event_desc") +" "+msgBody);
        //SendMailSSL.sendMail(toMailIds.toString(),inputRequest.getString("owner") + " posted new forum event - "+inputRequest.getString("event_desc"),msgBody);
        
        //mobile alerts
        if(Boolean.valueOf(GlobalConstants.getProperty(GlobalConstants.ENABLE_MOBILE_ALERTS)))
        {
            toMobileNos.deleteCharAt(toMailIds.length()-1);
            //call gateway api
        }
    }

    public void sendCommentPostedMailToRelatedUsers(List<EventCommentsBean> eventCommentsList,ForumEventComments eventComments,JSONObject inputRequest) 
    {
        HashMap<String,EventCommentsBean> relatedUsersMap = new HashMap<String,EventCommentsBean>();
        for (EventCommentsBean bean : eventCommentsList) 
        {
             relatedUsersMap.put(bean.getUsername(), bean);
        }
        
        Set<String> keySet = relatedUsersMap.keySet();
        Iterator<String> itr = keySet.iterator();
        String userNameKey=GlobalConstants.EMPTY_STRING;
        StringBuilder toMailIds=new StringBuilder(GlobalConstants.EMPTY_STRING);
        while(itr.hasNext())
        {
            userNameKey = itr.next();
            toMailIds.append(userNameKey);
            toMailIds.append(GlobalConstants.COMMA);
        }
        
        toMailIds.deleteCharAt(toMailIds.length()-1);
        System.out.println("====="+toMailIds.toString());
        String msgBody=GlobalConstants.getProperty(GlobalConstants.COMMENT_POSTED_MSG);
        msgBody=msgBody.replaceAll("<name>", eventComments.getName());
        try {
            msgBody=msgBody.replaceAll("<event_desc>", inputRequest.getString("event_desc"));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        
        
        System.out.println("====="+toMailIds.toString()+" " +eventComments.getName()+ " commented on your post" +" "+msgBody);
        //SendMailSSL.sendMail(toMailIds.toString(),eventComments.getName()+ " commented on your post",msgBody);
    }
}