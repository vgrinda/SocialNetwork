package com.kronets.SocialNetwork.rest;

import com.kronets.SocialNetwork.logic.PrivateMessageLogic;
import com.kronets.SocialNetwork.logic.lists.ReceivedMessageList;
import com.kronets.SocialNetwork.logic.lists.SentMessageList;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

/**
 * @author Volodymyr Grynda
 */

@Path("pm")
public class PrivateMessageController {
    @GET
    @Produces(MediaType.TEXT_HTML)
    public InputStream getPage(@Context ServletContext context) {
        return context.getResourceAsStream("/WEB-INF/pages/pm.html");
    }

    @GET
    @Path("received{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public ReceivedMessageList getReceivedMessages(
            @Context HttpServletRequest request,
            @PathParam("page") int page) {
        return new ReceivedMessageList((Long) request.getAttribute("userId"),
                                       page);
    }

    @GET
    @Path("sent{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public SentMessageList getSentMessage(@Context HttpServletRequest request,
                                          @PathParam("page") int page) {
        return new SentMessageList((Long) request.getAttribute("userId"), page);
    }

    @POST
    @Path("sendMessage")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public String sendMessage(@Context HttpServletRequest request,
                              String data)throws JSONException{
        JSONObject json = new JSONObject(data);
        long toUserId = json.getLong("to");
        String msg = json.getString("msg");
        return "{\"result\": " + new PrivateMessageLogic()
                .createPm((Long) request.getAttribute("userId"), toUserId, msg);
    }

    @GET
    @Path("getMessage{msgId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMessage(@Context HttpServletRequest request,
                             @PathParam("msgId") long msgId) {
        return "{\"message\": \"" + new PrivateMessageLogic()
                .readPm((Long) request.getAttribute("userId"), msgId) + "\"}";
    }
}
