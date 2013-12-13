/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.DAO;

import com.quick.tim.mobileserviceprovider.bean.EventCommentsBean;
import com.quick.tim.mobileserviceprovider.bean.EventLikeBean;
import com.quick.tim.mobileserviceprovider.bean.ForumEventDetailsBean;
import com.quick.tim.mobileserviceprovider.entity.*;



import java.util.List;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author suyogn
 */
public interface ForumDao {
    public List<ForumEventDetailsBean> getForumEventDetails(JSONObject inputRequest) throws JSONException;
    public List<EventLikeBean> getEventLikesById(JSONObject inputRequest) throws JSONException;
    public List<EventCommentsBean> getEventCommentsById(JSONObject inputRequest) throws JSONException;
    //public List<ForumEventDetails> getForumEventDetails(JSONObject inputRequest);
    public List<ForumEventDetailsBean> getForumEventById(JSONObject inputRequest) throws JSONException;

    public void saveEventDetails(ForumEventDetails event);
    public void saveEventLike(ForumEventLikes eventLike);
    public void saveEventComment(ForumEventComments eventComment);
}
