/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;

import com.quick.tim.mobileserviceprovider.DAO.PlannerDao;
import com.quick.tim.mobileserviceprovider.bean.AppointmentMstBean;
import com.quick.tim.mobileserviceprovider.bean.DictWordDetailsBean;
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
       
    public void saveNewWordDetails(JSONObject inputRequest) throws Exception {
        DictList word = new DictList();
        
        word.setWord(inputRequest.getString("word"));
        word.setMeaning(inputRequest.getString("meaning"));
        word.setLabels(inputRequest.getString("labels"));
        word.setAddDate(new Date());
        word.setOwnerUsername(inputRequest.getString("owner_username"));
        word.setOwnerName(inputRequest.getString("owner_name"));

        plannerDao.saveNewWordDetails(word);
    }

    public List<DictWordDetailsBean> getWordOfTheDay(JSONObject inputRequest) {
        List<DictWordDetailsBean> list = plannerDao.getWordOfTheDay(inputRequest);
        
        return list;
    }
}
