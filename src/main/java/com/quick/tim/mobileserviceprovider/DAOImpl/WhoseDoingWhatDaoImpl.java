/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAOImpl;

import com.quick.tim.mobileserviceprovider.entity.Whoisdoingwhat;
import com.quick.tim.mobileserviceprovider.DAO.WhoseDoingWhatDao;
import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
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
@Repository("whoseDoingWhatDao")
@Transactional
public class WhoseDoingWhatDaoImpl implements WhoseDoingWhatDao{
    
    private HibernateTemplate hibernateTemplate;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) 
    {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }
    
    private static final String forumTopic="Forum";
    public List<MasteParmBean> getWhoIsDoingWhat(String forStd, String forDiv, boolean isAdmin,int fetchResultsFrom) {
        List<MasteParmBean> doingWhats = null;
        try {
            //.createAlias("Whatsnew", "Whatsnew")
            DetachedCriteria detCri = DetachedCriteria.forClass(Whoisdoingwhat.class, "wdw");
            
            ProjectionList proList = Projections.projectionList();
            proList.add(Projections.property("std.std"),"std");
            proList.add(Projections.property("fordiv"),"div");
            proList.add(Projections.property("activityid"),"activityId");
            proList.add(Projections.property("bywhom"),"byWhom");
            proList.add(Projections.property("displaynotification"),"displaynotification");
            proList.add(Projections.property("uploadId"),"uploadId");
            proList.add(Projections.property("activitydate"),"uploadDate");
            proList.add(Projections.property("topicintro"),"topicintro");
            proList.add(Projections.property("classToInvoke"),"classToInvoke");
            proList.add(Projections.property("topic"),"topic");
            proList.add(Projections.property("videoUrl"),"videoUrl");
            
            detCri.setProjection(proList);      
            if(!isAdmin)
            {
                detCri.add(Restrictions.or(Restrictions.eq("std.std", forStd),Restrictions.eq("wdw.topic", forumTopic)));
//                detCri.add(Restrictions.eq("wdw.topic", forumTopic));
//                criteria.add(Restrictions.or(Restrictions.eq("fordiv", fordiv),Restrictions.eq("fordiv", null)));

            }
            detCri.addOrder(Order.desc("activitydate"));
            //intentionally removed
            //detCri.add(Restrictions.eq("fordiv", forDiv));
            System.out.println("$$$$fetchResultsFrom="+fetchResultsFrom);
            
            detCri.setResultTransformer(Transformers.aliasToBean(MasteParmBean.class));
            doingWhats = hibernateTemplate.findByCriteria(detCri,fetchResultsFrom,Integer.parseInt(GlobalConstants.getProperty(GlobalConstants.DASH_ACTIVITY_FETCH_SIZE)));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return doingWhats;
    }

    @Override
    public void sendWhosDoingWhatNotificationToStudents(Whoisdoingwhat w) 
    {
        hibernateTemplate.save(w);
    }

    
}
