/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAO;

import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import java.util.List;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Sonali Sangle
 */
public interface QuickDao {
   
    public List<MasteParmBean> getQuickLearnUploadList(JSONObject inputRequest) throws JSONException;
    
}
