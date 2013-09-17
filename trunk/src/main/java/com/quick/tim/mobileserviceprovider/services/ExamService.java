/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quick.tim.mobileserviceprovider.DAO.ExamDao;
import com.quick.tim.mobileserviceprovider.DAO.UserMasterDao;
import com.quick.tim.mobileserviceprovider.bean.ExamBean;
import com.quick.tim.mobileserviceprovider.bean.ExamQueAnsBean;
import com.quick.tim.mobileserviceprovider.entity.ExamEntry;
import com.quick.tim.mobileserviceprovider.entity.ExamQuestionsAnswers;
import com.quick.tim.mobileserviceprovider.entity.ExamStudentResponse;
import com.quick.tim.mobileserviceprovider.entity.ExamStudentResponseId;
import com.quick.tim.mobileserviceprovider.entity.Std;
import com.quick.tim.mobileserviceprovider.entity.Sub;
import com.quick.tim.mobileserviceprovider.entity.UserMaster;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import com.quick.tim.mobileserviceprovider.resource.PersonResource;
import com.quick.tim.mobileserviceprovider.utilities.DateUtil;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author rajkiran
 */
@Component
public class ExamService {
    @Autowired
    private ExamDao examDao;
    
    @Autowired
    private UserMasterDao userDao;
            
            
    public List<ExamBean> getExamList(String std,String div){
        return examDao.getExamList(std,div);
    }

    public List<ExamBean> getExamDetailsById(int exmId) {
        return examDao.getExamDetailsById(exmId);
    }

    public List<ExamQueAnsBean> getExamQuestionById(int exmId, boolean isSendAns) {
        return examDao.getExamQuestionById(exmId,isSendAns);
      
    }

    public void createExam(JSONObject inputRequest) {
        ExamEntry entry = getExamEntry(inputRequest);
        examDao.createExam(entry);
    }

    private ExamEntry getExamEntry(JSONObject inputRequest) {
         ExamEntry entry =null;
        try {
           entry = new ExamEntry();
           entry.setCreatedBy(inputRequest.getString("createdBy"));
           entry.setCreationDt(new Date());
           entry.setEndDt(DateUtil.stringToDate(inputRequest.getString("endDt")));
           entry.setStartDt(DateUtil.stringToDate(inputRequest.getString("startDt")));
           entry.setExName(inputRequest.getString("exName"));
           entry.setExType(inputRequest.getInt("exType"));
           entry.setFordiv(inputRequest.getString("fordiv"));
           entry.setNoOfQuestions(inputRequest.getInt("noOfQuestions"));
           entry.setPassingMarks(inputRequest.getInt("passingMarks"));
           entry.setContestLine(inputRequest.getString("contestline"));
           entry.setSub(new Sub(inputRequest.getString("sub")));
           entry.setTopic(inputRequest.getString("topic"));//
           entry.setTotalMarks(entry.getNoOfQuestions()*Integer.parseInt(inputRequest.getString("marksForQuestion")));
           
           entry.setExamQuestionsAnswerses(getExamQuestions(inputRequest,entry));
           entry.setStd(getStandard(inputRequest));
           
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
         return entry;
    }


    private Set<ExamQuestionsAnswers> getExamQuestions(JSONObject inputRequest,ExamEntry entry) {
        Set<ExamQuestionsAnswers> questionsAnswerses =new HashSet<ExamQuestionsAnswers>();
//        try {
//            Type listType = new TypeToken<Set<ExamQuestionsAnswers>>() {}.getType();
//            questionsAnswerses = new Gson().fromJson(inputRequest.getString(GlobalConstants.EXAMQUESTIONLIST), listType);
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }
        
      List<ExamBean>  examBeans = null;
        try {
            Type listType = new TypeToken<ArrayList<ExamBean>>() {}.getType();
            examBeans = new Gson().fromJson(inputRequest.getString(GlobalConstants.EXAMQUESTIONLIST), listType);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        
        for(ExamBean eb:examBeans){
            ExamQuestionsAnswers examQuestionsAnswers = new ExamQuestionsAnswers();
            examQuestionsAnswers.setOption1(eb.getOption1());
            examQuestionsAnswers.setOption2(eb.getOption2());
            examQuestionsAnswers.setOption3(eb.getOption3());
            examQuestionsAnswers.setOption4(eb.getOption4());
            examQuestionsAnswers.setAns(eb.getAns());
            examQuestionsAnswers.setQuestion(eb.getQueString());
            examQuestionsAnswers.setMarksForQuestion(eb.getMarksPerQuestion());
            examQuestionsAnswers.setQuestionType(eb.getQueType());
            examQuestionsAnswers.setExamEntry(entry);
            questionsAnswerses.add(examQuestionsAnswers);
        }
        
        
        return questionsAnswerses;
    }
    
    

    private Std getStandard(JSONObject inputRequest) {
        Std std=new Std(); 
        try {           
            std.setStd(inputRequest.getString("std"));        
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
       return std;
    }

    public void deleteExam(JSONObject inputRequest) throws JSONException 
    {
        
            ExamEntry entry = new ExamEntry(Integer.parseInt(inputRequest.getString("examId")));
            examDao.deleteExam(entry);
        
    }

    public List<ExamBean> getPresentStudentsForExam(int examId) 
    {
        return examDao.getPresentStudentsForExam(examId);
    }

    public List<ExamBean> getAbsentStudentsForExam(int examId,String std,String div) {
        return examDao.getAbsentStudentsForExam(examId,std,div);
    }

    public void submitQuestionAnsResponse(JSONObject inputRequest) 
    {
         Set examStudentResponseSet = getQuestionAnsResponse(inputRequest);
         examDao.sumbmitStudExamResponse(examStudentResponseSet);
    }
    
    private Set<ExamStudentResponse> getQuestionAnsResponse(JSONObject inputRequest) {
        Set<ExamStudentResponse> questionsAnswerses =new HashSet<ExamStudentResponse>();
        
        List<ExamEntry> examEntryList=null;
        ExamEntry examEntry=null;
        List<UserMaster> userMasterList=null;
        UserMaster userMaster=null;
        
        HashMap<Integer,ExamQuestionsAnswers> questionAnswersMap =null;
        
//        try {
//            Type listType = new TypeToken<Set<ExamQuestionsAnswers>>() {}.getType();
//            questionsAnswerses = new Gson().fromJson(inputRequest.getString(GlobalConstants.EXAMQUESTIONLIST), listType);
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }
        
      List<ExamBean>  examBeansList = null;
        try 
        {
            Type listType = new TypeToken<ArrayList<ExamBean>>() {}.getType();
            examBeansList = new Gson().fromJson(inputRequest.getString(GlobalConstants.EXAMQUESTIONLIST), listType);
            
            if(examBeansList!=null && examBeansList.size()>0)
            {
                examEntryList=examDao.getExamEntryById(inputRequest.getInt("examId"));                
                userMasterList=userDao.getUserMasterById(inputRequest.getString("userId"));
                examEntry=examEntryList.get(0);
                userMaster=userMasterList.get(0);
                questionAnswersMap=getQuestionAnswersMap(examEntry);
            }
        } 
        catch (JSONException ex) 
        {
            ex.printStackTrace();
        }
        
        for(ExamBean eb:examBeansList)
        {
            ExamStudentResponse examStudResponse = new ExamStudentResponse();
            examStudResponse.setExamEntry(examEntry);
            examStudResponse.setUserMaster(userMaster);
            examStudResponse.setId(new ExamStudentResponseId(examEntry.getExId(), userMaster.getUsername(), eb.getQuestionId()));
            examStudResponse.setObjAns(eb.getAns());
            
            //handling objective type question answers
//            if(eb.getQueType()==1)
//            {
                if(questionAnswersMap.containsKey(eb.getQuestionId()))
                {
                    if(eb.getAns().equals(questionAnswersMap.get(eb.getQuestionId()).getAns()))
                    {
                        examStudResponse.setObtMarksForObjQuestion(questionAnswersMap.get(eb.getQuestionId()).getMarksForQuestion());
                    }
                    else
                    {
                        examStudResponse.setObtMarksForObjQuestion(0);
                    }
                }
                
          ////  }
            
            questionsAnswerses.add(examStudResponse);
        }
        return questionsAnswerses;
    }
    
//    private Set<ExamQuestionsAnswers> getStudentResposePerQuestion(JSONObject inputRequest) 
//    {
//        try {
//            Set<ExamStudentResponse> questionsAnswerses =new HashSet<ExamStudentResponse>();
//    //        try {
//    //            Type listType = new TypeToken<Set<ExamQuestionsAnswers>>() {}.getType();
//    //            questionsAnswerses = new Gson().fromJson(inputRequest.getString(GlobalConstants.EXAMQUESTIONLIST), listType);
//    //        } catch (JSONException ex) {
//    //            ex.printStackTrace();
//    //        }
//            
//            List<ExamEntry> list = examDao.getExamEntryById(inputRequest.getInt("examId"));
//            
//          List<ExamBean>  examBeans = null;
//            try {
//                Type listType = new TypeToken<ArrayList<ExamBean>>() {}.getType();
//                examBeans = new Gson().fromJson(inputRequest.getString(GlobalConstants.EXAMQUESTIONLIST), listType);
//            } catch (JSONException ex) {
//                ex.printStackTrace();
//            }
//            
//            for(ExamBean eb:examBeans){
//                ExamStudentResponse examQuestionsAnswers = new ExamStudentResponse();
//                
//                examQuestionsAnswers.setExamId(examIdForSubmittingAnswers);
//                examQuestionsAnswers.setUsername(eb.getUsername());
//                examQuestionsAnswers.setQuestionId(String.valueOf(this.questionId));
//             
//                questionsAnswerses.add(examQuestionsAnswers);
//            }
//            
//            
//            return questionsAnswerses;
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }
//    }

    private HashMap<Integer,ExamQuestionsAnswers> getQuestionAnswersMap(ExamEntry examEntry) 
    {
        HashMap<Integer,ExamQuestionsAnswers> questionAnswersMap = new HashMap<Integer,ExamQuestionsAnswers>();
        Set<ExamQuestionsAnswers> queAnsSet = examEntry.getExamQuestionsAnswerses();
        Iterator<ExamQuestionsAnswers> itr = queAnsSet.iterator();
        while(itr.hasNext())
        {
            ExamQuestionsAnswers row = itr.next();
            questionAnswersMap.put(row.getQuestionId(), row);            
        }
        
        return questionAnswersMap;
    }
    
}
