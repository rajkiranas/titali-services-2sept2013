/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAOImpl;



import com.quick.tim.mobileserviceprovider.DAO.TechnologyDao;
import com.quick.tim.mobileserviceprovider.bean.CategoryDistributionBean;
import com.quick.tim.mobileserviceprovider.bean.UpcomingTechnologyBean;
import com.quick.tim.mobileserviceprovider.entity.CategoryDistribution;
import com.quick.tim.mobileserviceprovider.entity.UpcomingTechnology;
import java.util.Date;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author suyogn
 */
@Repository("technologyDao")
@Transactional
public class TechnologyDaoImpl implements TechnologyDao{
    
    private HibernateTemplate hibernateTemplate;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) 
    {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }
    
    public List<UpcomingTechnology> getTechnologies(String forStd, String forDiv) {
        List<UpcomingTechnology> technologies = null;
        try {
            //.createAlias("Whatsnew", "Whatsnew")
            DetachedCriteria detCri = DetachedCriteria.forClass(UpcomingTechnology.class);
            ProjectionList proList = Projections.projectionList();
            proList.add(Projections.property("technologyline"),"technologyline");
            proList.add(Projections.property("technologydate"),"technologydate");
            detCri.add(Restrictions.eq("std.std", forStd));
            detCri.add(Restrictions.eq("fordiv", forDiv));
            detCri.setProjection(proList);
            detCri.addOrder(Order.desc("technologydate"));
            detCri.setResultTransformer(Transformers.aliasToBean(UpcomingTechnology.class));
            technologies = hibernateTemplate.findByCriteria(detCri);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return technologies;
    }

    @Override
    public List<UpcomingTechnologyBean> getAllTechnologies() {
        
        List<UpcomingTechnologyBean> technologies = null;
        try {
            //.createAlias("Whatsnew", "Whatsnew")
            DetachedCriteria detCri = DetachedCriteria.forClass(UpcomingTechnology.class);
            ProjectionList proList = Projections.projectionList();
            proList.add(Projections.property("technologyline"), "technologyline");
            proList.add(Projections.property("technologydate"), "technologydate");

            proList.add(Projections.property("technologyid"), "technologyid");
            proList.add(Projections.property("std.std"), "std");
            proList.add(Projections.property("sub.sub"), "sub");
            proList.add(Projections.property("bywhom"), "bywhom");
            proList.add(Projections.property("fordiv"), "fordiv");
            proList.add(Projections.property("technologybody"), "technologybody");
            proList.add(Projections.property("category"), "category");

            detCri.setProjection(proList);
            detCri.addOrder(Order.desc("technologydate"));
            detCri.setResultTransformer(Transformers.aliasToBean(UpcomingTechnologyBean.class));
            technologies = hibernateTemplate.findByCriteria(detCri);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return technologies;
    }

    @Override
    public void saveTechnology(UpcomingTechnology technologies ){
        hibernateTemplate.saveOrUpdate(technologies);
    }

    @Override
    public void deleteTechnology(UpcomingTechnology technology) {
        hibernateTemplate.delete(technology);
    }

    private static final String findTechnologyByIdQry="from UpcomingTechnology as model where model.technologyid=?";
    
    @Override
    public List<UpcomingTechnology> getTechnologyById(int technologyId ){
        return hibernateTemplate.find(findTechnologyByIdQry, technologyId);        
    }

    @Override
    public List<CategoryDistributionBean> getTechnologyByCategory(String category) 
    {
        List<CategoryDistributionBean> technologies = null;
        try 
        {
            //.createAlias("Whatsnew", "Whatsnew")
            DetachedCriteria criteria = DetachedCriteria.forClass(CategoryDistribution.class);
            ProjectionList projection = Projections.projectionList();
            projection.add(Projections.property("id.category"),"category");
            projection.add(Projections.property("id.technologyName"),"technologyName");
            projection.add(Projections.property("percentage"),"percentage");
            
            criteria.add(Restrictions.eq("id.category", category));
            
            criteria.setProjection(projection);
            //detCri.addOrder(Order.desc("technologydate"));
            criteria.setResultTransformer(Transformers.aliasToBean(CategoryDistributionBean.class));
            technologies = hibernateTemplate.findByCriteria(criteria);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return technologies;
    }
}
