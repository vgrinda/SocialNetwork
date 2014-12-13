package com.kronets.SocialNetwork.logic.lists;

import com.kronets.SocialNetwork.dao.UserDao;
import com.kronets.SocialNetwork.dao.impl.UserDaoImpl;
import com.kronets.SocialNetwork.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;

/**
 * @author Volodymyr Grynda
 */
public class FollowingUsersList {
    private List<User> followingUsers;

    @JsonIgnore
    private static final Logger LOGGER =
            LogManager.getLogger(FollowingUsersList.class.getName());

    public FollowingUsersList(long id, int page) {
        UserDao userDao = new UserDaoImpl();
        try {
            followingUsers = userDao.selectFollowingsNext(id, page);
        }
//        catch (NullPointerException e) {
//            followingUsers = null;
//        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public FollowingUsersList(String fullName, long id, int beginId) {
        String[] arr = fullName.split(" ");
        String name = arr[0];
        String surname = arr[1];
        UserDao userDao = new UserDaoImpl();
        try {
            followingUsers =
                    userDao.selectFollowingsByFullName(name, surname, id,
                                                       beginId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public List<User> getFollowingUsers() {
        return followingUsers;
    }
}
