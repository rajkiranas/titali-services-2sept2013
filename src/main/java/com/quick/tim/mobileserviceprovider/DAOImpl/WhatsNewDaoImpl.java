/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAOImpl;

import com.quick.tim.mobileserviceprovider.entity.Whatsnew;

import com.quick.tim.mobileserviceprovider.DAO.WhatsNewDao;
import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
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

@Repository("whatsNewDao")
@Transactional
public class WhatsNewDaoImpl implements WhatsNewDao {

    //private static String getAllMsgQry = "from Ssconversation as model where model.messagestatus not in(" + GlobalConstants.DELETED_FROM_INBOX + ") and (model.msgto = ? or model.msgfrom = ?)  order by model.messageid desc";
    private HibernateTemplate hibernateTemplate;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) 
    {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }
    
    public List<Whatsnew> getWhatsNewForMe(String forStd, String forDiv, boolean isAdmin)
    {
        List<Whatsnew> whatsNewList=null;
        try 
        {
            //.createAlias("Whatsnew", "Whatsnew")
            DetachedCriteria detCri = DetachedCriteria.forClass(Whatsnew.class);
//            detCri.add(Restrictions.eq("std",forStd));   
//            detCri.add(Restrictions.eq("fordiv", forDiv));
            
 
            
            ProjectionList proList = Projections.projectionList();
            proList.add(Projections.property("displaynotification"),"displaynotification");
            proList.add(Projections.property("topic"),"topic");
            proList.add(Projections.property("itemid"),"itemid");
            proList.add(Projections.property("releasedate"),"releasedate");
            proList.add(Projections.property("topicintro"),"topicintro");
            
            detCri.setProjection(proList);
            if(!isAdmin)
            {
                detCri.add(Restrictions.eq("std.std", forStd));
            }
            
            detCri.addOrder(Order.desc("releasedate"));
            //intentionally removed division restriction
            ///detCri.add(Restrictions.eq("fordiv", forDiv));
            detCri.setResultTransformer(Transformers.aliasToBean(Whatsnew.class));
            whatsNewList = hibernateTemplate.findByCriteria(detCri);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return whatsNewList;
    }
    
    
      public List<MasteParmBean> getWhatsNewForMe(String subject) {
        
          List<MasteParmBean> whatsNewList=null;
        try 
        {
            //.createAlias("Whatsnew", "Whatsnew")
            DetachedCriteria detCri = DetachedCriteria.forClass(Whatsnew.class).createAlias("sub", "sub");
            ProjectionList proList = Projections.projectionList();
            proList.add(Projections.property("sub.sub"),"sub");
            proList.add(Projections.property("topic"),"topic");
            detCri.setProjection(proList);
            detCri.add(Restrictions.eq("sub.sub", subject));
            detCri.setResultTransformer(Transformers.aliasToBean(MasteParmBean.class));
            whatsNewList = hibernateTemplate.findByCriteria(detCri);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return whatsNewList;
        
    }
      
    @Override
      public void sendWhatsNewNotificationToStudents(Whatsnew whatsnew)
      {
          hibernateTemplate.saveOrUpdate(whatsnew);
      }

  /*  public List<Ssconversations> getAllMessages(String userId, List<Ofgroupuser> ofgroupuserList, String view) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Ssconversations.class).createAlias("ssmailbox", "ssmailbox");
      
        detachedCriteria.add(Restrictions.eq("folder", GlobalConstants.INBOX));   
        detachedCriteria.add(Restrictions.eq("userid", userId));
        detachedCriteria.addOrder(Order.asc("ssmailbox.messageid"));
        List<Ssconversations> userMsgList = findByCriteria(detachedCriteria);

        if (ofgroupuserList.size() > 0) {
            getGroupMessages(ofgroupuserList, userMsgList);
        }
        return userMsgList;
    } */

    /* private void getGroupMessages(List<Ofgroupuser> ofgroupuserList, List<Ssconversations> msgList) {
        for (Ofgroupuser ofgroupuser : ofgroupuserList) {
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Ssconversations.class).createAlias("ssmailbox", "ssmailbox");
            detachedCriteria.add(Restrictions.ilike("ssmailbox.msgto", ofgroupuser.getId().getGroupname(), MatchMode.ANYWHERE));
            detachedCriteria.add(Restrictions.eq("folder", GlobalConstants.INBOX));
            List<Ssconversations> gruopMsgList = findByCriteria(detachedCriteria);

            if (gruopMsgList.size() > 0) {
                for (Ssconversations ssConv : gruopMsgList) {
                    msgList.add(ssConv);
                }
            }
        }
    } */

    @Override
    public void deleteWhatsNewNotification(int uploadId) {
        
        Whatsnew whatsnew = new Whatsnew(uploadId);
        hibernateTemplate.delete(whatsnew);
        
    }
    
    @Override
    public void deleteWhoIsDoingWhatNotifications(int uploadId) {
        
        Whoisdoingwhat w = new Whoisdoingwhat(uploadId);
        hibernateTemplate.delete(w);
        
    }
}
