/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAO;

import com.quick.tim.mobileserviceprovider.entity.Notices;
import com.quick.tim.mobileserviceprovider.entity.UserMaster;
import java.util.List;

/**
 *
 * @author sonalis
 */
public interface StudentDao{
    public List getMaxPrn();
    public void saveOrUpdate(UserMaster userMaster);
}
