/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;

import com.quick.tim.mobileserviceprovider.DAO.DictDao;
import com.quick.tim.mobileserviceprovider.bean.DictWordDetailsBean;
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
public class DictService 
{
    @Autowired
    DictDao dictDao;
    
       public List<DictWordDetailsBean> getDictWordList(JSONObject inputRequest) throws JSONException
       {
           List<DictWordDetailsBean> list = dictDao.getDictWordList(inputRequest);
           
           return list;
       }
}
