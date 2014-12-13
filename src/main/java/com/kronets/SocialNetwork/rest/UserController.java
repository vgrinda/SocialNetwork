package com.kronets.SocialNetwork.rest;

import com.kronets.SocialNetwork.logic.*;
import com.kronets.SocialNetwork.logic.lists.InterestList;
import com.kronets.SocialNetwork.logic.lists.PostsList;
import com.kronets.SocialNetwork.logic.lists.UserGroupsList;
import com.kronets.SocialNetwork.logic.lists.UserGroupsListByName;
import com.kronets.SocialNetwork.models.User;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.print.attribute.standard.Media;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Volodymyr Grynda
 */


@Path("user{id}")
public class UserController {
    @GET
    @Path("interests")
    @Produces(MediaType.APPLICATION_JSON)
    public InterestList getInterests(@PathParam("id") long id) {
        return new InterestList(id);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public InputStream getPage(@Context ServletContext context) {
        return context.getResourceAsStream("/WEB-INF/pages/user.html");
    }

   @GET
   @Path("index")
   @Produces("application/json")
   public Response getData(@PathParam("f") String f) throws JSONException {

       JSONObject jsonObject = new JSONObject();
       String celsius;
       celsius = f + "sgfrs";
       jsonObject.put("F Value", f);
       jsonObject.put("C Value", celsius);


       String result = "@Produces(\"application/json\") Output: \n\nF to C Converter Output: \n\n" + jsonObject;
       return Response.status(200).entity(result).build();
   }

    @GET
    @Path("getUser")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") long id) {
        return new UserLogic().getUser(id);
    }

    @POST
    @Path("createPost")
    @Produces(MediaType.APPLICATION_JSON)
    public String addPost(@Context HttpServletRequest request,
                          @FormParam("msg") String msg) {
        return "{\"status\": " + new UserLogic()
                .createPost((Long) request.getAttribute("userId"), msg) + "}";
    }

    @POST
    @Path("deletePost")
    @Produces(MediaType.APPLICATION_JSON)
    public String deletePost(@Context HttpServletRequest request,
                             @FormParam("postId") long postId) {
        return "{\"status\": " + new UserLogic()
                .deletePost((Long) request.getAttribute("userId"), postId) +
               "}";
    }

    @GET
    @Path("posts{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public PostsList getPosts(@PathParam("id") long id,
                              @PathParam("page") int page) {
        return new PostsList(id, page);

    }

    @GET
    @Path("groups{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserGroupsList getGroups(@PathParam("id") long id,
                                    @PathParam("page") int page) {
        return new UserGroupsList(id, page);
    }

    @GET
    @Path("getGroupByName{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserGroupsListByName getGroups(@PathParam("id") long id,
                                          @PathParam("page") int page,
                                          @PathParam("name") String name) {
        return new UserGroupsListByName(id, page, name);
    }

    @POST
    @Path("edit")
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public String editUser(@Context HttpServletRequest request,
//                           @FormParam("interests") String interests,
//                           @FormParam("name") String name,
//                           @FormParam("surname") String surname,
//                           @FormParam("position") String position,
//                           @FormParam("year") String year,
//                           @FormParam("date") String day,
//                           @FormParam("month") String month
                             String data) throws JSONException {

        JSONObject json = new JSONObject(data);
        long userId = (Long) request.getAttribute("userId");
        EditUserProfileLogic editUserProfile = new EditUserProfileLogic();
        String name = json.getString("name");
        String interests = json.getString("interests");
        String surname = json.getString("surname");
        String position = json.getString("position");
        String year = json.getString("year");
        String day = json.getString("date");
        String month = json.getString("month");
        if (editUserProfile
                .edit(userId, name, surname, position, interests, day, month,
                      year)) {
            return Responses.JSON_RESPONSE_TRUE;
        } else {
            return Responses.JSON_RESPONSE_FALSE;
        }
    }


    @GET
    @Path("getAvatar")
    @Produces({"image/png", "image/jpeg"})
    public Response getAvatar(@Context ServletContext context,
                              @PathParam("id") long id) {


        UserAvatarLogic userAvatarLogic = new UserAvatarLogic();
        File file =
                userAvatarLogic.getAvatar(context.getRealPath("/WEB-INF"), id);
        return Response.ok(file).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("add")
    public String addFollowing(@Context HttpServletRequest request,
                               @PathParam("id") long id) {
        return "{\"status\": " + new FollowingLogic()
                .addFollowing((Long) request.getAttribute("userId"),
                              id) + "}";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("delete")
    public String deleteFollowing(@Context HttpServletRequest request,
                                  @PathParam("id") long id) {
        return "{\"status\": " + new FollowingLogic()
                .deleteFollowing((Long) request.getAttribute("userId"),
                                 id) + "}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("isFollowing")
    public String isFollowing(@Context HttpServletRequest request,
                              @PathParam("id") long id) {
        return "{\"isFollowing\": " + new FollowingLogic()
                .isFollowing((Long) request.getAttribute("userId"), id) +
               "}";
    }

    @GET
    @Path("exit")
    @Produces(MediaType.APPLICATION_JSON)
    public String exit(@Context HttpServletResponse response) {
        Cookie cookie = new Cookie("sessionId", "0");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        Cookie userIdCookie = new Cookie("userId", "0");
        userIdCookie.setPath("/");
        userIdCookie.setMaxAge(0);
        response.addCookie(userIdCookie);
        return Responses.JSON_RESPONSE_TRUE;
    }
}
