/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAO;

import com.quick.tim.mobileserviceprovider.bean.ExamBean;
import com.quick.tim.mobileserviceprovider.bean.ExamQueAnsBean;
import com.quick.tim.mobileserviceprovider.entity.ExamEntry;
import com.quick.tim.mobileserviceprovider.entity.ExamStudentResponse;
import com.quick.tim.mobileserviceprovider.entity.StudentExamSummary;
import java.util.List;
import java.util.Set;

/**
 *
 * @author rajkiran
 */
public interface ExamDao {
    public List<ExamBean> getExamList(String std,String div, boolean isAdmin);
    public List<ExamBean> getExamDetailsById(int exmId);
    public List<ExamQueAnsBean> getExamQuestionById(int exmId, boolean isSendAns);
    public void createExam(ExamEntry entry);

    public void deleteExam(ExamEntry entry);

    public List<ExamBean> getPresentStudentsForExam(int examId);

    public List<ExamBean> getAbsentStudentsForExam(int examId,String std,String div);
    /**
     *
     * @param entry
     * @return
     */
    public List<ExamEntry> getExamEntryById(int entry);
    public void sumbmitStudExamResponse(Set<ExamStudentResponse> questionsAnswerses);
    public void sumbmitStudExamSummary(StudentExamSummary summary);
    public void saveOrUpdateExamEntry(ExamEntry entry);
    public List<ExamBean> getAverageScoresForAllSubjects(String forStd, String forDiv);
    public List<ExamBean> getSubjectswiseAverageScoresForStudent(String userName);
}
