/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.resource;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quick.tim.mobileserviceprovider.bean.Userprofile;
import com.quick.tim.mobileserviceprovider.entity.QualificationMaster;
import com.quick.tim.mobileserviceprovider.entity.QuickLearn;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author rajkirans
 */
public class CreatePerson {

    public static void main(String[] args) throws MalformedURLException, IOException {
      //  authenticatePerson();
        getSubjectBystd("I");
       // getAllStudentList();
       // IsRollNoAlreadyExist("10");
      //  getQualificationList();
    //    getAllTeacherList();
        //getStudentDetailsByPrn(2);
  //      getTeacherDetailsByPrn(6);
        //    authenticatePerson();
//       sendMessage();
//        getMstProp();
//        sendSessionTrack();
//        setImpFlag();
//        fetchNewNsg();
//        setReceiveTime();
//        refreshAddressBook();
        // getMsgBody();
     //downloadAttachment();
       // getMails();
     //  sendMessage(); 
     //   getThreadedMsgBody();
  //     getAttachment();
    }

    private static void authenticatePerson() throws MalformedURLException, IOException {
        try {
            // i.e.: request = "http://example.com/index.php?param1=a&param2=b&param3=c";
                /*
             * URL url = new
             * URL("http://localhost:8084/restWeb/rest/person?firstName=raj?lastName=kiran?email=rajk@sat.com");
             * HttpURLConnection connection = (HttpURLConnection)
             * url.openConnection(); connection.setDoOutput(true);
             * connection.setInstanceFollowRedirects(false);
             * connection.setRequestMethod("POST");
             * connection.setRequestProperty("Content-Type", "text/plain");
             * connection.setRequestProperty("charset", "utf-8");
             * connection.connect();
             */


            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/auth/login");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            try {
                inputJson.put("userName", "sonali");
                inputJson.put("password", "sonali");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }


            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);

            /*
             * if (response.getStatus() != 201) { throw new
             * RuntimeException("Failed : HTTP error code : " +
             * response.getStatus()); }
             */
          //  JSONObject outNObject = null;
            String output = response.getEntity(String.class);
             System.out.println("output="+output);
//            outNObject = new JSONObject(output);
//
//            Type listType = new TypeToken<ArrayList<Whatsnew>>() {
//            }.getType();
//            List<Whatsnew> yourClassList = new Gson().fromJson(outNObject.getString(GlobalConstants.WHATSNEW), listType);
//            for (Whatsnew w : yourClassList) {
//                System.out.println("Notification :=" + w.getDisplaynotification());
//            }
        } catch (Exception ex) {
            Logger.getLogger(CreatePerson.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    public static List<Userprofile> getAllStudentList() {
         List<Userprofile> studentList = null;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/UserMaster/getAllstudentList");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);
            
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);

            Type listType = new TypeToken<ArrayList<Userprofile>>() {
            }.getType();
            
            studentList= new Gson().fromJson(outNObject.getString(GlobalConstants.STUDENTLIST), listType);
            System.out.println("studentList"+studentList);
        } catch (JSONException ex) {
           // Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return studentList;
            
    }
    
    
     private static boolean IsRollNoAlreadyExist(String rollNo) {
        boolean isRollNoExist = false;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/UserMaster/IsRollNoAlreadyExist");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            try {
                inputJson.put("rollNo",rollNo);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }


            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);

            /*
             * if (response.getStatus() != 201) { throw new
             * RuntimeException("Failed : HTTP error code : " +
             * response.getStatus()); }
             */

            String output = response.getEntity(String.class);
            System.out.println("output=" + output);


            JSONObject response1 = null;

            response1 = new JSONObject(output);

            if (response1.getBoolean(GlobalConstants.ISROLLNOEXIST)) {
               isRollNoExist = true;
              // Notification.show("Roll No is already exist",Notification.Type.WARNING_MESSAGE); 
                System.out.println("Roll No is already exist");
            }else{
                isRollNoExist = false;
                System.out.println("Roll No is not exist");
            } 
        } catch (JSONException ex) {
           // Logger.getLogger(DashboardUI.class.getName()).log(Level.SEVERE, null, ex);
         
        }
        
           return isRollNoExist;
    }
     
      public static List<QualificationMaster> getQualificationList() {
         List<QualificationMaster> qualificationList = null;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/UserMaster/getQualificationList");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);
            
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);

            Type listType = new TypeToken<ArrayList<QualificationMaster>>() {
            }.getType();
            
            qualificationList= new Gson().fromJson(outNObject.getString(GlobalConstants.QUALIFICATIONLIST), listType);
        } catch (JSONException ex) {
           // Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return qualificationList;
            
    }
      
      
      public static List<Userprofile> getAllTeacherList() {
         List<Userprofile> teacherList = null;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/UserMaster/getAllTeacherList");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);
            
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);

            Type listType = new TypeToken<ArrayList<Userprofile>>() {
            }.getType();
            
            teacherList= new Gson().fromJson(outNObject.getString(GlobalConstants.TEACHERLIST), listType);
        } catch (JSONException ex) {
           // Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teacherList;
            
    }
      
      
  public static List<Userprofile> getStudentDetailsByPrn(int prn) {
         List<Userprofile> studentList = null;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/UserMaster/getStudentDetailsByPrn");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            try {
                inputJson.put("prn",prn);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }            
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);
            
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);

            Type listType = new TypeToken<ArrayList<Userprofile>>() {
            }.getType();
            
            studentList= new Gson().fromJson(outNObject.getString(GlobalConstants.student), listType);
        } catch (JSONException ex) {
           // Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return studentList;
            
    }
  
  
   public static List<Userprofile> getTeacherDetailsByPrn(int prn) {
         List<Userprofile> teacherList = null;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/UserMaster/getTeacherDetailsByPrn");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
            try {
                inputJson.put("prn",prn);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }            
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);
            
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);

            Type listType = new TypeToken<ArrayList<Userprofile>>() {
            }.getType();
            
            teacherList= new Gson().fromJson(outNObject.getString(GlobalConstants.teacher), listType);
        } catch (JSONException ex) {
           // Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teacherList;
            
    }
   
   
    public static List<QuickLearn> getSubjectBystd(String std) {
         List<QuickLearn> subjectList = null;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8084/titali/rest/StandardMaster/getSubjectBystd");
            //String input = "{\"userName\":\"raj\",\"password\":\"FadeToBlack\"}";
            JSONObject inputJson = new JSONObject();
             try{           
                inputJson.put("std", std);  
             }catch(Exception ex){
                 
             }
            
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputJson);
            
            JSONObject outNObject = null;
            String output = response.getEntity(String.class);
            outNObject = new JSONObject(output);

            Type listType = new TypeToken<ArrayList<QuickLearn>>() {
            }.getType();
            
            subjectList= new Gson().fromJson(outNObject.getString(GlobalConstants.STANDARDLIST), listType);
        } catch (JSONException ex) {
          //  Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjectList;
            
    }
}
