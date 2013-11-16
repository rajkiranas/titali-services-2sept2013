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
import java.util.ArrayList;


import java.util.List;
import java.util.Random;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
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

    @Override
    public List<DictWordDetailsBean> searchWordList(JSONObject inputRequest) throws JSONException 
    {
           DetachedCriteria criteria = DetachedCriteria.forClass(DictList.class,"dictionary");
           
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("word"), "word");
        pl.add(Projections.property("meaning"), "meaning");
        pl.add(Projections.property("labels"), "labels");
        pl.add(Projections.property("addDate"), "addDate");
        pl.add(Projections.property("ownerUsername"), "ownerUsername");
        pl.add(Projections.property("ownerName"), "ownerName");
        
        if(inputRequest.getString("searchFor").equals("Word"))
           {
               criteria.add(Restrictions.ilike("dictionary.word",inputRequest.getString("searchWord"),MatchMode.ANYWHERE));
           }
           else
           {
               criteria.add(Restrictions.ilike("dictionary.labels",inputRequest.getString("searchWord"),MatchMode.ANYWHERE));
           }
        
        criteria.addOrder(Order.asc("word"));
        
        criteria.setProjection(pl);
        criteria.setResultTransformer(Transformers.aliasToBean(DictWordDetailsBean.class));
        return hibernateTemplate.findByCriteria(criteria);
        //System.out.println("inputRequest.getInt(*********)="+inputRequest.getInt("fetchResultsFrom"));
        //return hibernateTemplate.findByCriteria(criteria,inputRequest.getInt("fetchResultsFrom"),Integer.parseInt(GlobalConstants.getProperty(GlobalConstants.DICT_FETCH_SIZE)));
    }

    @Override
    public List<DictWordDetailsBean> getWordOfTheDay(JSONObject inputRequest) {
        DetachedCriteria criteria = DetachedCriteria.forClass(DictList.class);
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("word"), "word");
        pl.add(Projections.property("meaning"), "meaning");
        pl.add(Projections.property("labels"), "labels");
        criteria.add(Restrictions.sqlRestriction("1=1 order by random()"));
        criteria.setResultTransformer(Transformers.aliasToBean(DictWordDetailsBean.class));
        criteria.setProjection(pl);
        hibernateTemplate.setFetchSize(1);
        List<Object> list = hibernateTemplate.findByCriteria(criteria);
        Object obj[] = (Object[]) list.get(0);
        List<DictWordDetailsBean> abcList = new ArrayList<DictWordDetailsBean>();
        DictWordDetailsBean wordOfTheDayBean = new DictWordDetailsBean();
        wordOfTheDayBean.setWord((String)obj[0]);
        wordOfTheDayBean.setMeaning((String)obj[1]);
        wordOfTheDayBean.setLabels((String)obj[2]);
        abcList.add(wordOfTheDayBean);
        return abcList;
    }
}