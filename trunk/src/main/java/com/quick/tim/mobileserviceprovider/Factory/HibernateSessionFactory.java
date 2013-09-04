/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.Factory;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author kishorp
 */
public class HibernateSessionFactory {
   private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
    private static final ThreadLocal threadLocal = new ThreadLocal();
    //private  static Configuration configuration = new Configuration();
    private  static Configuration configuration = new AnnotationConfiguration();
    
    private  static Configuration altConfiguration = new Configuration();
    private static org.hibernate.SessionFactory sessionFactory;
    private static org.hibernate.SessionFactory altSessionFactory;
    private static String configFile = CONFIG_FILE_LOCATION;
//    static final Logger logger = LoggerFactory.getLogger(HibernateSessionFactory.class);
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            //sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            
            configuration.configure(CONFIG_FILE_LOCATION);
            ////////////sessionFactory = configuration.buildSessionFactory();
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            
        } catch (Throwable ex) {
            // Log the exception. 
            //System.err.println("Initial SessionFactory creation failed." + ex);
            //logger.debug("Initial SessionFactory creation failed." +" Exception= ", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }
    
     private HibernateSessionFactory() {
    }
    
    public static void initialHibernate() {
        //m_objLogger.debug("######################## Initializing Hibernate");
        Session session = getSession();
        session.close();
    }
    
    /**
     * Returns the ThreadLocal Session instance.  Lazy initialize
     * the <code>SessionFactory</code> if needed.
     *
     *  @return Session
     *  @throws HibernateException
     */
    public static Session getSessionfrmThread() throws HibernateException {
        Session session = (Session) threadLocal.get();
        
        return session;
    }
    
    
    /**
     * Returns the ThreadLocal Session instance.  Lazy initialize
     * the <code>SessionFactory</code> if needed.
     *
     *  @return Session
     *  @throws HibernateException
     */
    public static Session getSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        
        if (session == null || !session.isOpen()) {
            //m_objLogger.debug("######################## Hibernate session is closed !!!!!!!!!!!!!!!!!!!!!!!!");
            if (sessionFactory == null) {
                //m_objLogger.debug("######################## Hibernate creationg session factory !!!!!!!!!!!!!!!!!!!!!!!!");
                rebuildSessionFactory();
            }
            session = (sessionFactory != null) ? sessionFactory.openSession()
            : null;
            threadLocal.set(session);
            //m_objLogger.debug("######################## Hibernate session is created  !!!!!!!!!!!!!!!!!!!!!!!!");
        }else if(session != null && session.isOpen()) {
            if(!(session.isConnected())) {
                //m_objLogger.debug("######################## Hibernate session is open and not connected  !!!!!!!!!!!!!!!!!!!!!!!!");
                session = (sessionFactory != null) ? sessionFactory.openSession()
                : null;
                threadLocal.set(session);
            }else{
                //m_objLogger.debug("######################## Hibernate session is open and connected  !!!!!!!!!!!!!!!!!!!!!!!!");
            }
        }
        //m_objLogger.debug("######################## Hibernate returning session "+ session.isConnected() +" !!!!!!!!!!!!!!!!!!!!!!!!");
        return session;
    }
    
    /**
     *  Rebuild hibernate session factory
     *
     */
    public static void rebuildSessionFactory() {
        try {
            configuration.configure(CONFIG_FILE_LOCATION);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            System.err.println("%%%% Error Creating SessionFactory %%%%");
            e.printStackTrace();
        }
    }
    
    /**
     *  Close the single hibernate session instance.
     *
     *  @throws HibernateException
     */
    public static void closeSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);
        
        if (session != null) {
            if (session.isOpen() && session.isConnected())
            {
                session.close();
            }
            
            
        }
    }
    
    /**
     *  return session factory
     *
     */
    public static org.hibernate.SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    /**
     *  return session factory
     *
     *	session factory will be rebuilded in the next call
     */
    public static void setConfigFile(String configFile) {
        HibernateSessionFactory.configFile = configFile;
        sessionFactory = null;
    }
    
    /**
     *  return hibernate configuration
     *
     */
    public static Configuration getConfiguration() {
        return configuration;
    }

    public static void closeSessionFactory()
    {
        sessionFactory.close();
    }  
    
    /******************************/
    /* Alternate hibenate session */
    public static boolean createAltSessionFactory(String p_strUrl, String p_strUname, String p_strPwd)
    {
        try
        {
            altConfiguration = new Configuration();
            altConfiguration.addResource("hibernate.cfg.xml");
            altConfiguration.setProperty("hibernate.connection.url",p_strUrl);
            altConfiguration.setProperty("hibernate.connection.username",p_strUname);
            altConfiguration.setProperty("hibernate.connection.password",p_strPwd);
            altSessionFactory = altConfiguration.buildSessionFactory();
        }
        catch (Exception ex)
        {
            altSessionFactory = null;
            //ex.printStackTrace();
        }
        return (altSessionFactory == null) ? false : true;
    }
public static Session getAltSession() throws HibernateException {
        return altSessionFactory.openSession();
    }
    public static void closeAltSession(Session p_altSession) throws HibernateException {
        p_altSession.close();
    }
    
    public static void closeAltSessionFactory()
    {
        altSessionFactory.close();
    }
}
