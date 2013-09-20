/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAO;

import com.quick.tim.mobileserviceprovider.DAOImpl.GenericDaoImpl;
import com.quick.tim.mobileserviceprovider.bean.CategoryDistributionBean;
import com.quick.tim.mobileserviceprovider.bean.NoticeBean;
import com.quick.tim.mobileserviceprovider.bean.UpcomingTechnologyBean;
import com.quick.tim.mobileserviceprovider.entity.Notices;
import com.quick.tim.mobileserviceprovider.entity.UpcomingTechnology;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author suyogn
 */
public interface TechnologyDao {
    public List<UpcomingTechnology> getTechnologies(String forStd, String forDiv);

    public List<UpcomingTechnologyBean> getAllTechnologies();

    public void saveTechnology(UpcomingTechnology technology);

    public void deleteTechnology(UpcomingTechnology technology);

    public List<UpcomingTechnology> getTechnologyById(int technologyId);

    public List<CategoryDistributionBean> getTechnologyByCategory(String category);
}
