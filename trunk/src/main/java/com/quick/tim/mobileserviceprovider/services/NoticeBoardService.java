/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;

import com.quick.tim.mobileserviceprovider.entity.Notices;
import com.quick.tim.mobileserviceprovider.DAO.NoticeBoardDao;
import com.quick.tim.mobileserviceprovider.bean.NoticeBean;
import java.util.Date;
import java.util.List;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author suyogn
 */
@Component
public class NoticeBoardService {
    @Autowired
    NoticeBoardDao noticeBoardDao;

    public List<Notices> getNotices(String std,String div) {
        return noticeBoardDao.getNotices(std, div);
    }

    public List<NoticeBean> getAllNotices() {
        return noticeBoardDao.getAllNotices();
    }
    
     

    public void saveNotice(JSONObject inputRequest) throws JSONException {
        Notices notices =null;
        int noticeId=inputRequest.getInt("noticeId");
        if(noticeId==0)
        {
            notices = new Notices();
        }
        else
        {
            List<Notices> noticeList = noticeBoardDao.getNoticeById(noticeId);
            notices=noticeList.get(0);
        }
        
        notices.setBywhom(inputRequest.getString("bywhom"));
        notices.setNoticebody(inputRequest.getString("noticebody"));
        notices.setNoticeline(inputRequest.getString("noticeline"));
        notices.setNoticedate(new Date(inputRequest.getLong("noticedate")));
        
        noticeBoardDao.saveNotice(notices);
    }

    public void deleteNotice(JSONObject inputRequest) throws JSONException {
        Notices notices = new Notices(inputRequest.getInt("noticeId"));
        noticeBoardDao.deleteNotice(notices);
    }
}
