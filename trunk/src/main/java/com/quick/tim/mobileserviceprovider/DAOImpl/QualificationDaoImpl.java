/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAOImpl;

import com.quick.tim.mobileserviceprovider.entity.QualificationMaster;
import com.quick.tim.mobileserviceprovider.DAO.QualificationDao;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sonalis
 */
@Repository("qualificationDao")
@Transactional
public class QualificationDaoImpl implements QualificationDao{

    private HibernateTemplate hibernateTemplate;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) 
    {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }
    
    public List<QualificationMaster> getQualificationList() {
        
        List<QualificationMaster> qualificationList=null;
        try{
            DetachedCriteria detachedCriteria=DetachedCriteria.forClass(QualificationMaster.class);
            ProjectionList proList = Projections.projectionList();
            proList.add(Projections.property("qualificationName"),"qualificationName");            
            detachedCriteria.setProjection(proList);
            detachedCriteria.setResultTransformer(Transformers.aliasToBean(QualificationMaster.class));
            qualificationList = hibernateTemplate.findByCriteria(detachedCriteria);
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return qualificationList;
    }
    
}
