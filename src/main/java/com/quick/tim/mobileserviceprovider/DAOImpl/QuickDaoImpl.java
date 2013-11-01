/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAOImpl;

import com.quick.tim.mobileserviceprovider.DAO.QuickDao;
import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.entity.QuickLearn;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import java.util.List;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
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
 * @author Sonali Sangle
 */
@Repository("quickDao")
@Transactional
public class QuickDaoImpl implements QuickDao{
    
    private HibernateTemplate hibernateTemplate;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory){
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public List<MasteParmBean> getQuickLearnUploadList(JSONObject inputRequest) throws JSONException {
        List<MasteParmBean> quicklearnUploadList=null;
        DetachedCriteria criteria = DetachedCriteria.forClass(QuickLearn.class, "ql");

        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("uploadId"), "uploadId");
        pl.add(Projections.property("uploadDate"), "uploadDate");
        pl.add(Projections.property("ql.std.std"), "std");
        pl.add(Projections.property("fordiv"), "div");
        pl.add(Projections.property("ql.sub.sub"), "sub");
        pl.add(Projections.property("topic"), "topic");
        pl.add(Projections.property("lectureNotes"), "notes");
        pl.add(Projections.property("otherNotes"), "othernotes");
        pl.add(Projections.property("previousQuestion"), "previousQuestion");

        pl.add(Projections.property("lectureNotesInformation"), "lectureNotesInformation");
        pl.add(Projections.property("otherNotesInformation"), "otherNotesInformation");
        pl.add(Projections.property("previousQuestionInformation"), "previousQuestionInformation");
        criteria.addOrder(Order.desc("uploadDate"));
        criteria.setProjection(pl);
        criteria.setResultTransformer(Transformers.aliasToBean(MasteParmBean.class));
        
        if(inputRequest.has("subject"))
        {
            if (inputRequest.getString("subject") != null && !inputRequest.getString("subject").equals(GlobalConstants.EMPTY_STRING)) {
                //commented after changeing quick learn ui with pop up window
                            //subject is selected by user
                criteria.add(Restrictions.eq("ql.sub.sub", inputRequest.getString("subject")));
                quicklearnUploadList=hibernateTemplate.findByCriteria(criteria);
            }
            else
            {
                quicklearnUploadList=hibernateTemplate.findByCriteria(criteria,inputRequest.getInt("fetchResultsFrom"),Integer.parseInt(GlobalConstants.getProperty(GlobalConstants.QUICK_UPLOAD_ADMIN_FETCH_SIZE)));
            }
        }
        else
            {
                quicklearnUploadList=hibernateTemplate.findByCriteria(criteria,inputRequest.getInt("fetchResultsFrom"),Integer.parseInt(GlobalConstants.getProperty(GlobalConstants.QUICK_UPLOAD_ADMIN_FETCH_SIZE)));
            }

        
        return quicklearnUploadList;
    }
}
