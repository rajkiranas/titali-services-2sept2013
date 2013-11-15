/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAOImpl;

import com.quick.tim.mobileserviceprovider.DAO.DictDao;
import com.quick.tim.mobileserviceprovider.DAO.ForumDao;
import com.quick.tim.mobileserviceprovider.bean.*;
import com.quick.tim.mobileserviceprovider.entity.*;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;


import java.util.List;
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

@Repository("dictDao")
@Transactional
public class DictDaoImpl implements DictDao {

    //private static String getAllMsgQry = "from Ssconversation as model where model.messagestatus not in(" + GlobalConstants.DELETED_FROM_INBOX + ") and (model.msgto = ? or model.msgfrom = ?)  order by model.messageid desc";
    private HibernateTemplate hibernateTemplate;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) 
    {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    @Override
    public List<DictWordDetailsBean> getDictWordList(JSONObject inputRequest) throws JSONException {
        DetachedCriteria criteria = DetachedCriteria.forClass(DictList.class);
//        criteria.add(Restrictions.eq("std.std",std));
//        criteria.add(Restrictions.or(Restrictions.eq("fordiv", fordiv),Restrictions.eq("fordiv", null)));
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("word"), "word");
        pl.add(Projections.property("meaning"), "meaning");
        pl.add(Projections.property("labels"), "labels");
        pl.add(Projections.property("addDate"), "addDate");
        pl.add(Projections.property("ownerUsername"), "ownerUsername");
        pl.add(Projections.property("ownerName"), "ownerName");
        
        criteria.addOrder(Order.asc("word"));
        
        criteria.setProjection(pl);
        criteria.setResultTransformer(Transformers.aliasToBean(DictWordDetailsBean.class));
        //return hibernateTemplate.findByCriteria(criteria);
        //System.out.println("inputRequest.getInt(*********)="+inputRequest.getInt("fetchResultsFrom"));
        return hibernateTemplate.findByCriteria(criteria,inputRequest.getInt("fetchResultsFrom"),Integer.parseInt(GlobalConstants.getProperty(GlobalConstants.DICT_FETCH_SIZE)));
    }

    @Override
    public void saveNewWordDetails(DictList word) throws Exception {
        hibernateTemplate.saveOrUpdate(word);
    }
}
