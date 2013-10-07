/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author prashantk
 */
package com.quick.tim.mobileserviceprovider.resource;

import com.google.gson.Gson;
import com.quick.tim.mobileserviceprovider.entity.*;
import com.quick.tim.mobileserviceprovider.services.NoticeBoardService;
import com.quick.tim.mobileserviceprovider.services.StudentService;
import com.quick.tim.mobileserviceprovider.services.WhatsNewService;
import com.quick.tim.mobileserviceprovider.services.WhoseDoingWhatService;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Component;
@Component
@Path("/person")
public class PersonResource {

    private final static String FIRST_NAME = "firstName";
    private final static String LAST_NAME = "lastName";
    private final static String EMAIL = "email";
    //private Person person = new Person(1, "Sample", "Person", "sample_person@jerseyrest.com");
    // The @Context annotation allows us to have certain contextual objects
    // injected into this class.
    // UriInfo object allows us to get URI information (no kidding).
    @Context
    UriInfo uriInfo;
    // Another "injected" object. This allows us to use the information that's
    // part of any incoming request.
    // We could, for example, get header information, or the requestor's address.
    @Context
    Request request;

    // Basic "is the service running" test
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String respondAsReady() {
        return "Demo service is ready!";
    }

 /*   @GET
    @Path("authenticate/{userName}/{password}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject isUserAuthenticated(@PathParam("userName") String userName, @PathParam("password") String password) throws JSONException {
        

        //@PathParam(EMAIL,EMAIL,EMAIL)
        JSONObject j = new JSONObject();

        LoginAuthenticatorService loginAuthenticatorService = new LoginAuthenticatorService();

        try {
            boolean isValidUser = loginAuthenticatorService.authenticate(userName, password, GlobalConstants.SERVER);
           
            if (isValidUser) {

                j.put(GlobalConstants.isAuthenticated, GlobalConstants.YES);
                j.put(GlobalConstants.ADDRESSBOOK, getAlluser());
                j.put(GlobalConstants.INBOX, getMails(userName));

            } else {
                j.put(GlobalConstants.isAuthenticated, "YES");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return j;
    } */
    // Use data from the client source to create a new Person object, returned in JSON format. 
    
    private static final Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
    private static final org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();


        

    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject login(JSONObject inputRequest) throws JSONException {


        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();
        //org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        
        Subject currentUser = SecurityUtils.getSubject();
        
        if(authenticate(currentUser,inputRequest,response))
        {
            //after authorization, method gives call to fetch logged in users profile
            authorize(currentUser,inputRequest,response);
        }
        
        return response;
    }
    
    
    //authentication ofthe current shiro user
    private boolean authenticate(Subject currentUser,JSONObject inputRequest, JSONObject response) throws JSONException  
    {
        // get the currently executing user:
        if (!currentUser.isAuthenticated()) {
            try 
            {
                //authentication
                UsernamePasswordToken token = new UsernamePasswordToken(inputRequest.getString(GlobalConstants.userName), inputRequest.getString(GlobalConstants.password));
                token.setRememberMe(true);
                currentUser.login(token);
                response.put(GlobalConstants.isAuthenticated, GlobalConstants.YES);
                
                
            } catch (UnknownAccountException uae)
            {
                response.put(GlobalConstants.isAuthenticated, GlobalConstants.NO);
                
            } catch (IncorrectCredentialsException ice) {
                response.put(GlobalConstants.isAuthenticated, GlobalConstants.NO);
               
            } catch (LockedAccountException lae) {
                response.put(GlobalConstants.isAuthenticated, GlobalConstants.NO);
            }
            catch (AuthenticationException ae) {
                response.put(GlobalConstants.isAuthenticated, GlobalConstants.NO);
                //unexpected condition?  error?
            }
        }
        return currentUser.isAuthenticated();
        
    }
    
    //authorization of the currently logged in shiro user
    private void authorize(Subject currentUser,JSONObject inputRequest, JSONObject response) throws JSONException 
    {
                if(currentUser.hasRole(GlobalConstants.student))
                {
                    response.put(GlobalConstants.role, GlobalConstants.student);
                    //fetch and set student profile
                }
                else
                {
                    response.put(GlobalConstants.role, GlobalConstants.teacher);
                    //admin role to be overwritten in response
                    if(currentUser.hasRole(GlobalConstants.admin))
                        response.put(GlobalConstants.role, GlobalConstants.admin);
                    //fetch and set staff profile
                    //fetchStaffProfile();
                    //add spring 
                    //add other home scrren methods
                    //handle alias in dao
                    //note  in pom xml , asm 3.1 is used which is compatible with jersey
                    // asm 1.5.3 is not compatible with jersey 
                    // but it is used by hibernate
                    // hibernate has exclusions for asm, cglib, asm attrs
                    
                    /*              WhatsNewService              */
//                    WhatsNewService ser = new WhatsNewService();
//                    List<Whatsnew>  list=  ser.getWhatsNewForMe("6", "A");
//                    Gson gson = new Gson();
//                    String json = gson.toJson(list);  
//                    response.put(GlobalConstants.WHATSNEW, json);
//                    
//                    /*whose doing what */
//                    
//                    WhoseDoingWhatService wdws = new WhoseDoingWhatService();
//                    List<Whoisdoingwhat> whoisdoingwhats=  wdws.WhoseDoingWhat("", "");
//                    Gson Whoisdoingwhat_gson = new Gson();
//                    
//                    String Whoisdoingwhat_json = Whoisdoingwhat_gson.toJson(whoisdoingwhats);  
//                    response.put(GlobalConstants.WHOSEDOINGWHAT, Whoisdoingwhat_json);
//                    
//                    
//                    NoticeBoardService nbs= new NoticeBoardService();
//                    List<Notices> noticeses= nbs.getNotices();
//                    Gson Notices_gson = new Gson();
//                    String Notices_json = Notices_gson.toJson(noticeses);  
//                    response.put(GlobalConstants.NOTICES, Notices_json);
                    
                }
    }
    
    
    
    @Path("save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject save(JSONObject inputRequest) throws JSONException {


        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();       
        StudentService service=new StudentService();
        
        UserMaster userMaster=new UserMaster();
        userMaster.setPrn(1);
        userMaster.setMobile(inputRequest.getLong("phnumber"));
        userMaster.setCreationdate(Long.valueOf("1234567890"));
        userMaster.setName(inputRequest.getString("name"));
        userMaster.setUsername(inputRequest.getString("username"));
        userMaster.setPassword(inputRequest.getString("password"));       
        userMaster.setStudentMasters(getStudentmaster(userMaster,inputRequest));
        userMaster.setQualificationMasters(null);
        userMaster.setSubs(null);        
        userMaster.setStds(null);
        userMaster.setTeacherMasters(null);
        userMaster.setTeacherStddivSubs(null);
        userMaster.setUserRoleses(null);
        try {
            service.saveStudent(userMaster);
        } catch (Exception ex) {
            Logger.getLogger(PersonResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    
   private Set<StudentMaster> getStudentmaster(UserMaster userMaster, JSONObject inputRequest) {
       Set<StudentMaster> sms=new HashSet<StudentMaster>(); 
       try {
           
            StudentMaster master=new StudentMaster();
              master.setRno(Short.valueOf(inputRequest.getString("rollNo")));
              master.setAddDate(new Date());
              master.setAddress(inputRequest.getString("address"));
              master.setEduYear(inputRequest.getString("edu_year"));
             
              if((inputRequest.getString("gender")).equals("Male")){
                 master.setGender('M');
              }else{
                 master.setGender('F');
              }
              
              
              master.setUsername(userMaster.getUsername());
              master.setUploadBy(null);
              master.setUploadCount(null);
              master.setDiv(inputRequest.getString("division"));
              master.setDob((Date)inputRequest.get("dob"));
              master.setUserMaster(userMaster);
              sms.add(master);
           
        } catch (JSONException ex) {
            Logger.getLogger(PersonResource.class.getName()).log(Level.SEVERE, null, ex);
        }
         return sms;
    }
   
   private Set<Std> getStd() {
      Set<Std> set=new HashSet<Std>();
      Std s=new Std();
      s.setStd("MCA");
      set.add(s);
      return set;
    }
    

    private Set<QualificationMaster> getQual() {
      Set<QualificationMaster> masters=new HashSet<QualificationMaster>();
      QualificationMaster qm=new QualificationMaster();
      qm.setQualificationName("MCA");  
    
      masters.add(qm);
      return masters;
    }

    private Set<Sub> getSub() {
        Set<Sub> s=new HashSet<Sub>();
         Sub s1=new Sub();
         s1.setSub("English");
         s.add(s1);
        return s;
    }
    
  
    @Path("dashboard")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject dashBoard(JSONObject inputRequest) throws JSONException {


        System.out.println("userTrack="+inputRequest);
        JSONObject response =  new JSONObject();
      
            WhatsNewService ser = new WhatsNewService();
                    List<Whatsnew>  list=  ser.getWhatsNewForMe("6", "A",true);
                    Gson gson = new Gson();
                    String json = gson.toJson(list);  
                    response.put(GlobalConstants.WHATSNEW, json);
                    
                    /*whose doing what */
                    
//                    WhoseDoingWhatService wdws = new WhoseDoingWhatService();
//                    List<Whoisdoingwhat> whoisdoingwhats=  wdws.WhoseDoingWhat("", "");
//                    Gson Whoisdoingwhat_gson = new Gson();
//                    
//                    String Whoisdoingwhat_json = Whoisdoingwhat_gson.toJson(whoisdoingwhats);  
//                    response.put(GlobalConstants.WHOSEDOINGWHAT, Whoisdoingwhat_json);
                    
                    
                    NoticeBoardService nbs= new NoticeBoardService();
                    List<Notices> noticeses= nbs.getNotices("","");
                    Gson Notices_gson = new Gson();
                    String Notices_json = Notices_gson.toJson(noticeses);  
                    response.put(GlobalConstants.NOTICES, Notices_json);
                    
                    return response;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
/*
    @GET
    @Path("getMessages/{username}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public List getMails(@PathParam("username") String user) {
        HashMap threadedmap = new HashMap();

        InboxService inboxService = new InboxService();
        List<Ofgroupuser> ofGroupUserlist = new ArrayList<Ofgroupuser>();
        List<Ssconversations> mailList = inboxService.getAllMessages(user, ofGroupUserlist, GlobalConstants.PLAIN);
        List list = new ArrayList();
        try {
            for (Ssconversations ssconversation : mailList) {

                //   if (!threadedmap.containsKey(ssconversation.getParentmessageid())) {
                JSONObject map = new JSONObject();

                if (ssconversation.getSsmailbox().getMsgfrom() != null) {
                    //   if(ssconversation.getSsmailbox().getMsgfrom().contains(GlobalConstants.AT_THE_RATE+GlobalConstants.SERVER)) {
                    String msgFrom = ssconversation.getSsmailbox().getMsgfrom().replaceAll(GlobalConstants.AT_THE_RATE + GlobalConstants.SERVER, "");
                    map.put(GlobalConstants.MSGFROM, msgFrom.trim());
                    //   }

                } else {
                    map.put(GlobalConstants.MSGFROM, GlobalConstants.NO_DATA);
                }

                if (ssconversation.getSsmailbox().getMsgto() != null) {
                    String msgTo = ssconversation.getSsmailbox().getMsgto().replaceAll(GlobalConstants.AT_THE_RATE + GlobalConstants.SERVER, "");
                    map.put(GlobalConstants.MSGTO, msgTo.trim());
                } else {
                    map.put(GlobalConstants.MSGTO, GlobalConstants.NO_DATA);
                }

                if (ssconversation.getSsmailbox().getSubject() != null) {
                    map.put(GlobalConstants.SUBJECT, ssconversation.getSsmailbox().getSubject());
                } else {
                    map.put(GlobalConstants.SUBJECT, GlobalConstants.NO_DATA);
                }

                if (ssconversation.getSenttimemobile() != null) {
                    map.put(GlobalConstants.SENTTIME, ssconversation.getSenttimemobile());

                } else {
                    if (ssconversation.getSenttimeweb() != null) {
                        map.put(GlobalConstants.SENTTIME, ssconversation.getSenttimeweb());

                    }
                }
                
                Set<Ssattachement> attachmentSet = ssconversation.getSsmailbox().getSsattachements();
                if (!attachmentSet.isEmpty()) {
                    map.put(GlobalConstants.ATTACHMENTPRESENT, GlobalConstants.YES);
                } else {
                    map.put(GlobalConstants.ATTACHMENTPRESENT, GlobalConstants.NO);
                }

                map.put(GlobalConstants.ISTHREADED, GlobalConstants.NO);
                map.put(GlobalConstants.MESSAGEID, ssconversation.getSsmailbox().getMessageid());
                if (ssconversation.getMessagestatus() == null) {
                    map.put(GlobalConstants.IS_MESSAGEREAD, GlobalConstants.NO);
                } else {
                    map.put(GlobalConstants.IS_MESSAGEREAD, Integer.parseInt(ssconversation.getMessagestatus()));
                }
                map.put(GlobalConstants.REPLYCNT, 0);

                threadedmap.put(ssconversation.getSsmailbox().getMessageid(), map);
//                } else {
//                    JSONObject map = (JSONObject) threadedmap.get(ssconversation.getParentmessageid());
//                    int cnt = map.getInt(GlobalConstants.REPLYCNT);
//                    map.put(GlobalConstants.ISTHREADED, true);
//                    map.put(GlobalConstants.REPLYCNT, ++cnt);
//                    threadedmap.put(ssconversation.getParentmessageid(), map);
//                }
            }

            Set keySet = threadedmap.keySet();
            Iterator it = keySet.iterator();
            while (it.hasNext()) {
                list.add(threadedmap.get(it.next()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @GET
    @Path("getUsernames")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getAlluser() {

        JSONObject mainJsonObject = new JSONObject();
        JSONObject usersJsonObject = new JSONObject();

        OfUserService ofUserService = new OfUserService();
        List<Ofuser> ofuserList = ofUserService.getAlluser();

        OfgroupuserService ofgroupuserService = new OfgroupuserService();
        List usersList = new ArrayList();
        JSONObject userAndGroupJsonObject = new JSONObject();
        Set groupSet = new HashSet();

        try {

            for (Ofuser ofuser : ofuserList) {

                JSONObject map = new JSONObject();
                JSONArray jsonArray = new JSONArray();

                if (ofuser.getName() != null) {
                    map.put(GlobalConstants.USER, ofuser.getName());
                } else {
                    map.put(GlobalConstants.USER, GlobalConstants.NO_DATA);
                }

                if (ofuser.getUsername() != null) {
                    map.put(GlobalConstants.USERNAME, ofuser.getUsername());
                } else {
                    map.put(GlobalConstants.USERNAME, GlobalConstants.NO_DATA);
                }


                List<Ofgroupuser> ofgroupuserList = ofgroupuserService.getUserGroupByUserId(ofuser.getUsername());

                for (Ofgroupuser ofgroupuser : ofgroupuserList) {

                    jsonArray.put(ofgroupuser.getId().getGroupname());
                    groupSet.add(ofgroupuser.getId().getGroupname());
                }
                //    if (jsonArray.length() > 0) {
                map.put(GlobalConstants.GROUP, jsonArray);
                //    }
                usersList.add(map);
                if (usersList.size() > 0) {
                    userAndGroupJsonObject.put(GlobalConstants.USERS, usersList);
                } else {
                    userAndGroupJsonObject.put(GlobalConstants.USERS, GlobalConstants.NO_DATA);
                }

            }

            Iterator iterator = groupSet.iterator();
            JSONArray groupsArray = new JSONArray();

            while (iterator.hasNext()) {
                groupsArray.put(iterator.next());
            }
            //if (groupsArray.length() > 0) {
            userAndGroupJsonObject.put(GlobalConstants.GROUPS, groupsArray);
            // }
            // mainJsonObject.put("Address book", userAndGroupJsonObject);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return userAndGroupJsonObject;
    }

    @GET
    @Path("getMasterParams")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getMasterParams() {

        MasterPropertiesService masterPropService = new MasterPropertiesService();
        List<Masterproperties> mstPropServiceList = masterPropService.getMasterProperties();
        JSONObject jsonObj = new JSONObject();
        for (Masterproperties mstProp : mstPropServiceList) {
            try {
                if (mstProp.getInboxRefreshRate() != null) {
                    jsonObj.put(GlobalConstants.REFRESHRATE, mstProp.getInboxRefreshRate());
                } else {
                    jsonObj.put(GlobalConstants.REFRESHRATE, GlobalConstants.NO_DATA);
                }
                if (mstProp.getMobileBurnTime() != null) {
                    jsonObj.put(GlobalConstants.BURNTIME, mstProp.getMobileBurnTime());
                } else {
                    jsonObj.put(GlobalConstants.BURNTIME, GlobalConstants.NO_DATA);
                }
                if (mstProp.getMsgLife() != null) {
                    jsonObj.put(GlobalConstants.MSGLIFE, mstProp.getMsgLife());
                } else {
                    jsonObj.put(GlobalConstants.MSGLIFE, GlobalConstants.NO_DATA);
                }
                jsonObj.put(GlobalConstants.RECID, mstProp.getRecid());

            } catch (JSONException ex) {

                ex.printStackTrace();
            }
        }
        return jsonObj;
    }

    @GET
    @Path("recordTimeAndGeoLoc/{userId}/{deviceId}/{latitude}/{longitude}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public JSONObject recordTimeAndGeoLoc(@PathParam("userId") String userId, @PathParam("deviceId") int deviceId, @PathParam("latitude") String latitude, @PathParam("longitude") String longitude) {
        JSONObject returnRecjson = new JSONObject();
        SssessiontrackService sessionTrackService = new SssessiontrackService();
        Sssessiontrack sessionTrack = new Sssessiontrack();
        if (!userId.contains(GlobalConstants.AT_THE_RATE)) {
            userId = userId.trim() + GlobalConstants.AT_THE_RATE + GlobalConstants.SERVER;
        }
        sessionTrack.setUserid(userId);
        sessionTrack.setDeviceId(deviceId);
        sessionTrack.setAccessTime(new Date());
        sessionTrack.setLatitude(latitude);
        sessionTrack.setLogitude(latitude);

        try {
            sessionTrackService.insertTimeAndGeoLoc(sessionTrack);
            returnRecjson.put(GlobalConstants.STATUS, GlobalConstants.YES);
        } catch (JSONException ex) {
            Logger.getLogger(PersonResource.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        return returnRecjson;
    }

    @GET
    @Path("setImpFlag/{messageId}/{mark}/{userId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject setImpFlag(@PathParam("messageId") int messageId, @PathParam("mark") boolean mark, @PathParam("userId") String userId) {
        JSONObject json = new JSONObject();
        SsConversationService conversationService = new SsConversationService();

        try {
            conversationService.setImp(mark, messageId, userId);
            json.put(GlobalConstants.STATUS, GlobalConstants.YES);
        } catch (JSONException ex) {
            try {
                json.put(GlobalConstants.STATUS, GlobalConstants.NO);
            } catch (JSONException ex1) {
                Logger.getLogger(PersonResource.class.getName()).log(Level.SEVERE, null, ex1);
                ex1.printStackTrace();
            }
            Logger.getLogger(PersonResource.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return json;
    }

    @GET
    @Path("refreshContactList")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject refreshAddressBook() {

        JSONObject returnRefreshAddrsbook = new JSONObject();
        OfUserService ofUserService = new OfUserService();

        List<Ofuser> updateOfUserList = ofUserService.refreshContactList();

        OfgroupuserService ofgroupuserService = new OfgroupuserService();

        for (Ofuser ofuser : updateOfUserList) {

            JSONObject groupJson = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            try {
                if (ofuser.getName() != null) {
                    groupJson.put("user", ofuser.getName());
                } else {
                    groupJson.put("user", GlobalConstants.NO_DATA);
                }

                if (ofuser.getUsername() != null) {
                    groupJson.put("username", ofuser.getUsername());
                } else {
                    groupJson.put("username", GlobalConstants.NO_DATA);
                }

                groupJson.put("creationdate", ofuser.getCreationdate());

                List<Ofgroupuser> newUserGroupList = ofgroupuserService.getNewUserGroup(ofuser.getUsername());

                for (Ofgroupuser ofgroupuser : newUserGroupList) {

                    jsonArray.put(ofgroupuser.getId().getGroupname());
                }
                if (jsonArray.length() > 0) {
                    groupJson.put("group", jsonArray);
                } else {
                    groupJson.put("group", "");
                }
                returnRefreshAddrsbook.put("Users", groupJson);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return returnRefreshAddrsbook;
    }

    @DELETE
    @Path("deleteMsg/{username}/{folder}/{messageId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteMail(@PathParam("username") String username, @PathParam("folder") String folder, @PathParam("messageId") int messageId) {

        JSONObject deleteRespJson = new JSONObject();
        SsConversationService ssConversationService = new SsConversationService();
        try {
            int result = ssConversationService.deleteMessage(messageId, folder, username);
            if (result > 0) {
                deleteRespJson.put(GlobalConstants.DELETE, result);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return deleteRespJson;
    }

    @GET
    @Path("setMobileReceiveTime/{messageId}/{userId}/{folder}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject setMobileReceiveTime(@PathParam("messageId") int messageId, @PathParam("userId") String userId, @PathParam("folder") String folder) {
        JSONObject json = new JSONObject();
        SsConversationService conversationService = new SsConversationService();

        try {
            conversationService.setReceiveTime(messageId, userId, folder);
            json.put(GlobalConstants.STATUS, GlobalConstants.YES);
        } catch (JSONException ex) {
            try {
                json.put(GlobalConstants.STATUS, GlobalConstants.NO);
            } catch (JSONException ex1) {
                ex.printStackTrace();
            }
            ex.printStackTrace();
        }
        return json;
    }

    @GET
    @Path("setMobileReadTime/{messageId}/{userId}/{folder}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject setMobileReadTime(@PathParam("messageId") int messageId, @PathParam("userId") String userId, @PathParam("folder") String folder) {
        JSONObject updateRowJson = new JSONObject();
        SsConversationService conversationService = new SsConversationService();
        //if(messageId.length>0) {
        int updtRow = conversationService.setReadTime(messageId, userId, folder);
        try {
            if (updtRow != 0) {
                updateRowJson.put(GlobalConstants.STATUS, GlobalConstants.YES);
            } else {
                updateRowJson.put(GlobalConstants.STATUS, GlobalConstants.NO);
            }
        } catch (JSONException ex) {
            Logger.getLogger(PersonResource.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return updateRowJson;
    }
    // }

    @GET
    @Path("fetchNewMessages/{messageId}/{userId}/{folder}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject fetchNewMessages(@PathParam("messageId") int messageId, @PathParam("userId") String userId, @PathParam("folder") String folder) throws JSONException {

        SsConversationService conversationService = new SsConversationService();
        JSONObject jsonObj = new JSONObject();
        List jsonlist = new ArrayList();
        JSONObject jsonListObj = new JSONObject();

        if (!userId.contains(GlobalConstants.AT_THE_RATE)) {
            userId = userId.trim() + GlobalConstants.AT_THE_RATE + GlobalConstants.SERVER;
        }

        List<Ssconversations> list = conversationService.fetchNewMesg(userId, messageId, folder);
        if (list.size() > 0) {

            for (Ssconversations conversationObj : list) {
                try {
                    jsonObj = new JSONObject();
                    jsonObj.put(GlobalConstants.MESSAGEID, conversationObj.getSsmailbox().getMessageid());

                    if (conversationObj.getParentmessageid() != null) {
                        jsonObj.put(GlobalConstants.PARENTMESSAGEID, conversationObj.getParentmessageid());
                    } else {
                        jsonObj.put(GlobalConstants.PARENTMESSAGEID, GlobalConstants.NO_DATA);
                    }
                    if (conversationObj.getSsmailbox().getSubject() != null) {
                        jsonObj.put(GlobalConstants.SUBJECT, conversationObj.getSsmailbox().getSubject());
                    } else {
                        jsonObj.put(GlobalConstants.SUBJECT, GlobalConstants.NO_DATA);
                    }

                    if (conversationObj.getSsmailbox().getMsgfrom() != null) {
                        String msgFrom = conversationObj.getSsmailbox().getMsgfrom().replaceAll(GlobalConstants.AT_THE_RATE + GlobalConstants.SERVER, "");
                        jsonObj.put(GlobalConstants.MSGFROM, msgFrom.trim());
                    } else {
                        jsonObj.put(GlobalConstants.MSGFROM, GlobalConstants.NO_DATA);
                    }
                    if (conversationObj.getSsmailbox().getMsgto() != null) {
                        String msgTo = conversationObj.getSsmailbox().getMsgto().replaceAll(GlobalConstants.AT_THE_RATE + GlobalConstants.SERVER, "");
                        jsonObj.put(GlobalConstants.MSGTO, msgTo.trim());
                    } else {
                        jsonObj.put(GlobalConstants.MSGTO, GlobalConstants.NO_DATA);
                    }
                    if (conversationObj.getSenttimeweb() != null) {
                        jsonObj.put(GlobalConstants.SENTTIME, conversationObj.getSenttimeweb());
                    } else {
                        jsonObj.put(GlobalConstants.SENTTIME, GlobalConstants.NO_DATA);
                    }

                    Set<Ssattachement> attachmentSet = conversationObj.getSsmailbox().getSsattachements();

                    if (!attachmentSet.isEmpty()) {
                        jsonObj.put(GlobalConstants.ATTACHMENTPRESENT, GlobalConstants.YES);
                    } else {
                        jsonObj.put(GlobalConstants.ATTACHMENTPRESENT, GlobalConstants.NO);
                    }
                    if (conversationObj.getParentmessageid() != 0 && conversationObj.getParentmessageid() != null) {
                        jsonObj.put(GlobalConstants.ISTHREADED, GlobalConstants.YES);
                    } else {
                        jsonObj.put(GlobalConstants.ISTHREADED, GlobalConstants.NO);
                    }

                    if (conversationObj.getMessagestatus() == null) {
                        jsonObj.put(GlobalConstants.IS_MESSAGEREAD, GlobalConstants.NO);
                    } else {
                        jsonObj.put(GlobalConstants.IS_MESSAGEREAD, Integer.parseInt(conversationObj.getMessagestatus()));
                    }
                    jsonlist.add(jsonObj);

                } catch (JSONException ex) {

                    ex.printStackTrace();
                }
            }
            jsonListObj.put(GlobalConstants.NEWMAIL_LIST, jsonlist);
        } else {
            jsonListObj.put(GlobalConstants.NEWMAIL_LIST, jsonlist);
        }
        return jsonListObj;
    }

    @GET
    @Path("downloadAttachment/{attachmentId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject downloadAttachment(@PathParam("attachmentId") int attachmentId) {
        JSONObject attachmentJson = null;
        JSONObject returnAttachmentJson = new JSONObject();
        SsattachementService ssattachementService = new SsattachementService();
        List<Ssattachement> ssattachements = ssattachementService.getAttachmentByMesgId(attachmentId);
        for (Ssattachement ssattachement : ssattachements) {
            try {
                if (ssattachement.getAttachment().length > 0 && ssattachement.getFilename() != null) {
                    attachmentJson = new JSONObject();
                    String attachment = new String(Base64.encode(ssattachement.getAttachment()));
                    attachmentJson.put(GlobalConstants.ATTACHMENT, attachment);
                        downloadAttachmentTestString(attachment, ssattachement.getFilename());
                    attachmentJson.put(GlobalConstants.FILENAME, ssattachement.getFilename());
                } else {
                    returnAttachmentJson.put(GlobalConstants.ATTACHMENT, GlobalConstants.NO_DATA);
                    returnAttachmentJson.put(GlobalConstants.FILENAME, GlobalConstants.NO_DATA);
                }
                returnAttachmentJson.put(GlobalConstants.ATTACHMNT_DETAILS, attachmentJson);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return returnAttachmentJson;
    }

    @GET
    @Path("getMsgbody/{messageId}/{folder}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getMsgBody(@PathParam("messageId") int messageId, @PathParam("folder") String folder) {

        JSONObject jasonObj = new JSONObject();
        SsConversationService service = new SsConversationService();
        List<Ssconversations> list = service.getMessageBody(messageId, folder, false);
        Set ssAttachmentSet;
        List ssAttachmentList = new ArrayList();
        for (Ssconversations ssConversition : list) {
            try {
                if (ssConversition.getSsmailbox().getMessagebody() != null) {
                    jasonObj.put(GlobalConstants.MSGBODY, ssConversition.getSsmailbox().getMessagebody());
                } else {
                    jasonObj.put(GlobalConstants.MSGBODY, GlobalConstants.NO_DATA);
                }
                if (!ssConversition.getSsmailbox().getSsattachements().isEmpty()) {
                    ssAttachmentSet = ssConversition.getSsmailbox().getSsattachements();
                    Iterator itr = ssAttachmentSet.iterator();
                    while (itr.hasNext()) {
                        Ssattachement ssattachement = (Ssattachement) itr.next();
                        ssAttachmentList.add(ssattachement.getAttachementid());
                        ssAttachmentList.add(ssattachement.getFilename());
                        jasonObj.put(GlobalConstants.ATTAHCHMENTID_NAME, ssAttachmentList);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return jasonObj;
        }
        return jasonObj;
    }

    @GET
    @Path("getMsgThreadedBody/{messageId}/{folder}/{isThreaded}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getMsgThreadedBody(@PathParam("messageId") int messageId, @PathParam("folder") String folder, @PathParam("isThreaded") boolean isThreaded) {

        JSONObject jasonObj = new JSONObject();
        // Set msgTextList = new HashSet();
        SsConversationService service = new SsConversationService();
        JSONObject returnJasonObj = new JSONObject();
        List<Ssconversations> list = service.getMessageBody(messageId, folder, isThreaded);
        // HashMap msgBodyMap = new HashMap();
        Set ssAttachmentSet;
        JSONArray ssAttachmentArray = new JSONArray();
        JSONObject fileJeson;
        if (!list.isEmpty()) //  for (Ssconversations ssConversition : list)
        {
            Ssconversations ssConversition = list.get(0);
            try {

                // msgBodyMap = new HashMap();
                jasonObj = new JSONObject();

                jasonObj.put(GlobalConstants.MSGFROM, ssConversition.getSsmailbox().getMsgfrom());
                jasonObj.put(GlobalConstants.MSGTO, ssConversition.getSsmailbox().getMsgto());
                jasonObj.put(GlobalConstants.MSGBODY, ssConversition.getSsmailbox().getMessagebody());
                jasonObj.put(GlobalConstants.MESSAGEID, ssConversition.getSsmailbox().getMessageid());

                if (!ssConversition.getSsmailbox().getSsattachements().isEmpty()) {
                    ssAttachmentSet = ssConversition.getSsmailbox().getSsattachements();
                    Iterator itr = ssAttachmentSet.iterator();
                    while (itr.hasNext()) {
                        fileJeson = new JSONObject();
                        Ssattachement ssattachement = (Ssattachement) itr.next();
                        // ssAttachmentmap.put("attachmentId",ssattachement.getAttachementid());
                        fileJeson.put(GlobalConstants.FILENAME, ssattachement.getFilename());
                        fileJeson.put(GlobalConstants.ATTACHMENTID, ssattachement.getAttachementid());
                        ssAttachmentArray.put(fileJeson);
                    }
                    jasonObj.put(GlobalConstants.ATTACHMNT_DETAILS, ssAttachmentArray);
                } else {
                    jasonObj.put(GlobalConstants.ATTACHMNT_DETAILS, ssAttachmentArray);
                }
                // msgTextList.add(jasonObj);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        try {
            returnJasonObj.put(GlobalConstants.MSGTEXTLIST, jasonObj);
        } catch (JSONException ex) {
            Logger.getLogger(PersonResource.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return returnJasonObj;
    }

    // service for attachment download folder path
//    @GET
//    @Path("downloadAttachment/{attachmentID}")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String downloadAttachment(@PathParam("attachmentID") int attachmentID) {
//        String filePath = null;
//        JSONObject jasonObj = new JSONObject();
//        SsattachementService attach = new SsattachementService();
//        List<Ssattachement> list = attach.getAttachmentByMesgId(attachmentID);
//        for (Ssattachement attac : list) {
//            filePath = downloadAttachment(attac.getAttachment(), attac.getFilename());
//            System.out.println("file downlaod path=" + filePath);
//            try {
//                jasonObj.put(GlobalConstants.ATTACHMENT, filePath);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        System.out.println("jASON" + jasonObj);
//        return filePath;
//    }
    
 // Test attachment download method
    private String downloadAttachmentTestString(String data, String filename) {
        File dwnfile = null;
        String str = "\\/9j\\/4AAQSkZJRgABAQAAAQABAAD\\/4QB+RXhpZgAATU0AKgAAAAgABQESAAMAAAABAAEAAAExAAIAAAAXAAAASgEyAAIAAAAUAAAAYqACAAQAAAABAAAAFqADAAQAAAABAAAAGQAAAABmLXNwb3QgdmVyc2lvbiAwLjYuMS41AAAyMDEzOjAxOjExIDE0OjQ3OjU0AP\\/bAEMABQQEBAQDBQQEBAYFBQYIDQgIBwcIEAsMCQ0TEBQTEhASEhQXHRkUFhwWEhIaIxocHh8hISEUGSQnJCAmHSAhIP\\/bAEMBBQYGCAcIDwgIDyAVEhUgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIP\\/AABEIABkAFgMBIgACEQEDEQH\\/xAAfAAABBQEBAQEBAQAAAAAAAAAAAQIDBAUGBwgJCgv\\/xAC1EAACAQMDAgQDBQUEBAAAAX0BAgMABBEFEiExQQYTUWEHInEUMoGRoQgjQrHBFVLR8CQzYnKCCQoWFxgZGiUmJygpKjQ1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4eLj5OXm5+jp6vHy8\\/T19vf4+fr\\/xAAfAQADAQEBAQEBAQEBAAAAAAAAAQIDBAUGBwgJCgv\\/xAC1EQACAQIEBAMEBwUEBAABAncAAQIDEQQFITEGEkFRB2FxEyIygQgUQpGhscEJIzNS8BVictEKFiQ04SXxFxgZGiYnKCkqNTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqCg4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2dri4+Tl5ufo6ery8\\/T19vf4+fr\\/2gAMAwEAAhEDEQA\\/APc\\/hroXiOb4SeD5YvHN7bxNo1oyxJZW2EHkodo3RkkAcZNdZ\\/wjnij\\/AKKDqH\\/gDa\\/\\/ABuuR+G2t+J4fhL4Pht\\/BFxPGmj2ipKL+3XeohTDYLAjPpiuu\\/4SHxb\\/ANE\\/uf8AwYW\\/\\/wAVQByvjPUNW8D6NDq2peM9buopp1t1S00+zdwSrNnBQfLhcdetFT+K7PVPGOkx6Zrfw9v5beOcXCrDq0MbbgrKDlW6YY8UV59aGLc70WrfM9HDywih++Tv5WK3w08e+CrP4R+DrW58UadFPDo1ossbTBSjCFAQQeRz611n\\/CyfAP8A0N2l\\/wDgQtRfDf8A5JL4P\\/7Bdr\\/6LWuxr0Dzjwz4uazaeMfCFpp3gn4oaX4f1CK9SeS5N3s3xCN1KZUE\\/eZT\\/wABor2cf8fC\\/R\\/5iil7NPUXt5Q0SP\\/Z";
        byte[] newData = null;
        newData = Base64.decode(data);


        try {
            dwnfile = new File(GlobalConstants.FILE_DOWNLOAD_PATH + filename);
            FileOutputStream fos = new FileOutputStream(dwnfile);
            fos.write(newData);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
        return dwnfile.getAbsolutePath();
    }

    @POST
    @Path("/sendMessage")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject sendMail(String mail) throws XMPPException {

        JSONObject returnJson = new JSONObject();


        try {
            JSONObject json = new JSONObject(mail);


            JSONObject jSONObject = json.getJSONObject(GlobalConstants.NEWMSG);
            int isAttachmentPresent = json.getInt(GlobalConstants.ATTACHMENTPRESENT);
            String messageTo = jSONObject.getString(GlobalConstants.MSGTO);
            String messageFrom = jSONObject.getString(GlobalConstants.MSGFROM);

            if ((messageTo != null && !messageTo.equals(GlobalConstants.EMPTY_STRING)) && (messageFrom != null && !messageFrom.equals(GlobalConstants.EMPTY_STRING))) {
                Ssmailbox ssmailbox = getSsmailbox(jSONObject, json);
                SsMailBoxService ssMailBoxService = new SsMailBoxService();
                ssMailBoxService.sendMessage(ssmailbox);

                returnJson.put(GlobalConstants.STATUS, GlobalConstants.YES);
                returnJson.put(GlobalConstants.MESSAGEID, ssmailbox.getMessageid());
            } else {
                returnJson.put(GlobalConstants.STATUS, GlobalConstants.NO);
            }


            //Chatting purpose code 
//              String messageTo = (String) jSONObject.getString("msgTo");
////            String messageFrom = (String) jSONObject.get("msgFrom").toString();
////            String msgAttachment = (String) jSONObject.get("attachment").toString();
//              String msgSubject = (String) jSONObject.getString("subject");
//              String messageText = (String) jSONObject.getString("msgText");
////            filename = jSONObject.getString("filename");
//             MyApplication myApplication = new MyApplication();
//            Connection connection = myApplication.getXmppConnection();
//            Chat chat = connection.getChatManager().createChat(messageTo, new MessageListener() {
//
//                @Override
//                public void processMessage(Chat chat, Message message) {
//                }
//            });
//
//            Message msg = new Message(GlobalConstants.EMPTY_STRING, Message.Type.normal);
//
//            if (msgSubject!= null) {
//                msg.setSubject(msgSubject);
//            } else {
//                msg.setSubject(GlobalConstants.EMPTY_STRING);
//            }
//
//            if (messageText!= null) {
//                msg.setBody(messageText);
//            } else {
//                msg.setBody(GlobalConstants.EMPTY_STRING);
//            }
//            System.out.println("Thread Id = " + chat.getThreadID());
//
//            chat.sendMessage(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnJson;
    }

    private Ssmailbox getSsmailbox(JSONObject jSONObject, JSONObject mainJson) {

        byte attachment[] = null;
        Ssmailbox ssmailbox = new Ssmailbox();
        try {
            String messageTo = jSONObject.getString(GlobalConstants.MSGTO);
            String messageFrom = jSONObject.getString(GlobalConstants.MSGFROM);
            String msgSubject = jSONObject.getString(GlobalConstants.SUBJECT);
            String messageText = jSONObject.getString(GlobalConstants.MSGTEXT);

            JSONArray attachmentJSONArray = mainJson.getJSONArray(GlobalConstants.ATTCHMENT_DETAILS);
            int attachMentPresent = mainJson.getInt(GlobalConstants.ATTACHMENTPRESENT);
            Integer parentMsgId = jSONObject.getInt(GlobalConstants.PARENTMESSAGEID);

            if (!messageTo.contains(GlobalConstants.AT_THE_RATE)) {
                messageTo = messageTo.trim() + GlobalConstants.AT_THE_RATE + GlobalConstants.SERVER;
            }
            if (!messageFrom.contains(GlobalConstants.AT_THE_RATE)) {
                messageFrom = messageFrom.trim() + GlobalConstants.AT_THE_RATE + GlobalConstants.SERVER;
            }
            ssmailbox.setMessagebody(messageText);
            ssmailbox.setMsgfrom(messageFrom);
            ssmailbox.setMsgto(messageTo);
            ssmailbox.setSubject(msgSubject);

            ssmailbox.setSsconversationses(getSsconversations(ssmailbox, messageTo, parentMsgId));

            Set<Ssattachement> ssattachmentSet = new HashSet<Ssattachement>();

            for (int i = 0; i < attachmentJSONArray.length(); i++) {

                JSONObject attachmentKeyValueJson = attachmentJSONArray.getJSONObject(i);
                Iterator keysItr = attachmentKeyValueJson.keys();
                while (keysItr.hasNext()) {
                    String attachmentName = (String) keysItr.next();
                    String attachedFile = attachmentKeyValueJson.getString(attachmentName);

                    attachment = Base64.decode(attachedFile);
                    Ssattachement ssattachement = new Ssattachement(attachmentName, ssmailbox, attachment);
                    ssattachmentSet.add(ssattachement);
                }
            }
            ssmailbox.setSsattachements(ssattachmentSet);


        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return ssmailbox;
    }

    private Set<Ssconversations> getSsconversations(Ssmailbox ssmailbox, String messatgeTo, Integer parentMsgId) {
        Set<Ssconversations> s = new HashSet<Ssconversations>();

        s.add(getSsconversationsPojo(ssmailbox, ssmailbox.getMsgfrom(), GlobalConstants.SENT, parentMsgId));
        String msgto[] = messatgeTo.split(",");
        // for inbox
        for (int i = 0; i < msgto.length; i++) {
            s.add(getSsconversationsPojo(ssmailbox, msgto[i], GlobalConstants.INBOX, parentMsgId));

        }

        return s;
    }

    private Ssconversations getSsconversationsPojo(Ssmailbox ssmailbox, String userId, String folder, Integer parentMsgId) {

        Ssmailbox ssmail = new Ssmailbox();
        ssmail = ssmailbox;
        Ssconversations ssconversations = new Ssconversations();
        ssconversations.setFolder(folder);
        ssconversations.setImpflag(false);
        //  ssconversations.setMessagestatus(GlobalConstants.UNREAD);
        if (!userId.contains(GlobalConstants.AT_THE_RATE)) {
            userId = userId.trim() + GlobalConstants.AT_THE_RATE + GlobalConstants.SERVER;
        }

        if (parentMsgId == 0 || parentMsgId == null) {
            ssconversations.setParentmessageid(0);
        } else {
            ssconversations.setParentmessageid(parentMsgId);
        }

        ssconversations.setReadtimemobile(null);
        ssconversations.setReadtimeweb(null);
        ssconversations.setReceivetimemobile(null);
        ssconversations.setReceivetimeweb(null);
        ssconversations.setSenttimemobile(new Date());
        ssconversations.setSenttimeweb(new Date());
        ssconversations.setSsmailbox(ssmail);
        ssconversations.setStoreflag(false);
        ssconversations.setMessagestatus(GlobalConstants.UNREAD);
        ssconversations.setUserid(userId.trim());

        return ssconversations;
    }

    
*/
    
}
