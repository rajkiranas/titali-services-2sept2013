/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAOImpl;

import com.quick.tim.mobileserviceprovider.entity.Notices;
import com.quick.tim.mobileserviceprovider.entity.Whatsnew;
import com.quick.tim.mobileserviceprovider.entity.Whoisdoingwhat;
import com.quick.tim.mobileserviceprovider.DAO.NoticeBoardDao;
import com.quick.tim.mobileserviceprovider.services.WhoseDoingWhatService;
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
 * @author suyogn
 */
@Repository("noticeBoardDao")
@Transactional
public class NoticeBoardDaoImpl implements NoticeBoardDao{
    
    private HibernateTemplate hibernateTemplate;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) 
    {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }
    public List<Notices> getNotices(String forStd, String forDiv) {
        List<Notices> noticeses = null;
        try {
            //.createAlias("Whatsnew", "Whatsnew")
            DetachedCriteria detCri = DetachedCriteria.forClass(Notices.class);
            ProjectionList proList = Projections.projectionList();
            proList.add(Projections.property("noticeline"),"noticeline");
            detCri.add(Restrictions.eq("std.std", forStd));
            detCri.add(Restrictions.eq("fordiv", forDiv));
            detCri.setProjection(proList);
            detCri.setResultTransformer(Transformers.aliasToBean(Notices.class));
            noticeses = hibernateTemplate.findByCriteria(detCri);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return noticeses;
    }

    
}
