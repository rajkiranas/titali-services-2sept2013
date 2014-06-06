/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAOImpl;

import com.quick.tim.mobileserviceprovider.DAO.ForumDao;
import com.quick.tim.mobileserviceprovider.entity.Whatsnew;

import com.quick.tim.mobileserviceprovider.DAO.WhatsNewDao;
import com.quick.tim.mobileserviceprovider.bean.*;
import com.quick.tim.mobileserviceprovider.entity.*;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import java.util.Date;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
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
    public List<ForumEventDetailsBean> getForumEventDetails(JSONObject inputRequest) throws JSONException 
    {
        DetachedCriteria criteria = DetachedCriteria.forClass(ForumEventDetails.class);
//        criteria.add(Restrictions.eq("std.std",std));
//        criteria.add(Restrictions.or(Restrictions.eq("fordiv", fordiv),Restrictions.eq("fordiv", null)));
        
     
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("eventDetailId"), "eventDetailId");
        pl.add(Projections.property("eventDate"), "eventDate");
        pl.add(Projections.property("eventDesc"), "eventDesc");
        pl.add(Projections.property("eventBody"), "eventBody");
        pl.add(Projections.property("eventImage"), "eventImage");
        pl.add(Projections.property("eventOwner"), "eventOwner");
        
        pl.add(Projections.property("parentForumId"), "parentForumId");
        pl.add(Projections.property("imageFilename"), "imageFileName");
        pl.add(Projections.property("videoUrl"), "videoUrl");
        
        criteria.addOrder(Order.desc("eventDate"));
        
        criteria.setProjection(pl);
        criteria.setResultTransformer(Transformers.aliasToBean(ForumEventDetailsBean.class));
        //return hibernateTemplate.findByCriteria(criteria);
        System.out.println("inputRequest.getInt(*********)="+inputRequest.getInt("fetchResultsFrom"));
        return hibernateTemplate.findByCriteria(criteria,inputRequest.getInt("fetchResultsFrom"),Integer.parseInt(GlobalConstants.getProperty(GlobalConstants.FORUM_EVENTS_FETCH_SIZE)));
    }
    
    /* private static final String getlAllForumDetailsQry="from ForumEventDetails as model";
    @Override
    public List<ForumEventDetails> getForumEventDetails(JSONObject inputRequest) 
    {
        return hibernateTemplate.find(getlAllForumDetailsQry);
    } */

    @Override
    public void saveEventDetails(ForumEventDetails event) 
    {
        hibernateTemplate.saveOrUpdate(event);
    }

    @Override
    public void saveEventLike(ForumEventLikes eventLike) {
        hibernateTemplate.saveOrUpdate(eventLike);
    }


    @Override
    public List<EventLikeBean> getEventLikesById(JSONObject inputRequest) throws JSONException 
    {
        DetachedCriteria criteria = DetachedCriteria.forClass(ForumEventLikes.class);
     
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("name"), "name");
        pl.add(Projections.property("id.eventDetailId"), "eventDetailId");
                                    
        pl.add(Projections.property("id.username"), "username");
        pl.add(Projections.property("likeTime"), "likeTime");
        
        criteria.add(Restrictions.eq("id.eventDetailId",inputRequest.getInt("event_id")));        
        criteria.addOrder(Order.asc("likeTime"));
        
        criteria.setProjection(pl);
        criteria.setResultTransformer(Transformers.aliasToBean(EventLikeBean.class));
        return hibernateTemplate.findByCriteria(criteria);
    }
    
    @Override
    public List<EventCommentsBean> getEventCommentsById(JSONObject inputRequest) throws JSONException 
    {
        DetachedCriteria criteria = DetachedCriteria.forClass(ForumEventComments.class);
     
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("name"), "name");
        pl.add(Projections.property("commentBody"), "commentBody");
        pl.add(Projections.property("id.eventDetailId"), "eventDetailId");
                                    
        pl.add(Projections.property("id.username"), "username");
        pl.add(Projections.property("id.commentTime"), "commentTime");
        
        criteria.add(Restrictions.eq("id.eventDetailId",inputRequest.getInt("event_id")));        
        criteria.addOrder(Order.asc("id.commentTime"));
        
        criteria.setProjection(pl);
        criteria.setResultTransformer(Transformers.aliasToBean(EventCommentsBean.class));
        return hibernateTemplate.findByCriteria(criteria);
    }

    @Override
    public void saveEventComment(ForumEventComments eventComment) {
        hibernateTemplate.saveOrUpdate(eventComment);
    }

    
    
    @Override
    public List<ForumEventDetailsBean> getForumEventById(JSONObject inputRequest) throws JSONException 
    {
        DetachedCriteria criteria = DetachedCriteria.forClass(ForumEventDetails.class,"alias");
//        criteria.add(Restrictions.eq("std.std",std));
//        criteria.add(Restrictions.or(Restrictions.eq("fordiv", fordiv),Restrictions.eq("fordiv", null)));
        criteria.add(Restrictions.eq("alias.eventDetailId",Integer.parseInt(inputRequest.getString("eventId"))));
        
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("eventDetailId"), "eventDetailId");
        pl.add(Projections.property("eventDate"), "eventDate");
        pl.add(Projections.property("eventDesc"), "eventDesc");
        pl.add(Projections.property("eventBody"), "eventBody");
        pl.add(Projections.property("eventImage"), "eventImage");
            pl.add(Projections.property("eventOwner"), "eventOwner");
        
        pl.add(Projections.property("parentForumId"), "parentForumId");
        pl.add(Projections.property("imageFilename"), "imageFileName");
        pl.add(Projections.property("videoUrl"), "videoUrl");
        
        criteria.addOrder(Order.desc("eventDate"));
        
        criteria.setProjection(pl);
        criteria.setResultTransformer(Transformers.aliasToBean(ForumEventDetailsBean.class));
        //return hibernateTemplate.findByCriteria(criteria);
        return hibernateTemplate.findByCriteria(criteria);
    }
}
