/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAO;

import com.quick.tim.mobileserviceprovider.DAOImpl.GenericDaoImpl;
import com.quick.tim.mobileserviceprovider.entity.Notices;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author suyogn
 */
public interface NoticeBoardDao {
    public List<Notices> getNotices(String forStd, String forDiv);
}
