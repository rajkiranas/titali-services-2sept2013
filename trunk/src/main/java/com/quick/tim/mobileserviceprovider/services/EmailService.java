/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;


import com.quick.tim.mobileserviceprovider.bean.Userprofile;
import com.quick.tim.mobileserviceprovider.entity.QuickLearn;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import com.quick.tim.mobileserviceprovider.utilities.SendMailSSL;
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
        //System.out.println("====="+toMailIds.toString()+" " +"New topic released-"+quickLearn.getTopic() +" "+msgBody);
        SendMailSSL.sendMail(toMailIds.toString(),"New exam released on -"+inputRequest.getString("topic"),msgBody);
    }
}
