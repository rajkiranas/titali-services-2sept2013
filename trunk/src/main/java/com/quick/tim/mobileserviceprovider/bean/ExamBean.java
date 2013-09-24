/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.bean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Date;

/**
 *
 * @author rajkiran
 */
public class ExamBean {
     private int examId;
     private String std;
     private String sub;
     private String exName;
     private int exType;
     private Date startDt;
     private Date endDt;
     private String fordiv;
     private String topic;
     private String createdBy;
     private Date creationDt;
     private int noOfQuestions;
     private int totalMarks;
     private int passingMarks;
     private int totalStudents;
     private int appearedStudents;
     private int passedStudents;
     private int failedStudents;
     private String option1;
     private String option2;
     private String option3;
     private String option4;
     private String ans;
     private String queString;
     private int marksPerQuestion;
     private int queType;
     private String contestLine;
     private String username;
     private Date responseDt;
     private String result;
     private int questionId;
     
     private float examTopScore;
     private float examAvgScore;
     private float examLowScore;
     
     private double subjectWiseAvgPerformance;
     
     private Short totalObtMarksObj;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getResponseDt() {
        return responseDt;
    }

    public void setResponseDt(Date responseDt) {
        this.responseDt = responseDt;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getContestLine() {
        return contestLine;
    }

    public void setContestLine(String contestLine) {
        this.contestLine = contestLine;
    }
     
     
    public int getQueType() {
        return queType;
    }

    public void setQueType(int queType) {
        this.queType = queType;
    }

    public String getQueString() {
        return queString;
    }

    public void setQueString(String queString) {
        this.queString = queString;
    }
     

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }
 

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public int getAppearedStudents() {
        return appearedStudents;
    }

    public void setAppearedStudents(int appearedStudents) {
        this.appearedStudents = appearedStudents;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDt() {
        return creationDt;
    }

    public void setCreationDt(Date creationDt) {
        this.creationDt = creationDt;
    }

    public Date getEndDt() {
        return endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

  

    public String getExName() {
        return exName;
    }

    public void setExName(String exName) {
        this.exName = exName;
    }

    public int getExType() {
        return exType;
    }

    public void setExType(int exType) {
        this.exType = exType;
    }

    public int getFailedStudents() {
        return failedStudents;
    }

    public void setFailedStudents(int failedStudents) {
        this.failedStudents = failedStudents;
    }

    public String getFordiv() {
        return fordiv;
    }

    public void setFordiv(String fordiv) {
        this.fordiv = fordiv;
    }

    public int getNoOfQuestions() {
        return noOfQuestions;
    }

    public void setNoOfQuestions(int noOfQuestions) {
        this.noOfQuestions = noOfQuestions;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getPassedStudents() {
        return passedStudents;
    }

    public void setPassedStudents(int passedStudents) {
        this.passedStudents = passedStudents;
    }

    public int getPassingMarks() {
        return passingMarks;
    }

    public void setPassingMarks(int passingMarks) {
        this.passingMarks = passingMarks;
    }

    public Date getStartDt() {
        return startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    public String getStd() {
        return std;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }

    public int getMarksPerQuestion() {
        return marksPerQuestion;
    }

    public void setMarksPerQuestion(int marksPerQuestion) {
        this.marksPerQuestion = marksPerQuestion;
    }

    public float getExamTopScore() {
        return examTopScore;
    }

    public void setExamTopScore(float examTopScore) {
        this.examTopScore = examTopScore;
    }

    public float getExamAvgScore() {
        return examAvgScore;
    }

    public void setExamAvgScore(float examAvgScore) {
        this.examAvgScore = examAvgScore;
    }

    public float getExamLowScore() {
        return examLowScore;
    }

    public void setExamLowScore(float examLowScore) {
        this.examLowScore = examLowScore;
    }

    /**
     * @return the totalObtMarksObj
     */
    public Short getTotalObtMarksObj() {
        return totalObtMarksObj;
    }

    /**
     * @param totalObtMarksObj the totalObtMarksObj to set
     */
    public void setTotalObtMarksObj(Short totalObtMarksObj) {
        this.totalObtMarksObj = totalObtMarksObj;
    }

    /**
     * @return the subjectWiseAvgPerformance
     */
    public double getSubjectWiseAvgPerformance() {
        return subjectWiseAvgPerformance;
    }

    /**
     * @param subjectWiseAvgPerformance the subjectWiseAvgPerformance to set
     */
    public void setSubjectWiseAvgPerformance(double subjectWiseAvgPerformance) {
        this.subjectWiseAvgPerformance = subjectWiseAvgPerformance;
    }

 
   

}