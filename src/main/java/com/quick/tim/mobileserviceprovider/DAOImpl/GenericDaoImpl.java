/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAOImpl;

import com.quick.tim.mobileserviceprovider.Factory.HibernateSessionFactory;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author kishorp
 */
public class GenericDaoImpl < T > implements GenericDAO < T > {
     
   private HibernateTemplate hibernateTemplate = new HibernateTemplate(HibernateSessionFactory.getSessionFactory());
   private Query query;
   

   // ExceptionHandler exceptionHandler = new ExceptionHandler();
    private Class< T > type;

    public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];

    }
    /**
     * This method is used to fetch session
     * @return session
     */
   

//    public void save(T t) {
//        hibernateTemplate.saveOrUpdate(t);
//    }
    
    /**
     * 
     * @param t
     * @throws Exception 
     */
    public void saveOrUpdate(T t ) throws Exception
    {  Session session = null;
        Transaction tx = null ;
        try 
        { 
             session=getSession();
             tx= session.beginTransaction();
             tx.begin();
//                hibernateTemplate.saveOrUpdate(t);
                session.saveOrUpdate(t);
             tx.commit();
            
            
//            hibernateTemplate.saveOrUpdate(t);
            
        } catch (Exception e) 
        {
             tx.rollback();
            // logger.debug("Exception occured in saveOrUpdate(T t ) method,parameter t="+t +" Exception= ", e); 
             e.printStackTrace();
            throw e;
        }finally {
            session.close();
        }
    }
    
    public void saveOrUpdateWithoutCommit(T t ) throws Exception
    {
       
        try 
        {
            getSession().saveOrUpdate(t);
        } catch (Exception e) 
        {
            e.printStackTrace();
            //logger.debug("Exception occured in saveOrUpdate(T t ) method,parameter t="+t +" Exception= ", e);
            throw e;
        }
    }
    
    public void delete(String Query, Object[] obj) throws Exception
      {
        try 
        {
            getSession().delete(Query,obj);
            
        } catch (Exception e) 
        {
            e.printStackTrace();
          //  logger.debug("Exception occured in saveOrUpdate(T t ) method = ", e);
            throw e;
        }
    }
    

    public T findByNamedParam(String query ,Object[] param) {
        return (T) hibernateTemplate.find(query,param);
    }

        //Update/delete all objects according to the given query.
	public int bulkUpdate(String queryString)throws DataAccessException {
		return hibernateTemplate.bulkUpdate(queryString);
	}
	// Update/delete all objects according to the given query, binding one value to a "?"
	
	public int bulkUpdate(String queryString, T value) throws DataAccessException {
		return hibernateTemplate.bulkUpdate(queryString, value);
	}
	//Update/delete all objects according to the given query, binding a number of values to "?"
	
	public int bulkUpdate (String queryString, T... values) throws DataAccessException {
		return hibernateTemplate.bulkUpdate(queryString, values);
	}
	//Remove all objects from the Session cache, and cancel all pending saves, updates and deletes.
        
        public void clear()throws DataAccessException  {
		hibernateTemplate.clear();
	}
        //Check whether the given object is in the Session cache.
	
	public boolean contains(T entity) throws DataAccessException {
		return hibernateTemplate.contains(entity);
	}
	// Delete the given persistent instance.
	
	public void delete(T entity) throws DataAccessException, Exception 
        {
		try 
                {
                    Session session=getSession();
                    Transaction tx = session.beginTransaction();
                    tx.begin();
                        
                        session.delete(entity);
                    tx.commit();
                    session.close(); 

                } catch (Exception e) 
                {
                    e.printStackTrace();
                    throw e;
                } 
            
            //hibernateTemplate.delete(entity);
	}
	// Delete the given persistent instance.
	
	public void delete(T entity, LockMode lockMode) throws DataAccessException {
		hibernateTemplate.delete(entity,lockMode);
	}
	// Delete the given persistent instance.
	
	public void delete(String entityName,T entity) throws DataAccessException {
		hibernateTemplate.delete(entityName, entity);
	}
	
	public void delete(String entityName,T entity, LockMode lockMode) throws DataAccessException {
		hibernateTemplate.delete(entityName, entity, lockMode);
	}
	// Delete all given persistent instances.
	public void deleteAll(List<T> entities) throws DataAccessException {
		hibernateTemplate.deleteAll(entities);
	}
        //Return an enabled Hibernate Filter for the given filter name.
	public Filter enableFilter(String filterName)  throws DataAccessException {
		return hibernateTemplate.enableFilter(filterName);
	}
	//Remove the given object from the Session cache.
	public void evict(T entity)  throws DataAccessException {
		hibernateTemplate.evict(entity);
	}
        //Execute the action specified by the given action object within a Session.
	

	public T execute( HibernateCallback<T> action)  throws DataAccessException {
		return hibernateTemplate.execute(action);
	}
	//Execute the specified action assuming that the result object is a List.

	@SuppressWarnings("unchecked")
	public List<T> executeFind(HibernateCallback<T> action) throws DataAccessException {
		return hibernateTemplate.executeFind(action);
	}
	// Execute an HQL query.
	/* @SuppressWarnings("unchecked")
	public List<T> find(String queryString)throws DataAccessException {
		return hibernateTemplate.find(queryString);
	} */
	//Execute an HQL query, binding one value to a "?"
	@SuppressWarnings("unchecked")
	public List<T> find(String queryString, Object value)throws DataAccessException {
		return hibernateTemplate.find(queryString, value);
	}
	// Execute an HQL query, binding a number of values to "?"
	
	@SuppressWarnings("unchecked")
	public List<T> find(String queryString, T... values)throws DataAccessException {
		return hibernateTemplate.find(queryString, values);
	}
	// Execute a query based on a given Hibernate criteria object.
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(DetachedCriteria criteria)throws DataAccessException {
		return hibernateTemplate.findByCriteria(criteria);
	}
	//Execute a query based on the given Hibernate criteria object.
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) {
		return hibernateTemplate.findByCriteria(criteria,firstResult,maxResults);
	}
	//  Execute a query based on the given example entity object.
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleEntity)throws DataAccessException {
		return hibernateTemplate.findByExample(exampleEntity);
	}
	//  Execute a query based on the given example entity object.
	@SuppressWarnings("unchecked")
	public List<T> findByExample(String entityName, T exampleEntity)throws DataAccessException {
		return hibernateTemplate.findByExample(entityName, exampleEntity);
	}
	// Execute a query based on a given example entity object.
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleEntity, int firstResult, int maxResults) throws DataAccessException {
		return hibernateTemplate.findByExample(exampleEntity,firstResult,maxResults);
	}
	// Execute a query based on a given example entity object.
	@SuppressWarnings("unchecked")
	public List<T> findByExample(String entityName, T exampleEntity, int firstResult, int maxResults) throws DataAccessException {
		return hibernateTemplate.findByExample(entityName,exampleEntity,firstResult,maxResults);
	}
	// Execute an HQL query, binding a number of values to ":" named parameters in the query string.
	@SuppressWarnings("unchecked")
	public List<T> findByNamedParam(String queryString, String paramName, T value) throws DataAccessException {
		return hibernateTemplate.findByNamedParam(queryString, paramName, value);
	}
	// Execute an HQL query, binding a number of values to ":" named parameters in the query string.
	@SuppressWarnings("unchecked")
	public List<T> findByNamedParam(String queryString, String[] paramNames, T[] values) throws DataAccessException {
		return hibernateTemplate.findByNamedParam(queryString, paramNames, values);
	}
	@SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(String queryName, Long valueOf) {
		return hibernateTemplate.findByNamedQuery(queryName,valueOf);
	}
	// Execute a named query.
	@SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(String queryName) throws DataAccessException {
		return hibernateTemplate.findByNamedQuery(queryName);
	}
	// Execute a named query, binding one value to a "?"
	@SuppressWarnings("unchecked")
	public List<T>  findByNamedQuery(String queryName, T value) throws DataAccessException {
		return hibernateTemplate.findByNamedQuery(queryName,value);
	}
	// Execute a named query binding a number of values to "?"
	@SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(String queryName, T... values) throws DataAccessException {
		return hibernateTemplate.findByNamedQuery(queryName,values);
	}
	// Execute a named query, binding a number of values to ":" named parameters in the query string.
	@SuppressWarnings("unchecked")
	public List<T> findByNamedQueryAndNamedParam(String queryName, String[] paramNames, T[] values)throws DataAccessException {
		return hibernateTemplate.findByNamedQueryAndNamedParam(queryName,paramNames,values);
	}
	// Execute a named query, binding one value to a ":" named parameter in the query string.
	@SuppressWarnings("unchecked")
	public List<T> findByNamedQueryAndNamedParam(String queryName, String paramName, T value)throws DataAccessException {
		return hibernateTemplate.findByNamedQueryAndNamedParam(queryName,paramName,value);
	}
	//  Execute a named query, binding the properties of the given bean to ":" named parameters in the query string.
	@SuppressWarnings("unchecked")
	public List<T> findByNamedQueryAndValueBean(String queryName, T valueBean)throws DataAccessException {
		return hibernateTemplate.findByNamedQueryAndValueBean(queryName, valueBean);
	}
	
	// Execute an HQL query, binding the properties of the given bean to named parameters in the query string.
	@SuppressWarnings("unchecked")
	public List<T> findByValueBean(String queryString, T valueBean) throws DataAccessException {
		return hibernateTemplate.findByValueBean(queryString, valueBean);
	}
        public void flush()throws DataAccessException {
		hibernateTemplate.flush();
	}
	// Return the persistent instance of the given entity class with the given identifier, or null if not found.
		public T get(  Class<T> entityClass, Serializable id) throws DataAccessException {
		return hibernateTemplate.get(entityClass, id);
	}
	//Return the persistent instance of the given entity class with the given identifier, or null if not found.

	public T  get( Class<T> entityClass, Serializable id, LockMode lockMode) throws DataAccessException {
		return hibernateTemplate.get(entityClass, id,lockMode);
	}
	// Return the persistent instance of the given entity class with the given identifier, or null if not found.
	@SuppressWarnings("unchecked")
	public T  get(String entityName, Serializable id)throws DataAccessException {
		return  (T) hibernateTemplate.get(entityName,id);
	}
	// Return the persistent instance of the given entity class with the given identifier, or null if not found.
	@SuppressWarnings("unchecked")
	public T  get(String entityName, Serializable id, LockMode lockMode)throws DataAccessException {
		return (T) hibernateTemplate.get(entityName,id,lockMode);
	}
	// Force initialization of a Hibernate proxy or persistent collection.
	public void initialize(final Object proxy) throws DataAccessException {
		hibernateTemplate.initialize(proxy);
	}
	// Execute a query for persistent instances.
		@SuppressWarnings("unchecked")
		public Iterator<T> iterate(String queryString)throws DataAccessException {
		return hibernateTemplate.iterate(queryString);
	}
	// Execute a query for persistent instances, binding one value to a "?"
	@SuppressWarnings("unchecked")
	public Iterator<T> iterate(String queryString, Object value) throws DataAccessException {
		return hibernateTemplate.iterate(queryString, value);
	}
	// Execute a query for persistent instances, binding a number of values to "?"
	@SuppressWarnings("unchecked")
	public Iterator<T>  iterate(String queryString, Object... values) throws DataAccessException {
		return hibernateTemplate.iterate(queryString, values);
	}
	// Return the persistent instance of the given entity class with the given identifier, throwing an exception if not found.
	public T load(Class<T> entityClass, Serializable id) throws DataAccessException {
		return hibernateTemplate.load(entityClass, id);
	}
	//Return the persistent instance of the given entity class with the given identifier, throwing an exception if not found.
	public T load(Class<T> entityClass, Serializable id, LockMode lockMode) throws DataAccessException {
		return hibernateTemplate.load(entityClass, id,lockMode);
	}
	// Load the persistent instance with the given identifier into the given object, throwing an exception if not found
	public void load(T entity, Serializable id) throws DataAccessException {
		hibernateTemplate.load(entity, id);
	}
	// Return the persistent instance of the given entity class with the given identifier, throwing an exception if not found.
	@SuppressWarnings("unchecked")
	public T load(String entityName, Serializable id) throws DataAccessException {
		return (T) hibernateTemplate.load(entityName, id);
	}
	// Return the persistent instance of the given entity class with the given identifier, throwing an exception if not found.
	@SuppressWarnings("unchecked")
	public T load(String entityName, Serializable id, LockMode lockMode) throws DataAccessException {
		return (T) hibernateTemplate.load(entityName, id, lockMode);
	}
	//  Return all persistent instances of the given entity class.
	public List<T> loadAll(Class<T> entityClass) throws DataAccessException {
		return hibernateTemplate.loadAll(entityClass);
	}
	// Obtain the specified lock level upon the given object, implicitly checking whether the corresponding database entry still exists.
	public void lock(Object entity, LockMode lockMode) throws DataAccessException {
		hibernateTemplate.lock(entity, lockMode);
	}
	//Obtain the specified lock level upon the given object, implicitly checking whether the corresponding database entry still exists.
	public void lock(String entityName, Object entity, LockMode lockMode) throws DataAccessException {
		hibernateTemplate.lock(entityName, entity, lockMode);
	}
	//Copy the state of the given object onto the persistent object with the same identifier.
	public T merge(T entity) throws DataAccessException {
		return hibernateTemplate.merge(entity);
	}
	//Copy the state of the given object onto the persistent object with the same identifier.
	public T merge(String entityName, T entity) throws DataAccessException {
		return hibernateTemplate.merge(entityName, entity);
	}
	//Persist the given transient instance.
	public void persist(T entity) throws DataAccessException {
		hibernateTemplate.persist(entity);
	}
	//Persist the given transient instance.
	public void persist(String entityName, T entity) throws DataAccessException {
		hibernateTemplate.persist(entityName, entity);
	}
	//  Re-read the state of the given persistent instance.
	public void refresh(T entity) throws DataAccessException {
		hibernateTemplate.refresh(entity);
	}
	//  Re-read the state of the given persistent instance.
	public void refresh(T entity, LockMode lockMode) throws DataAccessException {
		hibernateTemplate.refresh(entity);
	}
	// Persist the state of the given detached instance according to the given replication mode, reusing the current identifier value.
	public void replicate(T entity, ReplicationMode replicationMode) throws DataAccessException {
		hibernateTemplate.replicate(entity, replicationMode);
	}
	// Persist the state of the given detached instance according to the given replication mode, reusing the current identifier value.
	public void replicate(String entityName,T entity, ReplicationMode replicationMode) throws DataAccessException {
		hibernateTemplate.replicate(entityName, entity, replicationMode);
	}
	
        /* // Persist the given transient instance.
	public Serializable saveOrUpdate(T entity) throws DataAccessException {
		return hibernateTemplate.saveOrUpdate(entity);
	} */
	// Persist the given transient instance.
	public Serializable save(String entityName,T entity) throws DataAccessException {
		return hibernateTemplate.save(entityName, entity);
	}
	
        /* // Save or update the given persistent instance, according to its id (matching the configured "unsaved-value"?).
	public void saveOrUpdate(T entity) throws DataAccessException {
		hibernateTemplate.saveOrUpdate(entity);
	} */
        
        
	// Save or update the given persistent instance, according to its id (matching the configured "unsaved-value"?).
	public void saveOrUpdate(String entityName, T entity) throws DataAccessException {
		hibernateTemplate.saveOrUpdate(entityName, entity);
	}
	// Save or update the given persistent instance, according to its id (matching the configured "unsaved-value"?).
	public void saveOrUpdateAll(Collection<T> entities) throws DataAccessException {
		hibernateTemplate.saveOrUpdate(entities);
	}
	// Update the given persistent instance, associating it with the current Hibernate Session.
	public void update(T entity) throws DataAccessException {
		hibernateTemplate.update(entity);
	}
	// Update the given persistent instance, associating it with the current Hibernate Session.
	
	public void update(T entity, LockMode lockMode) throws DataAccessException {
		hibernateTemplate.update(entity, lockMode);
	}
	// Update the given persistent instance, associating it with the current Hibernate Session.
	
	public void update(String entityName, T entity) throws DataAccessException {
		hibernateTemplate.update(entityName, entity);
	}
	// Update the given persistent instance, associating it with the current Hibernate Session.
	
	public void update(String entityName,T entity, LockMode lockMode) throws DataAccessException {
		hibernateTemplate.update(entityName, entity, lockMode);
	}

    @Override
    public Session getSession() {
       return HibernateSessionFactory.getSession();
    }

    @Override
    public void Update() {
       hibernateTemplate.update(type);
    }

    @Override
    public void delete() {
       hibernateTemplate.delete(type);
    }

    @Override
    public T find(String query) {
         return (T) hibernateTemplate.find(query);
    }

    @Override
    public T findAll() {
        String q = "from " + type.getSimpleName();
        return (T) hibernateTemplate.find(q);
    }
    
}
