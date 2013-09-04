/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;

import com.quick.tim.mobileserviceprovider.entity.UserMaster;
import com.quick.tim.mobileserviceprovider.DAO.StudentDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author sonalis
 */

@Component
public class StudentService {
    @Autowired
    StudentDao studentDao;
    
    public void saveStudent(UserMaster master) throws Exception {
        studentDao.saveOrUpdate(master);
    }
    
    public int getMaxPrn() {
        List prnCode = new ArrayList();
        int id = 1;
        prnCode = studentDao.getMaxPrn();
        if (prnCode.get(0) != null) {
            id = id + Integer.parseInt(prnCode.get(0).toString());
        }
        return id;
    }

}
