/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAOImpl;

import com.quick.tim.mobileserviceprovider.entity.UserMaster;
import com.quick.tim.mobileserviceprovider.DAO.TeacherDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sonalis
 */

@Repository("teacherDao")
@Transactional
public class TeacherDaoImpl implements TeacherDao{
 private HibernateTemplate hibernateTemplate;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) 
    {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }
    public void saveOrUpdate(UserMaster master) {
        hibernateTemplate.saveOrUpdate(master);
    }
    
}
