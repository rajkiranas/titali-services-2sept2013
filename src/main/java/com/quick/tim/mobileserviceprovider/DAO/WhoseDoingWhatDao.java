/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAO;

import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.entity.Whatsnew;
import com.quick.tim.mobileserviceprovider.entity.Whoisdoingwhat;
import com.quick.tim.mobileserviceprovider.services.WhoseDoingWhatService;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author suyogn
 */
public interface WhoseDoingWhatDao {
    public List<MasteParmBean> getWhoIsDoingWhat(String forStd, String forDiv, boolean isAdmin,int fetchResultsFrom);

    public void sendWhosDoingWhatNotificationToStudents(Whoisdoingwhat w);
}
