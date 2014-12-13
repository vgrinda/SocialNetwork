package com.kronets.SocialNetwork.logic;

import com.kronets.SocialNetwork.dao.PostDao;
import com.kronets.SocialNetwork.dao.UserDao;
import com.kronets.SocialNetwork.dao.impl.PostDaoImpl;
import com.kronets.SocialNetwork.dao.impl.UserDaoImpl;
import com.kronets.SocialNetwork.models.Post;
import com.kronets.SocialNetwork.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.Date;


/**
 * @author Volodymyr Grynda
 */
public class UserLogic {
    private static final Logger LOGGER =
            LogManager.getLogger(UserLogic.class.getName());

    public User getUser(long id) {
        User user = null;
        try {
            user = new UserDaoImpl().selectById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean createPost(long id, String msg) {
        try {
            PostDao postDao = new PostDaoImpl();
            Post post = new Post(msg, new UserDaoImpl().selectById(id),
                    new Timestamp(new Date().getTime()));
            postDao.insert(post);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deletePost(long userId, long postId) {
        try {
            PostDao postDao = new PostDaoImpl();
            Post post = postDao.selectById(postId);
            if (post.getUser().getId() == userId) {
                postDao.delete(post);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean subscribeOnUser(long userId, long followingId) {
        try {
            UserDao userDao = new UserDaoImpl();
            User user = userDao.selectById(userId);
            User following = userDao.selectById(followingId);
            userDao.insertFollowing(user, following);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean unsubscribeUser(long userId, long followingId) {
        try {
            UserDao userDao = new UserDaoImpl();
            User user = userDao.selectById(userId);
            User following = userDao.selectById(followingId);
            userDao.deleteFollowing(user, following);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
