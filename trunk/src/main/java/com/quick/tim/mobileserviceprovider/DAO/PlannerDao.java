/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAO;

import com.quick.tim.mobileserviceprovider.bean.AppointmentMstBean;
import com.quick.tim.mobileserviceprovider.bean.DictWordDetailsBean;
import com.quick.tim.mobileserviceprovider.entity.AppointmentMst;
import com.quick.tim.mobileserviceprovider.entity.DictList;


import java.util.List;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author suyogn
 */
public interface PlannerDao {
    public List<AppointmentMstBean> getEventList(JSONObject inputRequest) throws JSONException;    
    public List<DictWordDetailsBean> searchWordList(JSONObject inputRequest) throws JSONException;    
    public void saveEvent(AppointmentMst event) throws Exception;
    public void deleteEvent(AppointmentMst event) throws Exception;
    public List<DictWordDetailsBean> getWordOfTheDay(JSONObject inputRequest);
}
