/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;

import com.quick.tim.mobileserviceprovider.bean.Userprofile;
import com.quick.tim.mobileserviceprovider.DAO.UserMasterDao;
import com.quick.tim.mobileserviceprovider.bean.TeacherStddivSubIdBean;
import com.quick.tim.mobileserviceprovider.entity.QuickLearn;
import com.quick.tim.mobileserviceprovider.entity.UserMaster;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author rajkirans
 */
@Component
public class UserMasterService 
{
    @Autowired
    UserMasterDao userMasterDao;
    
    public List<Userprofile> getUserProfile(String userName)
    {
         List<Userprofile> userProfileList = userMasterDao.getUserProfile(userName);
         
        return userProfileList;
    }
    
    public List<Userprofile> getTeacherProfile(String userName)
    {
         List<Userprofile> userProfileList = userMasterDao.getTeacherProfile(userName);
         
        return userProfileList;
    }

    public List<Userprofile> getAllStudentList() {
        List<Userprofile> userProfileList = userMasterDao.getAllStudentList();
        return userProfileList;
    }

    public boolean IsRollNoAlreadyExist(String rollNo) {
       List<Userprofile> list=userMasterDao.IsRollNoAlreadyExist(rollNo);
       if(list.isEmpty()){
           return false;
       }else{
           return true;
       } 
    }

    public List<Userprofile> getAllTeacherList() {
        List<Userprofile> userProfileList = userMasterDao.getAllTeacherList();
        return userProfileList;
    }

    public List<Userprofile> getStudentDetailsByPrn(int prn) {
       List<Userprofile> studentList=userMasterDao.getStudentDetailsByPrn(prn);
        return studentList;
    }

    public List<Userprofile> getTeacherDetailsByPrn(int prn) {
        List<Userprofile> teacherList=userMasterDao.getTeacherDetailsByPrn(prn);
        return teacherList;
    }

    public boolean IsUsernameAlreadyExist(String username) {
       List<Userprofile> list=userMasterDao.IsUsernameAlreadyExist(username);
       if(list.isEmpty()){
           return false;
       }else{
           return true;
       }
    }

    public List<Userprofile> getsearchStudentFilterCriteriaList(String searchCriteria, String inputCriteria) {
        List<Userprofile> searchstudList = userMasterDao.getsearchStudentFilterCriteriaList(searchCriteria,inputCriteria);
        return searchstudList;
    } 
    
    
    public List<Userprofile> getsearchTeacherFilterCriteria(String searchCriteria, String inputCriteria) {
        List<Userprofile> searchTeacherList = userMasterDao.getsearchTeacherFilterCriteria(searchCriteria,inputCriteria);
        return searchTeacherList;
    }

    public List<TeacherStddivSubIdBean> getTeacherStdDivSubListByUsername(String username) {
        List<TeacherStddivSubIdBean> teacherList = userMasterDao.getTeacherStdDivSubListByUsername(username);
        return teacherList;
    }

    public void deleteUserFromDB(UserMaster userMaster) {
        userMasterDao.deleteUserFromDB(userMaster);
    }
    
    public List<Userprofile> getStudentUserIdsByClass(String std)
    {
        return userMasterDao.getStudentUserIdsByClass(std);
    }
    
    public List<Userprofile> getAllStudentUserIds()
    {
        return userMasterDao.getAllStudentUserIds();
    }
}
