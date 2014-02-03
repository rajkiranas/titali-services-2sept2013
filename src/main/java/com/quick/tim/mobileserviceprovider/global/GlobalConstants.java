/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.global;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author kishorp
 */
public class GlobalConstants {
    private static Properties properties = new Properties();
    static
    {
        try {
            loadProperties();
        } catch (IOException ex) {
          //  logger.debug("Exception occured in loadProperties() method, Exception=", ex);
            //Logger.getLogger(GlobalConstants.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
    
    
    
    static class PropertyLoader {

        public InputStream getProperty() {
            InputStream l_objInputStream = getClass().getClassLoader().
                    getResourceAsStream("Default.properties");
            return l_objInputStream;
        }
    }

    private static void loadProperties() throws IOException {
            GlobalConstants.PropertyLoader PL = new GlobalConstants.PropertyLoader();
            InputStream inputStream = PL.getProperty();
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
             // logger.debug("Properties File not found!");
            }
    }
 
     public static String getProperty(String key)
     {
         return properties.getProperty(key);
     }
    
    //public static String SERVER = "SAVILANSecureMessaging";    
    public static String SERVER = "ss00dpcddr0052";
    public static String AT_THE_RATE = "@";
    public static String CLIENT = "Smack";
    public static String DELETED_FROM_INBOX = "2";
    public static String UNREAD = "0";
    public static String SENT = "SENT";
    public static String INBOX = "INBOX";
    public static String DRAFT = "DRAFT";
    public static String TRASH = "TRASH";
    public static String FILE_DOWNLOAD_PATH= "c:\\safeMsg\\download\\"; //"c:\\safeMsg\\download\\";     // This path for 107  "E:\\SafeMSg_Mob_services\\restwed_download\\";
    public static String NO_DATA = "No Data";
    public static String EMPTY_STRING = "";
    public static String MSGFROM = "msgfrom";
    public static String MSGTO = "msgTo";
    public static String SUBJECT = "subject";
    public static String SENTTIME = "senttime";
    public static String ATTACHMENTPRESENT = "attachmentpresnet";
    public static String ISTHREADED = "isThreaded";
    public static String MESSAGEID = "messageId";
    public static String IS_MESSAGEREAD = "isMessageRead";
    public static String REPLYCNT = "replycnt";
    public static String PARENTMESSAGEID = "parentMessageid";
    public static String NEWMAIL_LIST = "newMailList";
    public static String ATTACHMENT = "attachment";
    public static String FILENAME = "filename";
    public static String ATTACHMNT_DETAILS = "attachmentdetails";
    public static String MSGBODY = "msgBody";
    public static String ATTAHCHMENTID_NAME = "attachIDName";
    public static String NEWMSG = "newMessage";
    public static String STATUS = "Status";
    public static String MSGTEXT = "msgText";
    public static String ATTCHMENT_DETAILS = "attachmentdetails";
    
    public static String ADDRESSBOOK = "addressBook";
    
    public static String PLAIN = "plain";
    public static String USER = "user";
    public static String USERNAME = "username";
    public static String GROUP = "group";
    public static String USERS = "Users";
    public static String GROUPS = "Groups";
    public static String REFRESHRATE = "refreshRate";
    public static String BURNTIME = "burnTime";
    public static String MSGLIFE = "msgLife";
    public static String RECID = "recId";
    public static String DELETE = "delete";
    public static String MSGTEXTLIST = "msgTextList";
    public static String ATTACHMENTID = "attachmentId";
    
    /////
    public static final String userName = "userName";
    public static final String password = "password";
    public static final String KEY="sateri@gmail.com";
    public static final String IV ="initialvector123";
    public static final int YES = 1;
    public static final int NO = 0;
    public static final String isAuthenticated = "isAuthenticated";
    public static final String admin = "admin";
    public static final String teacher = "teacher";
    public static final String student = "student";
    public static final String role="role";
    public static final String WHATSNEW = "whatsNew";
    public static final String NOTICES = "notices";
    public static final String wordOfTheDay = "wordOfTheDay";
    public static final String Technologies = "Technologies";
    public static final String category_distribution = "category_distribution";
    public static final String WHOSEDOINGWHAT = "Whodwht";
    public static final String auth_resource = "/auth";
    public static final String dashborad_resource = "/dashboard";
    public static final String UserMasterResource = "/UserMaster";
    public static final String CurrentUserProfile = "CurrentUserProfile";
    
    public static final String HYPHEN = "-";
    public static final String forwardSlash="/";
    public static final String DATEFORMAT = "MM/dd/yyyy";
    
    public static final String STANDARDLIST = "standardList";
    public static final String STUDENTLIST = "studentList";
    public static final String QUALIFICATIONLIST = "qualificationList";
    public static final String ISROLLNOEXIST="isRollNoExist";
    public static final String TEACHERLIST = "teacherList";
    public static final String ISUSERNAMEEXIST="isUsernameExist";
    
    public static final String StandardResource="/StandardMaster";
    public static final String QuickResource="/QuickMaster";
    public static final String WhatsNewResource="/WhatsNew";
    public static final String ForumResource="/ForumResource";
    public static final String DictResource="/DictResource";
    public static final String PlannerResource="/PlannerResource";
    public static final String WhosDoingWhatResource="/WhosDoingWhat";
    public static final String QUICKLEARNLIST="quickLearnList";
    public static final String MasterParamResource = "/MasterParam";
    public static final String STDSUBLIST = "stdsub";
    public static final String QuickLearnResource = "/QuickLearn";
    public static final String MYQUICKNOTEs="quicknotes";
    public static final String subjectList = "subjectList";
    public static final String divisionList = "divisionList";
    public static final String teacherStdDivSubIdList = "teacherStdDivSubIdList";
    
    public static final String EXAMRESOURCE = "examResource";
    public static final String EXAMLIST="examList";
    public static final String eventDetailsList="eventDetailsList";
    public static final String dictWordList="dictWordList";
    public static final String eventList="eventList";
    public static final String eventLikes="eventLikes";
    public static final String eventComments="eventComments";
    public static final String subjectWiseAvgPerformance="subjectWiseAvgPerformance";
    public static final String subwiseAvgScoreForStud="subwiseAvgScoreForStud";
     public static final String EXAMQUESTIONLIST ="examQuestionList";
     public static final String space =" ";
     public static final String COMMA =",";
     public static final String has ="has";
     public static final String is ="is";
     public static final String in ="in";
     public static final String going_through ="going through";
     public static final String shared ="shared";
     public static final String updated ="updated";
     public static final String topicInformation ="topic";
     public static final String gsonTimeFormat="yyyy-MM-dd'T'HH:mm:ss'Z'";
     public static final String PASS="PASS";
     public static final String FAIL="FAIL";
     public static final String noticeBoardResource = "noticeResource";
     public static final String technologyResource = "technologyResource";
     public static final String tripple_dots="...";
     public static final String TOPIC_INTRO_LENGTH="TOPIC_INTRO_LENGTH";
     public static final String ENABLE_MOBILE_ALERTS="ENABLE_MOBILE_ALERTS";
     
     
     //////////////////mail properties//////////////////////
    public static final String SMTP_HOST="SMTP_HOST";
    public static final String SMTP_PORT="SMTP_PORT";
    public static final String SOCKET_FACTORY_PORT="SOCKET_FACTORY_PORT";
    public static final String FROM_MAIL_ID="FROM_MAIL_ID";
    public static final String FROM_MAIL_PWD="FROM_MAIL_PWD";
    
    
    public static final String NEW_TOPIC_RELEASE_MSG="NEW_TOPIC_RELEASE_MSG";
    public static final String NEW_EXAM_RELEASE_MSG="NEW_EXAM_RELEASE_MSG";
    public static final String NEW_TECHNOLOGY_RELEASE_MSG="NEW_TECHNOLOGY_RELEASE_MSG";
    public static final String NEW_NOTICE_RELEASE_MSG="NEW_NOTICE_RELEASE_MSG";
    public static final String NEW_FORUM_EVENT_RELEASE_MSG="NEW_FORUM_EVENT_RELEASE_MSG";
    public static final String COMMENT_POSTED_MSG="COMMENT_POSTED_MSG";
    
    public static final String DASH_ACTIVITY_FETCH_SIZE="DASH_ACTIVITY_FETCH_SIZE";
    public static final String FORUM_EVENTS_FETCH_SIZE="FORUM_EVENTS_FETCH_SIZE";
    public static final String DICT_FETCH_SIZE="DICT_FETCH_SIZE";
    public static final String QUICK_LEARN_FETCH_SIZE="QUICK_LEARN_FETCH_SIZE";
    public static final String QUICK_UPLOAD_ADMIN_FETCH_SIZE="QUICK_UPLOAD_ADMIN_FETCH_SIZE";
     
     
}
