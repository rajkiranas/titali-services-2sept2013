/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;

import com.quick.tim.mobileserviceprovider.DAO.DictDao;
import com.quick.tim.mobileserviceprovider.bean.DictWordDetailsBean;
import com.quick.tim.mobileserviceprovider.entity.DictList;
import com.quick.tim.mobileserviceprovider.entity.ForumEventDetails;
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
public class DictService 
{
    @Autowired
    DictDao dictDao;
    
       public List<DictWordDetailsBean> getDictWordList(JSONObject inputRequest) throws JSONException
       {
           List<DictWordDetailsBean> list = dictDao.getDictWordList(inputRequest);
           
           return list;
       }
       
       public List<DictWordDetailsBean> searchWordList(JSONObject inputRequest) throws JSONException
       {
           List<DictWordDetailsBean> list = dictDao.searchWordList(inputRequest);
           
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

        dictDao.saveNewWordDetails(word);
    }

    public List<DictWordDetailsBean> getWordOfTheDay(JSONObject inputRequest) {
        List<DictWordDetailsBean> list = dictDao.getWordOfTheDay(inputRequest);
        
        return list;
    }
}
