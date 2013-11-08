/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;

import com.quick.tim.mobileserviceprovider.DAO.QuickLearnDAO;
import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.entity.QuickLearn;
import com.quick.tim.mobileserviceprovider.entity.QuickNotes;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author COMPUTER1
 */
@Component
public class QuickLearnService {
@Autowired
QuickLearnDAO quickLearnDAO;
    public List<QuickLearn> getQuickLearnByID(int aInt) {
        return quickLearnDAO.getQuickLearnByID(aInt);
    }
    
    public List<QuickLearn> getNotesByID(int aInt) {
        return quickLearnDAO.getNotesByID(aInt);
    }
    
    public List<QuickLearn> getOtherNotesByID(int aInt) {
        return quickLearnDAO.getgetOtherNotesByID(aInt);
    }
    
    public List<QuickLearn> getPreviousQuestionsByID(int aInt) {
        return quickLearnDAO.getpreviousQueByID(aInt);
    }
    
     public void saveMyShortNotes(QuickNotes quickNotes) {
        quickLearnDAO.saveMyShortNotes(quickNotes);
    }

    public List<MasteParmBean> getWhatsNewForMe(String subject, int fetchResultsFrom) {
        return quickLearnDAO.getWhatsNewForMe(subject,fetchResultsFrom);
    }

    public String getMyQuickNotesById(int uploadId, String userName) {
        List list =quickLearnDAO.getMyQuickNotesById(uploadId,userName);
        if(!list.isEmpty()){
            return list.get(0).toString();
        }else{
         return "";   
        }
        
    }
    
    public void saveQuickUploadDetails(QuickLearn quickLearn) {
       quickLearnDAO.saveQuickUploadDetails(quickLearn);
    }

    public List<MasteParmBean> getquickLearnByUploadId(int uploadId) {
         return quickLearnDAO.getquickLearnByUploadId(uploadId);
    }

    public int getMaxUplaodId() {
        List uploadId = new ArrayList();
        int id = 1;
        uploadId = quickLearnDAO.getMaxUplaodId();
        if (uploadId.get(0) != null) {
            id = id + Integer.parseInt(uploadId.get(0).toString());
        }
        return id;
    }
    
    public void deleteTopicByUploadId(QuickLearn quickLearn) {
       quickLearnDAO.deleteTopicByUploadId(quickLearn);
    }
    
}
