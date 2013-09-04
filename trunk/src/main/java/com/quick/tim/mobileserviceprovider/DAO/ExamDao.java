/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAO;

import com.quick.tim.mobileserviceprovider.bean.ExamBean;
import com.quick.tim.mobileserviceprovider.bean.ExamQueAnsBean;
import com.quick.tim.mobileserviceprovider.entity.ExamEntry;
import java.util.List;

/**
 *
 * @author rajkiran
 */
public interface ExamDao {
    public List<ExamBean> getExamList(String std,String div);
    public List<ExamBean> getExamDetailsById(int exmId);
    public List<ExamQueAnsBean> getExamQuestionById(int exmId);
    public void createExam(ExamEntry entry);

    public void deleteExam(ExamEntry entry);
}
