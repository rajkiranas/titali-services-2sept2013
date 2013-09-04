/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAOImpl;

import org.hibernate.Session;

/**
 *
 * @author kishorp
 */
public interface GenericDAO <T>{
  //  public void save(T t);
   // public void save(String EntityName,T t);
    public void Update();
    public void delete();
    public T find(String query);
    public T findAll();
    public T findByNamedParam(String query ,Object[] param);
    public Session getSession();
}