package com.kronets.SocialNetwork.rest;

import com.kronets.SocialNetwork.logic.*;
import com.kronets.SocialNetwork.logic.lists.InterestList;
import com.kronets.SocialNetwork.logic.lists.PostsList;
import com.kronets.SocialNetwork.logic.lists.UserGroupsList;
import com.kronets.SocialNetwork.logic.lists.UserGroupsListByName;
import com.kronets.SocialNetwork.models.User;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.InputStream;

/**
 * @author Volodymyr Grynda
 */


@Path("gitpage")
public class GitPageController {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public InputStream getPage(@Context ServletContext context) {
        return context.getResourceAsStream("/WEB-INF/pages/gitpage.html");
    }

}
