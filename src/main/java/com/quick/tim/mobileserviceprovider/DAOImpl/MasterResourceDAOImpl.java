/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAOImpl;

import com.quick.tim.mobileserviceprovider.DAO.MasterResourceDAO;
import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.bean.QuickLearn;
import com.quick.tim.mobileserviceprovider.entity.Std;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
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
 * @author COMPUTER1
 */
@Repository("masterResourceDAO")
@Transactional
public class MasterResourceDAOImpl implements MasterResourceDAO{
    private HibernateTemplate hibernateTemplate;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) 
    {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }
    public List<MasteParmBean> getStandardWisesubjects(String std) {
        
     List<MasteParmBean> masterParamList=null;
        try 
        {
            DetachedCriteria detCri = DetachedCriteria.forClass(Std.class,"um");
            detCri.createAlias("um.subs", "subs");
            ProjectionList pl = Projections.projectionList();
            pl.add(Projections.property("subs.sub"), "sub");
            detCri.setProjection(pl);
            detCri.add(Restrictions.eq("std", std));
            detCri.setResultTransformer(Transformers.aliasToBean(MasteParmBean.class));
            masterParamList = hibernateTemplate.findByCriteria(detCri);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return masterParamList;
    }
    
    
    
      public List<Std> getStandardList() {
        
        List<Std> stdList=null;
        try{
            DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Std.class);
            ProjectionList proList = Projections.projectionList();
            proList.add(Projections.property("std"),"std");
            
            detachedCriteria.setProjection(proList);
            detachedCriteria.setResultTransformer(Transformers.aliasToBean(Std.class));
            stdList = hibernateTemplate.findByCriteria(detachedCriteria);
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return stdList;
    }

    public List<QuickLearn> getSubjectBystd(String std) {
        List<QuickLearn> stdList=null;
        try{
            DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Std.class);
            detachedCriteria.createAlias("subs", "subs");
            detachedCriteria.add(Restrictions.eq("std", std));
            ProjectionList proList = Projections.projectionList();
            proList.add(Projections.property("subs.sub"),"sub");            
            detachedCriteria.setProjection(proList);
            detachedCriteria.setResultTransformer(Transformers.aliasToBean(QuickLearn.class));
            stdList = hibernateTemplate.findByCriteria(detachedCriteria);
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return stdList;
    }
    
    
    public List<QuickLearn> getDivisionBystd(String std) {
        List<QuickLearn> divList=null;
        try{
            DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Std.class);
            detachedCriteria.createAlias("stddivs", "stddivs");
            detachedCriteria.add(Restrictions.eq("std", std));
            ProjectionList proList = Projections.projectionList();
            proList.add(Projections.property("stddivs.id.div"),"fordiv");  
          //  proList.add(Projections.property("stddivs.id.std"),"std");   
            detachedCriteria.setProjection(proList);
            detachedCriteria.setResultTransformer(Transformers.aliasToBean(QuickLearn.class));
            divList = hibernateTemplate.findByCriteria(detachedCriteria);
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return divList;
    }
    
}
