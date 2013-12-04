/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAOImpl;

import com.quick.tim.mobileserviceprovider.DAO.ExamDao;
import com.quick.tim.mobileserviceprovider.bean.ExamBean;
import com.quick.tim.mobileserviceprovider.bean.ExamQueAnsBean;
import com.quick.tim.mobileserviceprovider.entity.ExamEntry;
import com.quick.tim.mobileserviceprovider.entity.ExamStudentResponse;
import com.quick.tim.mobileserviceprovider.entity.Std;
import com.quick.tim.mobileserviceprovider.entity.StudentExamSummary;
import com.quick.tim.mobileserviceprovider.entity.StudentMaster;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rajkiran
 */
@Repository("examDao")
@Transactional
public class ExamDaoImpl implements ExamDao {

    private HibernateTemplate hibernateTemplate;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) 
    {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }
    public List<ExamBean> getExamList(String std,String fordiv, boolean isAdmin) {
        DetachedCriteria criteria = DetachedCriteria.forClass(ExamEntry.class).createAlias("sub", "sub").createAlias("std", "std");
        if(!isAdmin)
        {
            criteria.add(Restrictions.eq("std.std",std));
            criteria.add(Restrictions.or(Restrictions.eq("fordiv", fordiv),Restrictions.eq("fordiv", null)));
        }
        
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("exId"), "examId");
        pl.add(Projections.property("sub.sub"), "sub");
        pl.add(Projections.property("exName"), "exName");
        pl.add(Projections.property("exType"), "exType");
        pl.add(Projections.property("startDt"), "startDt");
        pl.add(Projections.property("endDt"), "endDt");
        criteria.setProjection(pl);
        criteria.setResultTransformer(Transformers.aliasToBean(ExamBean.class));
        return hibernateTemplate.findByCriteria(criteria);
        
    }

    @Override
    public List<ExamBean> getExamDetailsById(int exmId) {
        DetachedCriteria summaryCriteria = DetachedCriteria.forClass(StudentExamSummary.class);
        summaryCriteria.add(Restrictions.eq("id.exId", exmId));
        List exsummaryList = hibernateTemplate.findByCriteria(summaryCriteria);
        
        DetachedCriteria criteria = DetachedCriteria.forClass(ExamEntry.class);
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("exId"), "examId");
        pl.add(Projections.property("std.std"), "std");
        pl.add(Projections.property("sub.sub"), "sub");
        pl.add(Projections.property("fordiv"), "fordiv");
        pl.add(Projections.property("exName"), "exName");
        pl.add(Projections.property("exType"), "exType");
        pl.add(Projections.property("topic"), "topic");
        pl.add(Projections.property("startDt"), "startDt");
        pl.add(Projections.property("endDt"), "endDt");
        pl.add(Projections.property("contestLine"), "contestLine");
        pl.add(Projections.property("noOfQuestions"), "noOfQuestions");
        pl.add(Projections.property("totalMarks"), "totalMarks");
        pl.add(Projections.property("examque.marksForQuestion"), "marksPerQuestion");
        pl.add(Projections.property("passingMarks"), "passingMarks");
        pl.add(Projections.property("appearedStudents"), "appearedStudents");
        pl.add(Projections.property("passedStudents"), "passedStudents");
        pl.add(Projections.property("failedStudents"), "failedStudents");
        pl.add(Projections.property("totalStudents"), "totalStudents");
        
        pl.add(Projections.property("examTopScore"), "examTopScore");
        pl.add(Projections.property("examAvgScore"), "examAvgScore");
        pl.add(Projections.property("examLowScore"), "examLowScore");
        if(!exsummaryList.isEmpty()){
            pl.add(Projections.property("summary.totalObtMarksObj"), "totalObtMarksObj");
        }
        pl.add(Projections.property("responseDt"), "responseDt");
        
        criteria.setProjection(pl);
       
        criteria.add(Restrictions.eq("exId",exmId));
        if(!exsummaryList.isEmpty()){
            criteria.createAlias("studentExamSummaries", "summary");
        }
        criteria.createAlias("sub", "sub");
        criteria.createAlias("std", "std");
        criteria.createAlias("examQuestionsAnswerses", "examque");
        // criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(Transformers.aliasToBean(ExamBean.class));
        return hibernateTemplate.findByCriteria(criteria);
    }
    
    public List<ExamBean> getPresentStudentsForExam(int examId)
    {
        DetachedCriteria criteria = DetachedCriteria.forClass(StudentExamSummary.class);
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("id.exId"), "examId");
        pl.add(Projections.property("id.username"), "username");
        pl.add(Projections.property("responseDt"), "responseDt");
        pl.add(Projections.property("totalMarks"), "totalMarks");
        pl.add(Projections.property("result"), "result");
                
        criteria.setProjection(pl);
        criteria.add(Restrictions.eq("id.exId",examId));
        criteria.setResultTransformer(Transformers.aliasToBean(ExamBean.class));
        
        return hibernateTemplate.findByCriteria(criteria);
        
    }

    
    
    @Override
    public List<ExamBean> getAbsentStudentsForExam(int examId,String forstd,String forDiv)
    {
        DetachedCriteria subcriteria = DetachedCriteria.forClass(StudentExamSummary.class);
         
        ProjectionList pl = Projections.projectionList();
         pl.add(Projections.property("id.username"), "username");
         subcriteria.setProjection(pl);
        
         subcriteria.add(Restrictions.eq("id.exId",examId));
         
         DetachedCriteria mainCriteria =  DetachedCriteria.forClass(StudentMaster.class).createAlias("std", "std");
         mainCriteria.add(Restrictions.eq("std.std", forstd));
        // mainCriteria.add(Restrictions.eq("div", forDiv));
         mainCriteria.add(Subqueries.propertyNotIn("username", subcriteria));
         ProjectionList pl1 = Projections.projectionList();
         pl1.add(Projections.property("username"), "username");
         mainCriteria.setProjection(pl1);
         mainCriteria.setResultTransformer(Transformers.aliasToBean(ExamBean.class));
        
        return hibernateTemplate.findByCriteria(mainCriteria);
         
    }

    @Override
    public List<ExamQueAnsBean> getExamQuestionById(int exmId, boolean isSendAns) {
        DetachedCriteria criteria = DetachedCriteria.forClass(ExamEntry.class).createAlias("examQuestionsAnswerses", "que");
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("exId"), "examId");
        pl.add(Projections.property("exType"), "exType");
        if(isSendAns)
        {
            pl.add(Projections.property("que.ans"), "ans");
        }
        pl.add(Projections.property("que.questionId"), "questionId");
        pl.add(Projections.property("que.questionType"), "questionType");
        pl.add(Projections.property("que.question"), "question");
        pl.add(Projections.property("que.marksForQuestion"), "marksForQuestion");
        pl.add(Projections.property("que.option1"), "option1");
        pl.add(Projections.property("que.option2"), "option2");
        pl.add(Projections.property("que.option3"), "option3");
        pl.add(Projections.property("que.option4"), "option4");
        criteria.setProjection(pl);
        criteria.add(Restrictions.eq("exId",exmId));
        criteria.setResultTransformer(Transformers.aliasToBean(ExamQueAnsBean.class));
        return hibernateTemplate.findByCriteria(criteria);
    }

    public void createExam(ExamEntry entry) {
        hibernateTemplate.saveOrUpdate(entry);
    }
    
    public void deleteExam(ExamEntry entry) {
        hibernateTemplate.delete(entry);
    }

    private static final String findExamEntryByIdQry="from ExamEntry as model where model.exId=?";
    @Override
    public List<ExamEntry> getExamEntryById(int examId) 
    {
        return hibernateTemplate.find(findExamEntryByIdQry, examId);        
    }
    
    @Override
    public void sumbmitStudExamResponse(Set<ExamStudentResponse> questionsAnswerses) 
    {
        hibernateTemplate.saveOrUpdateAll(questionsAnswerses);
    }

    @Override
    public void sumbmitStudExamSummary(StudentExamSummary summary) {
        hibernateTemplate.saveOrUpdate(summary);
    }

    @Override
    public void saveOrUpdateExamEntry(ExamEntry entry) {
        hibernateTemplate.saveOrUpdate(entry);
    }
    
    @Override
    public List<ExamBean> getAverageScoresForAllSubjects(String forStd, String forDiv) {
        
        // to avoid referring summary table is values are nor present
        DetachedCriteria summaryCriteria = DetachedCriteria.forClass(StudentExamSummary.class).createAlias("examEntry", "examEntry");
        summaryCriteria.add(Restrictions.eq("examEntry.std.std", forStd));
        summaryCriteria.add(Restrictions.eq("examEntry.fordiv", forDiv));
        List exsummaryList = hibernateTemplate.findByCriteria(summaryCriteria);
        
        
        DetachedCriteria criteria = DetachedCriteria.forClass(ExamEntry.class);
        ProjectionList pl = Projections.projectionList();
        
        pl.add(Projections.property("sub.sub"), "sub");
        if(!exsummaryList.isEmpty())
        {
            pl.add(Projections.avg("summary.totalObtMarksObj"),"subjectWiseAvgPerformance");

        }
        pl.add(Projections.groupProperty(("sub.sub")));
        
        criteria.add(Restrictions.eq("std.std",forStd));
        criteria.add(Restrictions.eq("fordiv",forDiv));
        
        criteria.setProjection(pl);
        
        if(!exsummaryList.isEmpty())
        {
            criteria.createAlias("studentExamSummaries", "summary");
        }
        criteria.createAlias("sub", "sub");
        criteria.createAlias("std", "std");
        criteria.createAlias("examQuestionsAnswerses", "examque");
        // criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(Transformers.aliasToBean(ExamBean.class));
        
        return hibernateTemplate.findByCriteria(criteria);
    }
    
    @Override
    public List<ExamBean> getSubjectswiseAverageScoresForStudent(String userName) {
        
        DetachedCriteria summaryCriteria = DetachedCriteria.forClass(StudentExamSummary.class);
        summaryCriteria.add(Restrictions.eq("id.username", userName));
        List exsummaryList = hibernateTemplate.findByCriteria(summaryCriteria);
        
        
        DetachedCriteria criteria = DetachedCriteria.forClass(ExamEntry.class);
        ProjectionList pl = Projections.projectionList();
        
        pl.add(Projections.property("sub.sub"), "sub");
        if(!exsummaryList.isEmpty())
        {
            pl.add(Projections.avg("summary.totalObtMarksObj"),"subjectWiseAvgPerformance");
            criteria.add(Restrictions.eq("summary.userMaster.username",userName));

        }
        pl.add(Projections.groupProperty(("sub.sub")));
        
        
//        criteria.add(Restrictions.eq("std.std",forStd));
//        criteria.add(Restrictions.eq("fordiv",forDiv));
        
        criteria.setProjection(pl);
        
        if(!exsummaryList.isEmpty())
        {
            criteria.createAlias("studentExamSummaries", "summary");

        }
        criteria.createAlias("sub", "sub");
        criteria.createAlias("std", "std");
        criteria.createAlias("examQuestionsAnswerses", "examque");
        // criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(Transformers.aliasToBean(ExamBean.class));
        
        return hibernateTemplate.findByCriteria(criteria);
    }
}
