/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author prashantk
 */
package com.quick.tim.mobileserviceprovider.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.quick.tim.mobileserviceprovider.bean.TeacherStddivSubIdBean;
import com.quick.tim.mobileserviceprovider.bean.Userprofile;
import com.quick.tim.mobileserviceprovider.entity.*;
import com.quick.tim.mobileserviceprovider.services.*;
import com.quick.tim.mobileserviceprovider.utilities.DateUtil;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
@Path(GlobalConstants.UserMasterResource)
public class UserMasterResource {
    @Autowired
    private  UserMasterService userMasterService;
    @Autowired
    private StudentService studentService;
   
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private QualificationService qualificationService ;

    private List<TeacherStddivSubIdBean> teacherSubAssociationList;
    
    private static final String saveStudent="saveStudent";
    private static final String getUserProfile="getUserProfile";
    private static final String getTeacherProfile="getTeacherProfile";
    private static final String saveTeacher = "saveTeacher";   
    private static final String getAllstudentList="getAllstudentList";
    private static final String getQualificationList="getQualificationList";
    private static final String IsRollNoAlreadyExist="IsRollNoAlreadyExist";
    private static final String getAllTeacherList="getAllTeacherList";
    private static final String getStudentDetailsByPrn="getStudentDetailsByPrn";
    private static final String getTeacherDetailsByPrn="getTeacherDetailsByPrn";
    private static final String IsUsernameAlreadyExist="IsUsernameAlreadyExist";
    private static final String getsearchStudentFilterCriteriaList="getsearchStudentFilterCriteriaList";
    private static final String getsearchTeacherFilterCriteria="getsearchTeacherFilterCriteria";
    private static final String getTeacherStdDivSubListByUsername="getTeacherStdDivSubListByUsername"  ;
    private static final String deleteStudentFromDB ="deleteStudentFromDB";
     private static final String deleteTeacherFromDB ="deleteTeacherFromDB";
    @Path(getUserProfile)
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserProfile(JSONObject inputRequest) throws JSONException {
        List<Userprofile> userlist=userMasterService.getUserProfile(inputRequest.getString(GlobalConstants.userName));
        Gson gson = new Gson();
        String jsonStringUserProfile = gson.toJson(userlist);  
        return jsonStringUserProfile;
        
    }
    
    @Path(getTeacherProfile)
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getTeacherProfile(JSONObject inputRequest) throws JSONException {
        List<Userprofile> userlist=userMasterService.getTeacherProfile(inputRequest.getString(GlobalConstants.userName));
        Gson gson = new Gson();
        String jsonStringUserProfile = gson.toJson(userlist);  
        return jsonStringUserProfile;
        
    }
    
       
    @Path(saveStudent)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject save(JSONObject inputRequest) throws JSONException, ParseException {


        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();       
        
        
        UserMaster userMaster=new UserMaster();
        String prn=inputRequest.getString("prn");
        if(!prn.equals("null")){
             userMaster.setPrn(Integer.parseInt(prn));
        }else{
             userMaster.setPrn(getMaxPrn()); 
        }
        
        userMaster.setMobile(inputRequest.getLong("phnumber"));
        userMaster.setCreationdate(Long.valueOf("1234567890"));
        userMaster.setName(inputRequest.getString("name"));
        userMaster.setUsername(inputRequest.getString("username"));
        userMaster.setPassword(inputRequest.getString("password"));       
        userMaster.setStudentMasters(getStudentmaster(userMaster,inputRequest));
        //userMaster.setQualificationMasters(null);
        //userMaster.setSubs(null);        
        userMaster.setStds(getStandardDetails(inputRequest));
        //userMaster.setTeacherMasters(null);
        //userMaster.setTeacherStddivSubs(null);
        associateUserRole(userMaster,"student");
        try {
            studentService.saveStudent(userMaster);
        } catch (Exception ex) {
            Logger.getLogger(PersonResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    
   private Set<StudentMaster> getStudentmaster(UserMaster userMaster, JSONObject inputRequest) throws ParseException {
       Set<StudentMaster> sms=new HashSet<StudentMaster>(); 
       try {
           
            StudentMaster master=new StudentMaster();
              master.setRno(Short.valueOf(inputRequest.getString("rollNo")));
              master.setAddDate(new Date());
              master.setAddress(inputRequest.getString("address"));
              master.setEduYear(inputRequest.getString("edu_year"));
             
              if((inputRequest.getString("gender")).equals("Male")){
                 master.setGender('M');
              }else{
                 master.setGender('F');
              }              
              
              master.setUsername(userMaster.getUsername());
              master.setUploadBy(null);
              master.setUploadCount(null);
              master.setDiv(inputRequest.getString("division"));
              master.setStd(getStudentStdDetails(inputRequest));
              master.setDob(DateUtil.stringToDate(inputRequest.getString("dob")));
              master.setUserMaster(userMaster);
              sms.add(master);
           
        } catch (JSONException ex) {
            Logger.getLogger(PersonResource.class.getName()).log(Level.SEVERE, null, ex);
        }
         return sms;
    }
   
   private Std getStudentStdDetails(JSONObject inputRequest) {
        Std std=new Std(); 
        try {           
            std.setStd(inputRequest.getString("standard"));        
        } catch (JSONException ex) {
            Logger.getLogger(PersonResource.class.getName()).log(Level.SEVERE, null, ex);
        }
       return std;
    }
     
   private int getMaxPrn() {
        return studentService.getMaxPrn();
    }

   
    
    
    
    //======================================Teacher  ======================================================
    
    
    
    @Path(saveTeacher)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject saveTeacher(JSONObject inputRequest) throws JSONException, ParseException {


        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();       
        
        
        UserMaster userMaster=new UserMaster();  
        
        String prn=inputRequest.getString("prn");
        if(!prn.equals("null")){
            userMaster.setPrn(Integer.parseInt(prn));
        }else{
            userMaster.setPrn(getMaxPrn()); 
        }
       
        userMaster.setMobile(inputRequest.getLong("mobile"));
        userMaster.setCreationdate(Long.valueOf("1234567890"));
        userMaster.setName(inputRequest.getString("name"));
        userMaster.setUsername(inputRequest.getString("username"));
        userMaster.setPassword(inputRequest.getString("password"));       
        userMaster.setStudentMasters(null);
        userMaster.setQualificationMasters(getTeacherQualficationMasterDetails(inputRequest));
        userMaster.setSubs(null);   
        
        
        Type listType = new TypeToken<ArrayList<TeacherStddivSubIdBean>>() {
            }.getType();
        
        teacherSubAssociationList= new Gson().fromJson(inputRequest.getString("teacherSubjson"), listType);
        
        
        userMaster.setStds(getTeacherStdDetails(teacherSubAssociationList));
        userMaster.setTeacherMasters(getTeacherMasterDetails(userMaster,inputRequest));
        userMaster.setTeacherStddivSubs(getTeacherStdDivSubDetails(teacherSubAssociationList,userMaster));
        associateUserRole(userMaster,"teacher");
        
        try {
            teacherService.saveTeacher(userMaster);
        } catch (Exception ex) {
            Logger.getLogger(PersonResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    private Set<QualificationMaster> getTeacherQualficationMasterDetails(JSONObject inputRequest) {
        Set<QualificationMaster> qualificationMasters=new HashSet<QualificationMaster>();
        QualificationMaster  master=new QualificationMaster();
        try {
            master.setQualificationName(inputRequest.getString("qualName"));
            qualificationMasters.add(master);
        } catch (JSONException ex) {
            Logger.getLogger(UserMasterResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return qualificationMasters;
                
    }

    private Set<Sub> getSubjectDetails(JSONObject inputRequest) {
        Set<Sub> subs =new HashSet<Sub>();
        Sub s=new Sub();
        s.setSub(null);            
        subs.add(s);
        return subs;
    }

    private Set<Std> getStandardDetails(JSONObject inputRequest) {
       Set<Std>  stds=new HashSet<Std>();
       Std s=new Std();
        try {
            s.setStd(inputRequest.getString("standard"));
            stds.add(s);
        } catch (JSONException ex) {
            Logger.getLogger(UserMasterResource.class.getName()).log(Level.SEVERE, null, ex);
        }
       return  stds;
    }

    private Set<TeacherMaster> getTeacherMasterDetails(UserMaster userMaster, JSONObject inputRequest) throws ParseException {
        Set<TeacherMaster> teacherMasters=new HashSet<TeacherMaster>();
        TeacherMaster master=new TeacherMaster();
        try {
            master.setUsername(inputRequest.getString("username"));
            master.setAddress(inputRequest.getString("address"));
//            master.setCtClass(inputRequest.getString("IsCtclass"));
            master.setCtClass("Yes");
            master.setDob(DateUtil.stringToDate(inputRequest.getString("dob")));
            master.setDoj(DateUtil.stringToDate(inputRequest.getString("doj")));

            if((inputRequest.getString("gender")).equals("Male")){
                 master.setGender('M');
            }else{
                 master.setGender('F');
            }              
           
             master.setIsClassTeacher('Y');
//            if((inputRequest.getString("IsClassTeacher")).equals("Yes")){
//                 master.setIsClassTeacher('Y');
//            }else{
//                 master.setIsClassTeacher('N');
//            }
           
            master.setIsDeleted("N");
            master.setUserMaster(userMaster);
            teacherMasters.add(master);
        } catch (JSONException ex) {
            Logger.getLogger(UserMasterResource.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        return teacherMasters;
    }

    private Set<TeacherStddivSub> getTeacherStdDivSubDetails(List<TeacherStddivSubIdBean> teacherStddivSubIdBeanList,UserMaster userMaster) {
        Set<TeacherStddivSub> teacherStddivSubs=new HashSet<TeacherStddivSub>();         
        for(TeacherStddivSubIdBean bean:teacherStddivSubIdBeanList){
             TeacherStddivSub stddivSub=new TeacherStddivSub();
             stddivSub.setId(getTeacherStddivSubIdDetails(bean));
             stddivSub.setStd(getTeacherStddivSubInfo(bean));
             stddivSub.setSub(getTeacherSubDetails(bean));
             stddivSub.setUserMaster(userMaster);
             teacherStddivSubs.add(stddivSub);
        }        
       
        return teacherStddivSubs;
                
                
    }

    private TeacherStddivSubId getTeacherStddivSubIdDetails(TeacherStddivSubIdBean bean) {
           TeacherStddivSubId stddivSubId=new  TeacherStddivSubId();
       // TeacherStddivSubIdBean bean;  
       
            stddivSubId.setDiv(bean.getDiv());
            System.out.println(""+stddivSubId.getDiv());
            
            stddivSubId.setStd(bean.getStd());
            System.out.println(""+stddivSubId.getStd());
            
            stddivSubId.setSub(bean.getSub());
            System.out.println(""+stddivSubId.getSub());
            
            stddivSubId.setUsername(bean.getUsername());
            System.out.println(""+stddivSubId.getUsername());
        
         return stddivSubId;
        
//        Iterator it=teacherStddivSubIdBeanList.iterator();
//        while(it.hasNext()){
//            bean=(TeacherStddivSubIdBean) it.next();
//            stddivSubId.setDiv(bean.getDiv());
//            stddivSubId.setStd(bean.getStd());
//            stddivSubId.setSub(bean.getSub());
//            stddivSubId.setUsername(bean.getUsername());
//        }
//        try {
//            stddivSubId.setDiv(inputRequest.getString("division"));
//            stddivSubId.setStd(inputRequest.getString("standard"));
//            stddivSubId.setSub(inputRequest.getString("subject"));
//            stddivSubId.setUsername(userMaster.getUsername());
//        } catch (JSONException ex) {
//            Logger.getLogger(UserMasterResource.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
       
    }

    private Sub getTeacherSubDetails(TeacherStddivSubIdBean bean) {
        Sub sub=new Sub();
        sub.setSub(bean.getSub());     
        
        
//        Iterator it=sublist.iterator();
//        while(it.hasNext()){
//           sub.setSub(""+it.next());          
//        }        
//        try {
//            sub.setSub(inputRequest.getString("subject"));
//        } catch (JSONException ex) {
//            Logger.getLogger(UserMasterResource.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return sub;
    }    
    
    private Set<Std> getTeacherStdDetails(List<TeacherStddivSubIdBean> stdList) {
       Set<Std>  stds=new HashSet<Std>();
       Std s=new Std();       
       for(TeacherStddivSubIdBean bean:stdList){
          s.setStd(bean.getStd());
          stds.add(s); 
       }
//       Iterator it=stdList.iterator();
//       while(it.hasNext()){
//           s.setStd(""+it.next());
//           stds.add(s);
//       }
//        try {
//            s.setStd(inputRequest.getString("standard"));
//            stds.add(s);
//        } catch (JSONException ex) {
//            Logger.getLogger(UserMasterResource.class.getName()).log(Level.SEVERE, null, ex);
//        }
       return  stds;
    }
    
     private Std getTeacherStddivSubInfo(TeacherStddivSubIdBean bean) {
        Std s=new Std();       
        s.setStd(bean.getStd());   
      
//        Iterator it=teacherStddivSublist.iterator();
//        while(it.hasNext()){
//           s.setStd(""+it.next());          
//       }
        return s;
    } 
    
 //***************************************all student list*************************************************//
   @Path(getAllstudentList)
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public JSONObject getAllstudentList(JSONObject inputRequest) throws JSONException {
        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();
       
        List<Userprofile> allStudentList=userMasterService.getAllStudentList();
        Gson gson = new Gson();
        String json = gson.toJson(allStudentList);  
        response.put(GlobalConstants.STUDENTLIST, json);     
        return response;       
    }
 
 //***************************************Qualification list*************************************************//
    
   @Path(getQualificationList)
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public JSONObject getQualificationList(JSONObject inputRequest) throws JSONException {
        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();
        
        List<QualificationMaster> qualificationList=qualificationService.getQualificationList();
        Gson gson = new Gson();
        String json = gson.toJson(qualificationList);  
        response.put(GlobalConstants.QUALIFICATIONLIST, json);     
        return response;       
    }  
   
 //***************************************Roll No checking*************************************************//  
   
   @Path(IsRollNoAlreadyExist)
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public JSONObject IsRollNoAlreadyExist(JSONObject inputRequest) throws JSONException {
        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();
        
        boolean rollNoResult=userMasterService.IsRollNoAlreadyExist(inputRequest.getString("rollNo"));        
        response.put(GlobalConstants.ISROLLNOEXIST,rollNoResult);                   
        return response;       
    }  
   
   
   //***************************************all teacher list*************************************************//
   @Path(getAllTeacherList)
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public JSONObject getAllTeacherList(JSONObject inputRequest) throws JSONException {
        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();
        List<Userprofile> teacherList=userMasterService.getAllTeacherList();
        Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();
        String json = gson.toJson(teacherList);  
        response.put(GlobalConstants.TEACHERLIST, json);     
        return response;       
    }  
   
   @Path(getStudentDetailsByPrn)
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public JSONObject getStudentDetailsByPrn(JSONObject inputRequest) throws JSONException {
        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();
       
        int prn=Integer.parseInt(inputRequest.getString("prn"));
        List<Userprofile> studentList=userMasterService.getStudentDetailsByPrn(prn);
        Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
        String json = gson.toJson(studentList);  
        response.put(GlobalConstants.student, json);     
        return response;       
    }
   
   @Path(getTeacherDetailsByPrn)
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public JSONObject getTeacherDetailsByPrn(JSONObject inputRequest) throws JSONException {
        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();
       
        int prn=Integer.parseInt(inputRequest.getString("prn"));
        List<Userprofile> teacherList=userMasterService.getTeacherDetailsByPrn(prn);
        Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();   
        String json = gson.toJson(teacherList);  
        response.put(GlobalConstants.teacher, json);     
        return response;       
    }
 
   @Path(IsUsernameAlreadyExist)
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public JSONObject  IsUsernameAlreadyExist(JSONObject inputRequest) throws JSONException {
        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();
        
        boolean usernameResult=userMasterService.IsUsernameAlreadyExist(inputRequest.getString("username"));        
        response.put(GlobalConstants.ISUSERNAMEEXIST,usernameResult);                   
        return response;       
    } 
   
   
   @Path(getsearchStudentFilterCriteriaList)
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public JSONObject getsearchStudentFilterCriteriaList(JSONObject inputRequest) throws JSONException {
        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();
        String searchCriteria=inputRequest.getString("searchCriteria");
        String inputCriteria=inputRequest.getString("searchValue");
        List<Userprofile> studentList=userMasterService.getsearchStudentFilterCriteriaList(searchCriteria,inputCriteria);
        Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();   
        String json = gson.toJson(studentList);  
        response.put(GlobalConstants.STUDENTLIST, json);     
        return response;       
    }  
   
    @Path(getsearchTeacherFilterCriteria)
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public JSONObject getsearchTeacherFilterCriteria(JSONObject inputRequest) throws JSONException {
        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();
        String searchCriteria=inputRequest.getString("searchCriteria");
        String inputCriteria=inputRequest.getString("searchValue");
        List<Userprofile> teacherList=userMasterService.getsearchTeacherFilterCriteria(searchCriteria,inputCriteria);
        Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();   
        String json = gson.toJson(teacherList);  
        response.put(GlobalConstants.TEACHERLIST, json);     
        return response;       
    }
    
    
   @Path(getTeacherStdDivSubListByUsername)
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public JSONObject getTeacherStdDivSubListByUsername(JSONObject inputRequest) throws JSONException {
        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();
       
        String username=(inputRequest.getString("username"));
        List<TeacherStddivSubIdBean> teacherList=userMasterService.getTeacherStdDivSubListByUsername(username);
        Gson gson = new Gson();
        String json = gson.toJson(teacherList);  
        response.put(GlobalConstants.teacherStdDivSubIdList, json);  
        return response;
    }
   
   
   
   
   
    @Path(deleteStudentFromDB)
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public JSONObject deleteStudentFromDB(JSONObject inputRequest){
         JSONObject response =  new JSONObject();
     try {
            System.out.println("userTrack="+inputRequest);
            String username=(inputRequest.getString("username"));
            int prn = (inputRequest.getInt("prn"));
            //UserMaster um = new UserMaster(username, prn);
            UserMaster um = new UserMaster();
            um.setUsername(username);
            userMasterService.deleteUserFromDB(um);
            response.put(GlobalConstants.STATUS, GlobalConstants.YES);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return response;
    }
    
    
    
    
    
   @Path(deleteTeacherFromDB)
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public JSONObject deleteTeacherFromDB(JSONObject inputRequest) {
        JSONObject response =  new JSONObject();
        try {
            System.out.println("userTrack="+inputRequest);
            String username=(inputRequest.getString("username"));
            int prn = (inputRequest.getInt("prn"));
            UserMaster um = new UserMaster(username, prn);
            userMasterService.deleteUserFromDB(um);
            response.put(GlobalConstants.STATUS, GlobalConstants.YES);
           
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
         return response;
    }

    private void associateUserRole(UserMaster userMaster,String roleName) {
        
        RoleMaster role=new RoleMaster();
        role.setRoleName(roleName);
        
        UserRoles userRoleAssociation = new UserRoles();
        userRoleAssociation.setUserMaster(userMaster);
        userRoleAssociation.setRoleMaster(role);
        
        Set<UserRoles> userRolesSet =  new HashSet<UserRoles>();
         userRolesSet.add(userRoleAssociation);         
         
         userMaster.setUserRoleses(userRolesSet);
    }
}



    