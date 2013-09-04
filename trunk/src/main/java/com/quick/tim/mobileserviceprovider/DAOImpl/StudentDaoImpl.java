/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAOImpl;

import com.quick.tim.mobileserviceprovider.entity.UserMaster;
import com.quick.tim.mobileserviceprovider.DAO.StudentDao;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sonalis
 */
@Repository("studentDao")
@Transactional
public class StudentDaoImpl implements StudentDao{

    private HibernateTemplate hibernateTemplate;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) 
    {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }
    
    
      private static final String getMaxPrnByQry="select max(model.prn) from UserMaster as model";

        public List getMaxPrn() {
            return (List<UserMaster>)hibernateTemplate.find(getMaxPrnByQry);
        }

    public void saveOrUpdate(UserMaster userMaster) {
        hibernateTemplate.saveOrUpdate(userMaster);
    }
    
   
}
