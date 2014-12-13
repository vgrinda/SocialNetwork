package com.kronets.SocialNetwork.logic.lists;

import com.kronets.SocialNetwork.dao.impl.BackOfficeAdminDaoImpl;
import com.kronets.SocialNetwork.dao.impl.PostDaoImpl;
import com.kronets.SocialNetwork.models.BackOfficeAdmin;
import com.kronets.SocialNetwork.models.Post;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;


import java.util.Collection;
import java.util.List;

/**
 * NewsList logic
 *
 * @author Volodymyr Grynda
 */
public class NewsList {
    @JsonIgnore
    private int newsListNumber;

    @JsonIgnore
    private boolean resolved;

    @JsonIgnore
    private PostDaoImpl postDao;

    private List<Post> posts;

    @JsonIgnore
    private static final Logger LOGGER =
            LogManager.getLogger(NewsList.class.getName());

    public NewsList(int newsNumber) {
        this.newsListNumber = newsNumber;
        try {
            BackOfficeAdminDaoImpl backOfficeAdminDao =
                    new BackOfficeAdminDaoImpl();
            postDao = new PostDaoImpl();
            posts = postDao.selectLastBeckOffWith(
                    backOfficeAdminDao.selectAll(),
                    newsListNumber);
            resolved = true;
        } catch (NullPointerException e) {
            resolved = false;
            posts = null;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            resolved = false;
        }

    }

    public boolean isResolved() {
        return resolved;
    }

    public List<Post> getPosts() {
        return posts;
    }
}