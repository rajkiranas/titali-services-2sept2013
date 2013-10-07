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
import com.quick.tim.mobileserviceprovider.entity.StudentExamSummary;
import com.quick.tim.mobileserviceprovider.entity.StudentExamSummaryId;
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
            
            
    public List<ExamBean> getExamList(String std,String div, boolean isAdmin){
        return examDao.getExamList(std,div,isAdmin);
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
           entry.setTotalStudents(getTotalStudentForClass(entry.getStd().getStd(),entry.getFordiv()));
           
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

    public void recordStudentExamResponse(JSONObject inputRequest) 
    {
         sumbmitStudExamResponse(inputRequest);         
         
    }
    
    private void sumbmitStudExamResponse(JSONObject inputRequest) 
    {
        Set<ExamStudentResponse> examStudentResponseSet =new HashSet<ExamStudentResponse>();
        
        List<ExamEntry> examEntryList=null;
        ExamEntry examEntry=null;
        List<UserMaster> userMasterList=null;
        UserMaster userMaster=null;
        int markesObtained=0;
        int examTotalMarks=0;
        
        HashMap<Integer,ExamQuestionsAnswers> questionAnswersMap =null;        
        
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
            examTotalMarks=examTotalMarks + questionAnswersMap.get(eb.getQuestionId()).getMarksForQuestion();
                if(questionAnswersMap.containsKey(eb.getQuestionId()))
                {
                    if(eb.getAns().equals(questionAnswersMap.get(eb.getQuestionId()).getAns()))
                    {
                        examStudResponse.setObtMarksForObjQuestion(questionAnswersMap.get(eb.getQuestionId()).getMarksForQuestion());
                        markesObtained=markesObtained+examStudResponse.getObtMarksForObjQuestion();
                    }
                    else
                    {
                        examStudResponse.setObtMarksForObjQuestion(0);
                    }
                }
                
            examStudentResponseSet.add(examStudResponse);
        }
        
        //inserting vlaues in exam_student_response table
        examDao.sumbmitStudExamResponse(examStudentResponseSet);
        
        //inserting vlaues in student_exam_summary table
        String passFailResult = submitStudentExamSummary(examEntry,userMaster,markesObtained,examTotalMarks);
        
        //updating vlaues in exam_entry table
        updateExamEntry(examEntry,userMaster,markesObtained,examTotalMarks,passFailResult);
        
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

    private String submitStudentExamSummary(ExamEntry examEntry, UserMaster userMaster, Integer markesObtained, Integer examTotalMarks) 
    {
        StudentExamSummary summary = new StudentExamSummary();
        summary.setExamEntry(examEntry);
        summary.setUserMaster(userMaster);
        summary.setId(new StudentExamSummaryId(examEntry.getExId(), userMaster.getUsername()));
        summary.setResponseDt(new Date());
        summary.setTotalMarks(examTotalMarks.shortValue());
        summary.setTotalObtMarksObj(markesObtained.shortValue());
        int temp=markesObtained*100;
        double percentage = temp/examTotalMarks;
        
        if(percentage>35) {
            summary.setResult(GlobalConstants.PASS);
        }
        else {
            summary.setResult(GlobalConstants.FAIL);
        }
        examDao.sumbmitStudExamSummary(summary);
        
        return summary.getResult();
    }
    
    /**
     * method is synchronised because it incremnets count of appeared students
     * because of this it again fetches exam entry object for taking new updates
     */
    
    private synchronized void updateExamEntry(ExamEntry examEntry, UserMaster userMaster, Integer markesObtained, Integer examTotalMarks,String passFailResult) 
    {
        //fetching exam entry intentionally
        List<ExamEntry> examEntryList=examDao.getExamEntryById(examEntry.getExId());                
        ExamEntry entry = examEntryList.get(0);
        
        if(passFailResult.equals(GlobalConstants.PASS))
        {
            entry.setPassedStudents(entry.getPassedStudents() + 1);
        }
        else
        {
            entry.setFailedStudents(entry.getFailedStudents() + 1);
        }
        
        if(markesObtained>entry.getExamTopScore())
        {
            entry.setExamTopScore(markesObtained);
        }
        
        if(markesObtained<entry.getExamLowScore())
        {
            entry.setExamLowScore(markesObtained);
        }
        
        
        float totalAvgScore = entry.getAppearedStudents() * entry.getExamAvgScore();
        totalAvgScore=totalAvgScore+markesObtained;
        
               
        //setting appread students after calculating exam avg score
        entry.setAppearedStudents(entry.getAppearedStudents() + 1);
        
        //calculating exam avg score
        entry.setExamAvgScore((totalAvgScore/entry.getAppearedStudents()));
        
        examDao.saveOrUpdateExamEntry(entry);
    }

    private int getTotalStudentForClass(String std, String fordiv) {
        return userDao.getStudentCountForClass(std,fordiv);
    }
    public List<ExamBean> getAverageScoresForAllSubjects(String forStd, String forDiv) {
        return examDao.getAverageScoresForAllSubjects(forStd, forDiv);
    }
    
        public List<ExamBean> getSubjectswiseAverageScoresForStudent(String userName) {
        return examDao.getSubjectswiseAverageScoresForStudent(userName);
    }
}
