/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;

import com.quick.tim.mobileserviceprovider.DAO.TechnologyDao;
import com.quick.tim.mobileserviceprovider.bean.CategoryDistributionBean;
import com.quick.tim.mobileserviceprovider.bean.UpcomingTechnologyBean;
import com.quick.tim.mobileserviceprovider.entity.UpcomingTechnology;
import java.util.Date;
import java.util.List;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author suyogn
 */
@Component
public class TechnologyService {
    @Autowired
    TechnologyDao technologyDao;

    public List<UpcomingTechnology> getTechnologies(String std,String div) {
        return technologyDao.getTechnologies(std, div);
    }

    public List<UpcomingTechnologyBean> getAllTechnologies() {
        return technologyDao.getAllTechnologies();
    }
    
     

    public void saveTechnology(JSONObject inputRequest) throws JSONException {
        UpcomingTechnology technology =null;
        int technologyId=inputRequest.getInt("technologyId");
        // new technology
        if(technologyId==0)
        {
            technology = new UpcomingTechnology();
        }
        else
        {
            //edit existing technology
            List<UpcomingTechnology> technologyList = technologyDao.getTechnologyById(technologyId);
            technology=technologyList.get(0);
        }
        
        technology.setBywhom(inputRequest.getString("bywhom"));
        technology.setTechnologybody(inputRequest.getString("technologybody"));
        technology.setTechnologyline(inputRequest.getString("technologyline"));
        technology.setTechnologydate(new Date(inputRequest.getLong("technologydate")));
        technology.setCategory(inputRequest.getString("category"));
        
        technologyDao.saveTechnology(technology);
    }

    public void deleteTechnology(JSONObject inputRequest) throws JSONException {
        UpcomingTechnology technology = new UpcomingTechnology(inputRequest.getInt("technologyId"));
        technologyDao.deleteTechnology(technology);
    }

    public List<CategoryDistributionBean> getTechnologyByCategory(JSONObject inputRequest) throws JSONException 
    {
        String category = inputRequest.getString("category");
        return technologyDao.getTechnologyByCategory(category);
    }
}
