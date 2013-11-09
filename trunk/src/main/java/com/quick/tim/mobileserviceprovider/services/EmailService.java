/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;


import com.quick.tim.mobileserviceprovider.bean.Userprofile;
import com.quick.tim.mobileserviceprovider.entity.QuickLearn;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import com.quick.tim.mobileserviceprovider.utilities.SendMailSSL;
import java.util.Date;
import java.util.List;
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
        for(Userprofile profile: userProfileList)
        {
            toMailIds.append(profile.getUsername());
        }
        String msgBody=GlobalConstants.NEW_TOPIC_RELEASE_MSG;
        msgBody=msgBody.replaceAll("<topic>", quickLearn.getTopic());
        System.out.println("====="+toMailIds.toString()+" " +"New topic released-"+quickLearn.getTopic() +" "+msgBody);
        //SendMailSSL.sendMail(toMailIds.toString(),"New topic released-"+quickLearn.getTopic(),msgBody);
    }
    
    public void sendNewExamNotificationByMail(JSONObject inputRequest) throws JSONException
    {
        List<Userprofile> userProfileList = masterService.getStudentUserIdsByClass(inputRequest.getString("std"));
        StringBuilder toMailIds=new StringBuilder(GlobalConstants.EMPTY_STRING);
        for(Userprofile profile: userProfileList)
        {
            toMailIds.append(profile.getUsername());
        }
        String msgBody=GlobalConstants.NEW_EXAM_RELEASE_MSG;
        msgBody=msgBody.replaceAll("<teachername>", inputRequest.getString("createdBy"));
        msgBody=msgBody.replaceAll("<exam>", inputRequest.getString("exName"));
        msgBody=msgBody.replaceAll("<topic>", inputRequest.getString("topic"));
        msgBody=msgBody.replaceAll("<stdate>", inputRequest.getString("startDt"));
        msgBody=msgBody.replaceAll("<enddate>", inputRequest.getString("endDt"));
        System.out.println("====="+toMailIds.toString()+" " +"New exam released-"+inputRequest.getString("topic") +" "+msgBody);
        //SendMailSSL.sendMail(toMailIds.toString(),"New exam released on -"+inputRequest.getString("topic"),msgBody);
    }
    
    public void sendNewTechnologyNotificationByMailToAll(JSONObject inputRequest) throws JSONException
    {
        List<Userprofile> userProfileList = masterService.getAllStudentUserIds();
        StringBuilder toMailIds=new StringBuilder(GlobalConstants.EMPTY_STRING);
        for(Userprofile profile: userProfileList)
        {
            toMailIds.append(profile.getUsername());
        }
        
        String msgBody=GlobalConstants.NEW_TECHNOLOGY_RELEASE_MSG;
        msgBody=msgBody.replaceAll("<technology>", inputRequest.getString("technologyline"));
        msgBody=msgBody.replaceAll("<bywhom>", inputRequest.getString("bywhom"));
        msgBody=msgBody.replaceAll("<category>", inputRequest.getString("category"));
        
        System.out.println("====="+toMailIds.toString()+" " +"New technology released-"+inputRequest.getString("topic") +" "+msgBody);
        //SendMailSSL.sendMail(toMailIds.toString(),"New technology released -"+inputRequest.getString("technologyline"),msgBody);
    }
    
    public void sendNewNoticeByMailToAll(JSONObject inputRequest) throws JSONException
    {
        List<Userprofile> userProfileList = masterService.getAllStudentUserIds();
        StringBuilder toMailIds=new StringBuilder(GlobalConstants.EMPTY_STRING);
        for(Userprofile profile: userProfileList)
        {
            toMailIds.append(profile.getUsername());
        }
        
        String msgBody=GlobalConstants.NEW_NOTICE_RELEASE_MSG;
        
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
    }
    
    public void sendNewForumEventNotificationByMailToAll(JSONObject inputRequest) throws JSONException
    {
        List<Userprofile> userProfileList = masterService.getAllStudentUserIds();
        StringBuilder toMailIds=new StringBuilder(GlobalConstants.EMPTY_STRING);
        for(Userprofile profile: userProfileList)
        {
            toMailIds.append(profile.getUsername());
        }
        
        String msgBody=GlobalConstants.NEW_FORUM_EVENT_RELEASE_MSG;
        msgBody=msgBody.replaceAll("<owner>", inputRequest.getString("owner"));
        msgBody=msgBody.replaceAll("<event_desc>", inputRequest.getString("event_desc"));
        
        
        System.out.println("====="+toMailIds.toString()+" " +inputRequest.getString("owner") + " posted new forum event - "+inputRequest.getString("event_desc") +" "+msgBody);
        //SendMailSSL.sendMail(toMailIds.toString(),inputRequest.getString("owner") + " posted new forum event - "+inputRequest.getString("event_desc"),msgBody);
    }
}
