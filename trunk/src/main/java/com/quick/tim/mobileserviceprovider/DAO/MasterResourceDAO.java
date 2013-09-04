/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAO;

import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.bean.QuickLearn;
import com.quick.tim.mobileserviceprovider.entity.Std;
import java.util.List;

/**
 *
 * @author suyogn
 */
public interface MasterResourceDAO {
    
 public List<MasteParmBean>  getStandardWisesubjects(String std);
 public List<Std> getStandardList();
 public List<QuickLearn> getSubjectBystd(String std);
 public List<QuickLearn> getDivisionBystd(String std);
 
 }
