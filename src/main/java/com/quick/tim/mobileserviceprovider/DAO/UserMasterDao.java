/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAO;

import com.quick.tim.mobileserviceprovider.bean.TeacherStddivSubIdBean;
import com.quick.tim.mobileserviceprovider.bean.Userprofile;
import com.quick.tim.mobileserviceprovider.entity.QuickLearn;
import com.quick.tim.mobileserviceprovider.entity.UserMaster;


import java.util.List;

/**
 *
 * @author Sonalis
 */
public interface UserMasterDao  {
    public List<Userprofile> getUserProfile(String userName);
    public List<Userprofile> getTeacherProfile(String userName);
    public List<Userprofile> getAllStudentList();      
    public List<Userprofile> IsRollNoAlreadyExist(String rollNo);
    public List<Userprofile> getAllTeacherList();
    public List<Userprofile> getStudentDetailsByPrn(int prn);
    public List<Userprofile> getTeacherDetailsByPrn(int prn);
    public List<Userprofile> IsUsernameAlreadyExist(String username);
    public List<Userprofile> getsearchStudentFilterCriteriaList(String searchCriteria, String inputCriteria);
    public List<Userprofile> getsearchTeacherFilterCriteria(String searchCriteria, String inputCriteria);
    public List<TeacherStddivSubIdBean> getTeacherStdDivSubListByUsername(String username);

    public void deleteUserFromDB(UserMaster userMaster);
    public List<UserMaster> getUserMasterById(String userName);

    public int getStudentCountForClass(String std, String fordiv);

    public List<Userprofile> getStudentUserIdsByClass(String std);
    public List<Userprofile> getAllStudentUserIds();
}
