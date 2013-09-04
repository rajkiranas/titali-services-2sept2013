/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;

import com.quick.tim.mobileserviceprovider.entity.Notices;
import com.quick.tim.mobileserviceprovider.DAO.NoticeBoardDao;
import java.util.List;
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
}
