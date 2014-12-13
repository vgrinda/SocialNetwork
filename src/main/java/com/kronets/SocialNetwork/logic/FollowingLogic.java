package com.kronets.SocialNetwork.logic;

import com.kronets.SocialNetwork.dao.UserDao;
import com.kronets.SocialNetwork.dao.impl.UserDaoImpl;
import com.kronets.SocialNetwork.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Volodymyr Grynda
 */
public class FollowingLogic {
    private UserDao userDao;

    private static final Logger LOGGER =
            LogManager.getLogger(FollowingLogic.class.getName());

    public FollowingLogic() {
        userDao = new UserDaoImpl();
    }

    public boolean addFollowing(long userId, long followingId) {
        try {
            User user = userDao.selectById(userId);
            User hisFollowing = userDao.selectById(followingId);
            userDao.insertFollowing(user, hisFollowing);
            return true;
        }
        catch (NullPointerException e ){
            return false;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    public boolean deleteFollowing(long userId, long followingId) {
        try {
            User user = userDao.selectById(userId);
            User hisFollowing = userDao.selectById(followingId);
            userDao.deleteFollowing(user, hisFollowing);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    public boolean isFollowing(long userId1, long userId2) {
        try {
            return userDao.isFollowing(userId1, userId2);

        } catch (NullPointerException e) {
            return false;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }
}
