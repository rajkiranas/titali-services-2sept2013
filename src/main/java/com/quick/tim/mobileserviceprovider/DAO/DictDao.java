/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAO;

import com.quick.tim.mobileserviceprovider.bean.DictWordDetailsBean;
import com.quick.tim.mobileserviceprovider.entity.DictList;


import java.util.List;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author suyogn
 */
public interface DictDao {
    public List<DictWordDetailsBean> getDictWordList(JSONObject inputRequest) throws JSONException;    
    public List<DictWordDetailsBean> searchWordList(JSONObject inputRequest) throws JSONException;    
    public void saveNewWordDetails(DictList word) throws Exception;
    public List<DictWordDetailsBean> getWordOfTheDay(JSONObject inputRequest);
}
