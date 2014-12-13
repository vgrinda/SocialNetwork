package com.kronets.SocialNetwork.logic.lists;

import com.kronets.SocialNetwork.dao.PostDao;
import com.kronets.SocialNetwork.dao.impl.PostDaoImpl;
import com.kronets.SocialNetwork.dao.impl.UserDaoImpl;
import com.kronets.SocialNetwork.models.Post;
import com.kronets.SocialNetwork.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Collection;
import java.util.List;

/**
 * @author Volodymyr Grynda
 */
public class PostsList {
    private Collection<Post> posts;

    @JsonIgnore
    private boolean resolved;

    @JsonIgnore
    private static final Logger LOGGER =
            LogManager.getLogger(PostsList.class.getName());

    public PostsList(long id, int page) {
        try {
            PostDao postDao = new PostDaoImpl();
            User user = new UserDaoImpl().selectById(id);
            posts = postDao.selectLastWith(user, page);
            resolved = true;
        }
        catch (NullPointerException e) {
            resolved = false;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            resolved = false;
        }
    }

    public boolean isResolved() {
        return resolved;
    }

    public Collection<Post> getPosts() {
        return posts;
    }


}
