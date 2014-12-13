package com.kronets.SocialNetwork.rest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.Collection;

import com.kronets.SocialNetwork.logic.lists.NewsList;
import com.kronets.SocialNetwork.models.Post;

/**
 * rest class for News
 *
 *
 * @author Volodymyr Grynda
 */

@Path("news")
public class NewsController {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public InputStream getNewsPage(@Context ServletContext context) {
            return context.getResourceAsStream("/WEB-INF/pages/news.html");
    }

    /**
     *
     * @param begin set beginIndex from which news list begin
     *
     * @return NewsList List with 10 last Posts
     */

    @Path("news{begins}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public NewsList getNextNews(@PathParam("begins") int begin) {
        NewsList newsList = new NewsList(begin);
        return newsList;
    }
}
