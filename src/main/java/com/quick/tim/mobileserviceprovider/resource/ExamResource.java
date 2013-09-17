/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quick.tim.mobileserviceprovider.bean.ExamBean;
import com.quick.tim.mobileserviceprovider.bean.ExamQueAnsBean;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import com.quick.tim.mobileserviceprovider.services.ExamService;
import com.sun.jersey.api.core.ResourceContext;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author rajkiran
 */
@Component
@Path(GlobalConstants.EXAMRESOURCE)
public class ExamResource {
    @Autowired
    private ExamService examService;
    private static final String getExamList="getExamList";
    private static final String getExamDetailsById = "getExamDetailsById";
    private static final String GETEXAMQUESTIONBYID = "getExamQuestionById";
    private static final String createExam = "createExam";
    private static final String deleteExam = "deleteExam";
    private static final String getPresentStudentsForExam="getPresentStudentsForExam";
    private static final String  getAbsentStudentsForExam ="getAbsentStudentsForExam";
    private static final String submitQuestionAnsResponse = "submitQuestionAnsResponse";
      
    @Context
    private ResourceContext resourceContext;

    // Basic "is the service running" test
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String respondAsReady() {
        return "Demo service is ready!";
    }

    @Path(getExamList)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCreatedExamList(JSONObject inputRequest) throws JSONException {


        System.out.println("userTrack=" + inputRequest);
        JSONObject response = new JSONObject();
        List<ExamBean> examList = examService.getExamList(inputRequest.getString("std"),inputRequest.getString("div"));
        Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
        String json = gson.toJson(examList);  
        response.put(GlobalConstants.EXAMLIST, json);     
        return response;       

      
    }
    
    
    @Path(getExamDetailsById)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getExamDetailsById(JSONObject inputRequest) throws JSONException {

       System.out.println("userTrack=" + inputRequest);
       
       JSONObject response = new JSONObject();

        try 
        {
            List<ExamBean> examList = examService.getExamDetailsById(inputRequest.getInt("exmId"));
            Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();
            String json = gson.toJson(examList);
            response.put(GlobalConstants.EXAMLIST, json);
            response.put(GlobalConstants.STATUS, GlobalConstants.YES);
        } 
        catch (Exception e) 
        {
            response.put(GlobalConstants.STATUS, GlobalConstants.NO);
            e.printStackTrace();
        }
         
        return response;

      
    }
    
    
    
    @Path(GETEXAMQUESTIONBYID)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getExamQuestionById(JSONObject inputRequest) throws JSONException {

        System.out.println("userTrack=" + inputRequest);
        JSONObject response = new JSONObject();
        List<ExamQueAnsBean> queList = examService.getExamQuestionById(inputRequest.getInt("exmId"),inputRequest.getBoolean("isSendAns"));
        Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
        String json = gson.toJson(queList);  
        response.put(GlobalConstants.EXAMLIST, json);     
        return response;       

      
    }
    
    
    @Path(createExam)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject createExam(JSONObject inputRequest) throws JSONException {

        JSONObject response = new JSONObject();

        try 
        {
            System.out.println("userTrack=" + inputRequest);
            examService.createExam(inputRequest);
            response.put(GlobalConstants.STATUS, GlobalConstants.YES);
            
        } catch (Exception e) 
        {
            response.put(GlobalConstants.STATUS, GlobalConstants.NO);
            e.printStackTrace();
        }
        
//        List<ExamQueAnsBean> queList = examService.getExamQuestionById(inputRequest.getInt("exmId"));
//        Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
//        String json = gson.toJson(queList);  
//        response.put(GlobalConstants.EXAMLIST, json);     
        return response;       

    }
    
    
    @Path(deleteExam)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteExam(JSONObject inputRequest)
    {
        JSONObject response = new JSONObject();
        try {
            System.out.println("userTrack=" + inputRequest);
            
            examService.deleteExam(inputRequest);       
            response.put(GlobalConstants.STATUS, GlobalConstants.YES);

            
        } 
        catch (JSONException ex) {
            ex.printStackTrace();
            try {
                response.put(GlobalConstants.STATUS, GlobalConstants.NO);
            } catch (JSONException ex1) {
                ex1.printStackTrace();
            }
        }   
        return response;

    }
    
@Path(getPresentStudentsForExam)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getPresentStudentsForExamById(JSONObject inputRequest) throws JSONException {


        System.out.println("getPresentStudentsForExamById () $$$$$ userTrack=" + inputRequest);
        JSONObject response = new JSONObject();
        try {
            List<ExamBean> examList = examService.getPresentStudentsForExam(inputRequest.getInt("examId"));
            Gson gson = new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();
            String json = gson.toJson(examList);
            response.put(GlobalConstants.EXAMLIST, json);
            response.put(GlobalConstants.STATUS, GlobalConstants.YES);

        } catch (Exception e) {
            response.put(GlobalConstants.STATUS, GlobalConstants.NO);
            e.printStackTrace();
        }

        return response;


    }    

@Path(getAbsentStudentsForExam)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getAbsentStudentsForExamById(JSONObject inputRequest) throws JSONException {


        System.out.println("getPresentStudentsForExamById () $$$$$ userTrack=" + inputRequest);
        JSONObject response = new JSONObject();
        try {
            List<ExamBean> examList = examService.getAbsentStudentsForExam(inputRequest.getInt("examId"),inputRequest.getString("forstd"),inputRequest.getString("fordiv"));
            Gson gson = new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();
            String json = gson.toJson(examList);
            response.put(GlobalConstants.EXAMLIST, json);
            response.put(GlobalConstants.STATUS, GlobalConstants.YES);

        } catch (Exception e) {
            response.put(GlobalConstants.STATUS, GlobalConstants.NO);
            e.printStackTrace();
        }

        return response;


    }    


@Path(submitQuestionAnsResponse)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject submitQuestionAnsResponse(JSONObject inputRequest) throws JSONException {

        JSONObject response = new JSONObject();

        try 
        {
            System.out.println("userTrack=" + inputRequest);
            examService.submitQuestionAnsResponse(inputRequest);
            response.put(GlobalConstants.STATUS, GlobalConstants.YES);
            
        } catch (Exception e) 
        {
            response.put(GlobalConstants.STATUS, GlobalConstants.NO);
            e.printStackTrace();
        }
        
//        List<ExamQueAnsBean> queList = examService.getExamQuestionById(inputRequest.getInt("exmId"));
//        Gson gson=  new GsonBuilder().setDateFormat(GlobalConstants.gsonTimeFormat).create();       
//        String json = gson.toJson(queList);  
//        response.put(GlobalConstants.EXAMLIST, json);     
        return response;       

    }
    
   
    
}
