/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quick.tim.mobileserviceprovider.DAO.ExamDao;
import com.quick.tim.mobileserviceprovider.bean.ExamBean;
import com.quick.tim.mobileserviceprovider.bean.ExamQueAnsBean;
import com.quick.tim.mobileserviceprovider.entity.ExamEntry;
import com.quick.tim.mobileserviceprovider.entity.ExamQuestionsAnswers;
import com.quick.tim.mobileserviceprovider.entity.Std;
import com.quick.tim.mobileserviceprovider.entity.Sub;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import com.quick.tim.mobileserviceprovider.resource.PersonResource;
import com.quick.tim.mobileserviceprovider.utilities.DateUtil;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
    public List<ExamBean> getExamList(String std,String div){
        return examDao.getExamList(std,div);
    }

    public List<ExamBean> getExamDetailsById(int exmId) {
        return examDao.getExamDetailsById(exmId);
    }

    public List<ExamQueAnsBean> getExamQuestionById(int exmId) {
        return examDao.getExamQuestionById(exmId);
      
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
    
}
