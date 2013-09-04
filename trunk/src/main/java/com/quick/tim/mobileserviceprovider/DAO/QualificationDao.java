/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAO;

import com.quick.tim.mobileserviceprovider.entity.QualificationMaster;
import com.quick.tim.mobileserviceprovider.entity.Std;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

/**
 *
 * @author sonalis
 */
public interface QualificationDao{
    public List<QualificationMaster> getQualificationList();
}
