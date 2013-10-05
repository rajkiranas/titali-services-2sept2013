/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAOImpl;

import com.quick.tim.mobileserviceprovider.DAO.ForumDao;
import com.quick.tim.mobileserviceprovider.entity.Whatsnew;

import com.quick.tim.mobileserviceprovider.DAO.WhatsNewDao;
import com.quick.tim.mobileserviceprovider.bean.ExamBean;
import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.entity.ExamEntry;
import com.quick.tim.mobileserviceprovider.entity.ForumEventDetails;
import com.quick.tim.mobileserviceprovider.entity.Whoisdoingwhat;


import java.util.List;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kishorp
 */

@Repository("forumDao")
@Transactional
public class ForumDaoImpl implements ForumDao {

    //private static String getAllMsgQry = "from Ssconversation as model where model.messagestatus not in(" + GlobalConstants.DELETED_FROM_INBOX + ") and (model.msgto = ? or model.msgfrom = ?)  order by model.messageid desc";
    private HibernateTemplate hibernateTemplate;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) 
    {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    @Override
    public List<ForumEventDetails> getForumEventDetails(JSONObject inputRequest) 
    {
        DetachedCriteria criteria = DetachedCriteria.forClass(ForumEventDetails.class);
//        criteria.add(Restrictions.eq("std.std",std));
//        criteria.add(Restrictions.or(Restrictions.eq("fordiv", fordiv),Restrictions.eq("fordiv", null)));
//        ProjectionList pl = Projections.projectionList();
//        pl.add(Projections.property("exId"), "examId");
//        pl.add(Projections.property("sub.sub"), "sub");
//        pl.add(Projections.property("exName"), "exName");
//        pl.add(Projections.property("exType"), "exType");
//        pl.add(Projections.property("startDt"), "startDt");
//        pl.add(Projections.property("endDt"), "endDt");
//        criteria.setProjection(pl);
//        criteria.setResultTransformer(Transformers.aliasToBean(ExamBean.class));
        //return hibernateTemplate.findByCriteria(criteria);
        return hibernateTemplate.findByCriteria(criteria);
    }
    
    
}