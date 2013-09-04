/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;

import com.quick.tim.mobileserviceprovider.entity.QualificationMaster;
import com.quick.tim.mobileserviceprovider.DAO.QualificationDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author sonalis
 */
@Component
public class QualificationService {
    @Autowired
    private QualificationDao qualificationDao;

    public List<QualificationMaster> getQualificationList() {
        List<QualificationMaster> qualificationList = qualificationDao.getQualificationList();
        return qualificationList;
    }
    
    
}
