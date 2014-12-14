package com.kronets.SocialNetwork.rest;


import com.kronets.SocialNetwork.logic.GroupLogic;
import com.kronets.SocialNetwork.models.Group;
import com.kronets.SocialNetwork.logic.CreateGroupLogic;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

/**
 * rest for CreateGroup
 * @author Volodymyr Grynda
 */

//not finished
@Path("newGroup")
public class CreateGroupController {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public String confirmCreating(
                                  @Context HttpServletRequest request,
                                  String data)throws JSONException{
        JSONObject json = new JSONObject(data);
        String name = json.getString("name");
        String description = json.getString("descr");
        long userId = (Long)request.getAttribute("userId");
        CreateGroupLogic createGroup =
                new CreateGroupLogic(name, description, userId);
        new GroupLogic().follow(userId, createGroup.getGroup().getGroupId());
        return createGroup.getResponse();
    }

}
