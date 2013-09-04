/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;

import com.quick.tim.mobileserviceprovider.entity.UserMaster;
import com.quick.tim.mobileserviceprovider.DAO.TeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author vmundhe
 */

@Component
public class TeacherService {
    
    @Autowired
    private TeacherDao teacherDao;
    
    public void saveTeacher(UserMaster master) throws Exception {
        teacherDao.saveOrUpdate(master);
    }

}
