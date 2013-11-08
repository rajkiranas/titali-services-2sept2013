/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAO;

import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.entity.QuickLearn;
import com.quick.tim.mobileserviceprovider.entity.QuickNotes;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Suyogn
 */
public interface QuickLearnDAO {
    public List<QuickLearn> getQuickLearnByID(int aInt);
    public List<QuickLearn> getNotesByID(int aInt);
    public List<QuickLearn> getgetOtherNotesByID(int aInt);
    public List<QuickLearn> getpreviousQueByID(int aInt);
    public void saveMyShortNotes(QuickNotes quickNotes);
    public List<MasteParmBean> getWhatsNewForMe(String subject,int fetchResultsFrom);
    public List getMyQuickNotesById(int uploadId, String userName);
    public void saveQuickUploadDetails(QuickLearn quickLearn);
    public List<MasteParmBean> getquickLearnByUploadId(int uploadId);
    public List getMaxUplaodId();
    public void deleteTopicByUploadId(QuickLearn quickLearn);

   
}
