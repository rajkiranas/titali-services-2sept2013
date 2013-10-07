/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAO;

import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.entity.Std;
import com.quick.tim.mobileserviceprovider.entity.Whatsnew;

import com.quick.tim.mobileserviceprovider.global.GlobalConstants;


import java.util.List;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;

/**
 *
 * @author suyogn
 */
public interface WhatsNewDao {
    public List<Whatsnew> getWhatsNewForMe(String forStd, String forDiv, boolean isAdmin);
    public List<MasteParmBean> getWhatsNewForMe(String subject);
    public void sendWhatsNewNotificationToStudents(Whatsnew whatsNew);

    public void deleteWhatsNewNotification(int uploadId);

    public void deleteWhoIsDoingWhatNotifications(int uploadId);
}
