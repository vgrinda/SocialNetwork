package com.kronets.SocialNetwork.rest;

import com.kronets.SocialNetwork.logic.LoginLogic;
import com.kronets.SocialNetwork.logic.RegistrationLogic;
import com.kronets.SocialNetwork.logic.Responses;
import com.kronets.SocialNetwork.logic.SessionLogic;
import com.kronets.SocialNetwork.models.User;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * @author Volodymyr Grynda
 */
@Path("index")
public class IndexController {
    @Context
    private ServletContext context;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public InputStream getPage(@Context HttpServletRequest request) {
        Long userID = (Long) request.getAttribute("userId");

        if (userID == null) {
            return context.getResourceAsStream("/WEB-INF/pages/login.html");
        } else {
            return context.getResourceAsStream("/WEB-INF/pages/user.html");
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @Path("login")
    public String login(@Context HttpServletRequest request,
                        @Context HttpServletResponse response,
//                        @FormParam("login") String login,
//                        @FormParam("pass") String password
                          String data)throws JSONException{
        JSONObject json = new JSONObject(data);
        String login = json.getString("login");
        String password = json.getString("pass");
        LoginLogic log = new LoginLogic();
        User user = log.getUser(login, password);
        Cookie cookie;
        Cookie userIdCookie;

        if (user != null) {
            SessionLogic sessionLogic = new SessionLogic();
            cookie = new Cookie("sessionId", sessionLogic.getNewSession(user));
            cookie.setPath("/");
            response.addCookie(cookie);
            userIdCookie = new Cookie("userId", String.valueOf(user.getId()));
            userIdCookie.setPath("/");
            response.addCookie(userIdCookie);

            return Responses.JSON_RESPONSE_TRUE;
        } else {
            return Responses.JSON_RESPONSE_FALSE;
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @Path("registration")
    public String registration(
//                               @FormParam("surname") String surname,
//                               @FormParam("position") String position,
//                               @FormParam("email") String login,
//                               @FormParam("password") String password,
//                               @FormParam("birthday") long birthday,
//                               @FormParam("invite") String invite
                                 String data)throws JSONException{
        JSONObject json = new JSONObject(data);
        String name = json.getString("name");
        String surname = json.getString("surname");
        String login = json.getString("email");
        String password = json.getString("password");
        String invite = json.getString("invite");
        RegistrationLogic registrationLogic = new RegistrationLogic();

        String result = registrationLogic.register(name, surname, login, password, invite);

        return result;
    }


}
