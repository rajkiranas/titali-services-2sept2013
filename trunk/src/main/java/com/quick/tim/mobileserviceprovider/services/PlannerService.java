/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;

import com.quick.tim.mobileserviceprovider.DAO.PlannerDao;
import com.quick.tim.mobileserviceprovider.bean.AppointmentMstBean;
import com.quick.tim.mobileserviceprovider.bean.DictWordDetailsBean;
import com.quick.tim.mobileserviceprovider.entity.AppointmentMst;
import com.quick.tim.mobileserviceprovider.entity.DictList;
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
public class PlannerService 
{
    @Autowired
    PlannerDao plannerDao;
    
       public List<AppointmentMstBean> getEventList(JSONObject inputRequest) throws JSONException
       {
           List<AppointmentMstBean> list = plannerDao.getEventList(inputRequest);
           
           return list;
       }
       
       public List<DictWordDetailsBean> searchWordList(JSONObject inputRequest) throws JSONException
       {
           List<DictWordDetailsBean> list = plannerDao.searchWordList(inputRequest);
           
           return list;
       }
       
    public void saveEvent(JSONObject inputRequest) throws Exception {
        AppointmentMst event = new AppointmentMst();
        
        event.setOwnerName(inputRequest.getString("owner_name"));
        event.setOwnerUsername(inputRequest.getString("owner_username"));
        event.setStarttime(new Date(inputRequest.getLong("startTime")));
        event.setEndtime(new Date(inputRequest.getLong("endTime")));
        event.setStartdate(new Date(inputRequest.getLong("startTime")));
        event.setEnddate(new Date(inputRequest.getLong("startTime")));
        
        event.setEventCaption(inputRequest.getString("caption"));
        event.setEventDescription(inputRequest.getString("desc"));
        event.setEventStyle(inputRequest.getString("event_style"));
        event.setIsallday(inputRequest.getBoolean("isallday"));
        event.setForWhom(inputRequest.getString("owner_name"));

        plannerDao.saveEvent(event);
    }

    public List<DictWordDetailsBean> getWordOfTheDay(JSONObject inputRequest) {
        List<DictWordDetailsBean> list = plannerDao.getWordOfTheDay(inputRequest);
        
        return list;
    }
}