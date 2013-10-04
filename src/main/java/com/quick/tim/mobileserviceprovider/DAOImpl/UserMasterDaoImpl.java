/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAOImpl;

import com.quick.tim.mobileserviceprovider.bean.Userprofile;
import com.quick.tim.mobileserviceprovider.entity.UserMaster;
import com.quick.tim.mobileserviceprovider.DAO.UserMasterDao;
import com.quick.tim.mobileserviceprovider.bean.TeacherStddivSubIdBean;
import com.quick.tim.mobileserviceprovider.entity.ExamEntry;
import com.quick.tim.mobileserviceprovider.entity.QuickLearn;
import com.quick.tim.mobileserviceprovider.entity.Std;
import com.quick.tim.mobileserviceprovider.entity.TeacherStddivSub;
import com.quick.tim.mobileserviceprovider.entity.UserRoles;
import java.util.ArrayList;
import java.util.Iterator;


import java.util.List;
import java.util.Set;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sonalis
 */

@Repository("userMasterDao")
@Transactional
public class UserMasterDaoImpl implements  UserMasterDao {

    //private static String getAllMsgQry = "from Ssconversation as model where model.messagestatus not in(" + GlobalConstants.DELETED_FROM_INBOX + ") and (model.msgto = ? or model.msgfrom = ?)  order by model.messageid desc";
    private HibernateTemplate hibernateTemplate;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) 
    {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }
    private static final String getRollNoDetailsByQry="select model.rno from StudentMaster as model where model.rno=?";
    private static final String getUsernameDetailsByQry="select model.username from UserMaster as model where model.username=?";
    private static final String getStudentsOfClassQry="select model.rno from StudentMaster as model where model.std.std=? and model.div=?";
    
    
    public List<Userprofile> getUserProfile(String userName){
        List<Userprofile> userProfileList=null;
        try{            
            DetachedCriteria detCri = DetachedCriteria.forClass(UserMaster.class,"um");
            detCri.createAlias("um.studentMasters", "studentMasters");
            ProjectionList pl = Projections.projectionList();
            pl.add(Projections.property("prn"), "prn");
            pl.add(Projections.property("name"), "name");
             pl.add(Projections.property("username"), "username");
            pl.add(Projections.property("creationdate"), "creationdate");
            pl.add(Projections.property("mobile"), "mobile");
            pl.add(Projections.property("studentMasters.div"),"div");
            pl.add(Projections.property("studentMasters.std.std"),"std");
            
            
            detCri.add(Restrictions.eq("um.username", userName));
            detCri.setProjection(pl);
//            detCri.setFetchMode("studentMasters", FetchMode.JOIN);
            detCri.setResultTransformer(Transformers.aliasToBean(Userprofile.class));
            userProfileList = hibernateTemplate.findByCriteria(detCri);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return userProfileList;
    }

    public List<Userprofile> getAllStudentList() {
      List<Userprofile> studentList=null;
      try{
          DetachedCriteria detCri = DetachedCriteria.forClass(UserMaster.class,"um");
            detCri.createAlias("um.studentMasters", "studentMasters");
            ProjectionList pl = Projections.projectionList();
            pl.add(Projections.property("um.prn"), "prn");
            pl.add(Projections.property("um.username"), "username");
            pl.add(Projections.property("um.name"), "name");
            pl.add(Projections.property("um.mobile"), "mobile");
            pl.add(Projections.property("studentMasters.rno"),"rno");
            pl.add(Projections.property("studentMasters.div"),"div");
            pl.add(Projections.property("studentMasters.std.std"),"std");
            pl.add(Projections.property("studentMasters.address"),"address");
            detCri.setProjection(pl);
            detCri.setResultTransformer(Transformers.aliasToBean(Userprofile.class));
            studentList = hibernateTemplate.findByCriteria(detCri);
      }catch(Exception ex){
          ex.printStackTrace();
      }      
     return studentList;      
    }

    public List<Userprofile> IsRollNoAlreadyExist(String rollNo) {
        return (List<Userprofile>)hibernateTemplate.find(getRollNoDetailsByQry, Short.valueOf(rollNo));
    }

    public List<Userprofile> getAllTeacherList() {
        List<Userprofile> teacherList=null;
      try{
          DetachedCriteria detCri = DetachedCriteria.forClass(UserMaster.class,"um");
            detCri.createAlias("um.teacherMasters", "teacherMasters");
            ProjectionList pl = Projections.projectionList();
            pl.add(Projections.property("um.prn"), "prn");
            pl.add(Projections.property("um.username"), "username");
            pl.add(Projections.property("teacherMasters.username"), "username");
            pl.add(Projections.property("um.name"), "name");
            pl.add(Projections.property("um.mobile"), "mobile");
            pl.add(Projections.property("teacherMasters.address"),"address");
            pl.add(Projections.property("teacherMasters.doj"),"doj");
            detCri.setProjection(pl);
            detCri.setResultTransformer(Transformers.aliasToBean(Userprofile.class));
            teacherList = hibernateTemplate.findByCriteria(detCri);
      }catch(Exception ex){
          ex.printStackTrace();
      }      
     return teacherList; 
    }

    public List<Userprofile> getStudentDetailsByPrn(int prn) {
         List<Userprofile> studentList=null;
      try{
            DetachedCriteria detCri = DetachedCriteria.forClass(UserMaster.class,"um");
            detCri.createAlias("um.studentMasters", "studentMasters");
            detCri.add(Restrictions.eq("um.prn", prn));           
            
            ProjectionList pl = Projections.projectionList();
            pl.add(Projections.property("um.prn"), "prn");
            pl.add(Projections.property("um.username"), "username");
            pl.add(Projections.property("um.password"), "password");            
            pl.add(Projections.property("um.name"), "name");
            pl.add(Projections.property("um.mobile"), "mobile");
            pl.add(Projections.property("studentMasters.rno"),"rno");
            pl.add(Projections.property("studentMasters.username"),"username");
            pl.add(Projections.property("studentMasters.div"),"div");
            pl.add(Projections.property("studentMasters.std.std"),"std");
            pl.add(Projections.property("studentMasters.address"),"address");
            pl.add(Projections.property("studentMasters.gender"),"gender");
            pl.add(Projections.property("studentMasters.dob"),"dob");
            pl.add(Projections.property("studentMasters.eduYear"),"eduYear");
            detCri.setProjection(pl);
            detCri.setResultTransformer(Transformers.aliasToBean(Userprofile.class));
            studentList = hibernateTemplate.findByCriteria(detCri);
      }catch(Exception ex){
          ex.printStackTrace();
      }      
     return studentList; 
    }

    public List<Userprofile> getTeacherDetailsByPrn(int prn) {
         List<Userprofile> teacherList=null;
      try{
          DetachedCriteria detCri = DetachedCriteria.forClass(UserMaster.class,"um");
            detCri.createAlias("um.teacherMasters", "teacherMasters");
            detCri.createAlias("um.qualificationMasters", "qualificationMasters");
            detCri.createAlias("um.teacherStddivSubs", "teacherStddivSubs");
            detCri.add(Restrictions.eq("um.prn", prn)); 
            ProjectionList pl = Projections.projectionList();
            pl.add(Projections.property("um.prn"), "prn");
            pl.add(Projections.property("um.username"), "username");
            pl.add(Projections.property("um.password"), "password");      
            pl.add(Projections.property("um.name"), "name");
            pl.add(Projections.property("um.mobile"), "mobile");
            pl.add(Projections.property("teacherMasters.address"),"address");
            pl.add(Projections.property("teacherMasters.gender"),"gender");
            pl.add(Projections.property("teacherMasters.doj"),"doj");
            pl.add(Projections.property("teacherMasters.dob"),"dob");      
            pl.add(Projections.property("teacherStddivSubs.id.std"),"std");
            pl.add(Projections.property("teacherStddivSubs.id.div"),"div");
            pl.add(Projections.property("qualificationMasters.qualificationName"),"qualName");
            detCri.setProjection(pl);
            detCri.setResultTransformer(Transformers.aliasToBean(Userprofile.class));
            teacherList = hibernateTemplate.findByCriteria(detCri);
      }catch(Exception ex){
          ex.printStackTrace();
      }      
     return teacherList; 
    }

    public List<Userprofile> IsUsernameAlreadyExist(String username) {
        return (List<Userprofile>)hibernateTemplate.find(getUsernameDetailsByQry,username);
    }
    
    public List<Userprofile> getsearchStudentFilterCriteriaList(String searchCriteria, String inputCriteria) {
        List<Userprofile> searchStudList=new ArrayList<Userprofile>();       
        DetachedCriteria criteria=DetachedCriteria.forClass(UserMaster.class,"um");
        criteria.createAlias("um.studentMasters", "studentMasters");
        ProjectionList pl = Projections.projectionList();
            pl.add(Projections.property("um.prn"), "prn");
            pl.add(Projections.property("um.name"), "name");
            pl.add(Projections.property("um.mobile"), "mobile");
            pl.add(Projections.property("studentMasters.rno"),"rno");
            pl.add(Projections.property("studentMasters.div"),"div");
            pl.add(Projections.property("studentMasters.std.std"),"std");
            pl.add(Projections.property("studentMasters.address"),"address");
            criteria.setProjection(pl);
            if(!searchCriteria.equals("")){
                if(searchCriteria.equals("Roll No")){
                    criteria.add(Restrictions.ilike("studentMasters.rno",Short.parseShort(inputCriteria)));
                }
                if(searchCriteria.equals("Student Name")){
                    criteria.add(Restrictions.ilike("um.username",inputCriteria,MatchMode.ANYWHERE));
                }
                
                if(searchCriteria.equals("Division")){
                    criteria.add(Restrictions.ilike("studentMasters.div",inputCriteria));
                }
                
                if(searchCriteria.equals("Standard")){
                    criteria.add(Restrictions.ilike("studentMasters.std.std",inputCriteria));
                }
                criteria.setResultTransformer(Transformers.aliasToBean(Userprofile.class));
                searchStudList=hibernateTemplate.findByCriteria(criteria);                
            }
       
        return searchStudList;
    }
    
  public List<Userprofile> getsearchTeacherFilterCriteria(String searchCriteria, String inputCriteria) {
       
      List<Userprofile> searchTeacherList=new ArrayList<Userprofile>();       
      DetachedCriteria criteria=DetachedCriteria.forClass(UserMaster.class,"um");
      criteria.createAlias("um.teacherMasters", "teacherMasters");
      criteria.createAlias("um.teacherStddivSubs", "teacherStddivSubs");
      ProjectionList pl = Projections.projectionList();
      pl.add(Projections.property("um.prn"), "prn");
      pl.add(Projections.property("teacherMasters.username"), "username");
      pl.add(Projections.property("um.name"), "name");
      pl.add(Projections.property("um.mobile"), "mobile");
      pl.add(Projections.property("teacherMasters.address"),"address");
      pl.add(Projections.property("teacherMasters.doj"),"doj");
      criteria.setProjection(pl);
            
      if(!searchCriteria.equals("")){               
                if(searchCriteria.equals("Teacher Name")){
                    criteria.add(Restrictions.ilike("um.username",inputCriteria,MatchMode.ANYWHERE));
                }
                
                if(searchCriteria.equals("Division")){
                    criteria.add(Restrictions.ilike("teacherStddivSubs.id.div",inputCriteria));
                }
                
                if(searchCriteria.equals("Standard")){
                    criteria.add(Restrictions.ilike("teacherStddivSubs.id.std",inputCriteria));
                }
                criteria.setResultTransformer(Transformers.aliasToBean(Userprofile.class));
                searchTeacherList=hibernateTemplate.findByCriteria(criteria);                
            }        
        return searchTeacherList;
    }

    public List<TeacherStddivSubIdBean> getTeacherStdDivSubListByUsername(String username) {
      List<TeacherStddivSubIdBean> teacherList=null;
      try{
            DetachedCriteria detCri = DetachedCriteria.forClass(TeacherStddivSub.class,"ts");           
            detCri.add(Restrictions.eq("ts.id.username", username)); 
            ProjectionList pl = Projections.projectionList();
            pl.add(Projections.property("ts.id.username"), "username");             
            pl.add(Projections.property("ts.id.std"),"std");
            pl.add(Projections.property("ts.id.div"),"div");
            pl.add(Projections.property("ts.id.sub"),"sub");
            detCri.setProjection(pl);
            detCri.setResultTransformer(Transformers.aliasToBean(TeacherStddivSubIdBean.class));
            teacherList = hibernateTemplate.findByCriteria(detCri);
      }catch(Exception ex){
          ex.printStackTrace();
      }      
     return teacherList; 
    }

    @Override
    public void deleteUserFromDB(UserMaster userMaster) {
//        Set<UserRoles> s=userMaster.getUserRoleses();
//        Iterator itr=s.iterator();
//        hibernateTemplate.update(itr.next());
//        hibernateTemplate.
        hibernateTemplate.deleteAll(hibernateTemplate.find("from UserRoles as model where model.userMaster.username=?",userMaster.getUsername()));
        
        hibernateTemplate.delete(userMaster);
    }
    
    private static final String findUserMasterByIdQry="from UserMaster as model where model.username=?";
    @Override
    public List<UserMaster> getUserMasterById(String userName) 
    {
        return hibernateTemplate.find(findUserMasterByIdQry, userName);
    }

    @Override
    public int getStudentCountForClass(String std, String fordiv) {
        return hibernateTemplate.find(getStudentsOfClassQry,std,fordiv).size();
    }

    @Override
    public List<Userprofile> getStudentUserIdsByClass(String std) 
    {
        DetachedCriteria criteria=DetachedCriteria.forClass(UserMaster.class,"um");
        
         ProjectionList pl = Projections.projectionList();
            
         pl.add(Projections.property("um.username"), "username");
            
         criteria.setProjection(pl);
         
         criteria.createAlias("um.studentMasters", "studentMasters");
            
         criteria.add(Restrictions.eq("studentMasters.std",new Std(std)));
            
         criteria.setResultTransformer(Transformers.aliasToBean(Userprofile.class));
            
         return hibernateTemplate.findByCriteria(criteria);                
           
    }
}
