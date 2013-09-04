/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.services;

import com.quick.tim.mobileserviceprovider.DAO.MasterResourceDAO;
import com.quick.tim.mobileserviceprovider.bean.MasteParmBean;
import com.quick.tim.mobileserviceprovider.bean.QuickLearn;
import com.quick.tim.mobileserviceprovider.entity.Std;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author suyogn
 */
@Component
public class MasterResourceService {
    @Autowired
    MasterResourceDAO masterResourceDAO;
    
    public List<MasteParmBean>getStandardWisesubjects(String std)
    {
        return masterResourceDAO.getStandardWisesubjects(std);
    }
    
    
      public List<Std> getStandardList() {
       List<Std> stdList=masterResourceDAO.getStandardList();
       return stdList;
    }

    public List<QuickLearn> getSubjectBystd(String std) {
       List<QuickLearn> subList=masterResourceDAO.getSubjectBystd(std);
       return subList;
    }

    public List<QuickLearn> getDivisionBystd(String std) {
       List<QuickLearn> subList=masterResourceDAO.getDivisionBystd(std);
       return subList;
    }
    
}
