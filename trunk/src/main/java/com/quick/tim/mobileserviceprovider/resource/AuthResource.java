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
import com.google.gson.reflect.TypeToken;
import com.quick.tim.mobileserviceprovider.bean.Userprofile;
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import com.sun.jersey.api.core.ResourceContext;

import java.lang.reflect.Type;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
@Path(GlobalConstants.auth_resource)
public class AuthResource {

    private static final String shiro_ini_classpath = "classpath:shiro.ini";
    private static final String strLoginPath = "login";
    private static final Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(shiro_ini_classpath);
    private static final org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
    /*
     * @Context UriInfo uriInfo; // We could, for example, get header
     * information, or the requestor's address. @Context Request request;
     */
    @Context
    private ResourceContext resourceContext;

    // Basic "is the service running" test
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String respondAsReady() {
        return "Demo service is ready!";
    }

    @Path(strLoginPath)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject login(JSONObject inputRequest) throws JSONException {


        System.out.println("userTrack=" + inputRequest);
        JSONObject response = new JSONObject();
        //org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        Subject currentUser = SecurityUtils.getSubject();

        if (authenticateWithShiro(currentUser, inputRequest, response)) {
            //after authorization, method gives call to fetch logged in users profile
            authorize(currentUser, inputRequest, response);
            logoutFromShiro(currentUser);
        }

        return response;
    }

    //authentication ofthe current shiro user
    private boolean authenticateWithShiro(Subject currentUser, JSONObject inputRequest, JSONObject response) throws JSONException {
        // get the currently executing user:
        if (!currentUser.isAuthenticated()) {
            try {
                //authentication
                UsernamePasswordToken token = new UsernamePasswordToken(inputRequest.getString(GlobalConstants.userName), inputRequest.getString(GlobalConstants.password));
                //token.setRememberMe(true);
                currentUser.login(token);
                response.put(GlobalConstants.isAuthenticated, GlobalConstants.YES);


            } catch (UnknownAccountException uae) {
                response.put(GlobalConstants.isAuthenticated, GlobalConstants.NO);
                uae.printStackTrace();

            } catch (IncorrectCredentialsException ice) {
                response.put(GlobalConstants.isAuthenticated, GlobalConstants.NO);
                ice.printStackTrace();

            } catch (LockedAccountException lae) {
                response.put(GlobalConstants.isAuthenticated, GlobalConstants.NO);
                lae.printStackTrace();
            } catch (AuthenticationException ae) {
                response.put(GlobalConstants.isAuthenticated, GlobalConstants.NO);
                ae.printStackTrace();
                //unexpected condition?  error?
            }
            catch (Exception e) {
                response.put(GlobalConstants.isAuthenticated, GlobalConstants.NO);
                e.printStackTrace();
                //unexpected condition?  error?
            }
        }
        return currentUser.isAuthenticated();

    }

    //authorization of the currently logged in shiro user
    private void authorize(Subject currentUser, JSONObject inputRequest, JSONObject response) throws JSONException {
        if (currentUser.hasRole(GlobalConstants.student)) {
            response.put(GlobalConstants.role, GlobalConstants.student);
            //fetch and set student profile

        } else {
            response.put(GlobalConstants.role, GlobalConstants.teacher);
            //admin role to be overwritten in response
            if (currentUser.hasRole(GlobalConstants.admin)) {
                response.put(GlobalConstants.role, GlobalConstants.admin);
            }
       
        }

        //fetch logged in user profile
        UserMasterResource userResource = resourceContext.getResource(UserMasterResource.class);
        String jsonStringUserProfile = userResource.getUserProfile(inputRequest);
        response.put(GlobalConstants.CurrentUserProfile, jsonStringUserProfile);
        
         Type listType = new TypeToken<ArrayList<Userprofile>>() {}.getType();
         
            List<Userprofile> userMasters = new Gson().fromJson(jsonStringUserProfile, listType);
            
            JSONObject param = new JSONObject();
            for (Userprofile userProfile : userMasters) {
                param.put("standard", userProfile.getStd());
                param.put("division", userProfile.getDiv());
             }
        //fetch dashboard of the user
        // DashboardResource dashboard = resourceContext.getResource(DashboardResource.class);
        // dashboard.getStudentDashboard(param);
    }

    private void logoutFromShiro(Subject currentUser) {
        currentUser.logout();
    }
}
