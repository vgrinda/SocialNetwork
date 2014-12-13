package com.kronets.SocialNetwork.logic;

import com.kronets.SocialNetwork.dao.GroupDao;
import com.kronets.SocialNetwork.dao.GroupPostDao;
import com.kronets.SocialNetwork.dao.UserDao;
import com.kronets.SocialNetwork.dao.impl.GroupDaoImpl;
import com.kronets.SocialNetwork.dao.impl.GroupPostDaoImpl;
import com.kronets.SocialNetwork.dao.impl.UserDaoImpl;
import com.kronets.SocialNetwork.models.Group;
import com.kronets.SocialNetwork.models.GroupPost;
import com.kronets.SocialNetwork.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Volodymyr Grynda
 */
public class GroupLogic {
    private GroupDao groupDao;
    private GroupPostDao groupPostDao;

    private static final Logger LOGGER =
            LogManager.getLogger(GroupLogic.class.getName());

    public GroupLogic() {
        groupDao = new GroupDaoImpl();
        groupPostDao = new GroupPostDaoImpl();
    }

    public Group getGroup(long id) {
        try {
            return groupDao.selectById(id);
        }
        catch (NullPointerException e){
            return null;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;

        }
    }

    public long members(long groupId) {
        try {
            return groupDao.selectCount(groupId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    public boolean createPost(long id, long ts, String msg) {
        try {
            Group group = getGroup(id);
            if (group == null) {
                return false;
            }

            User user = new UserDaoImpl().selectById(ts);

            groupPostDao.insert(new GroupPost(group, user, msg, new Timestamp(
                    new java.util.Date().getTime())));

            return true;
        }
        catch (NullPointerException e) {
            return false;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public boolean deletePost(long ts, long post) {
        try {
            GroupPost groupPost = groupPostDao.selectById(post);
            if (groupPost == null) {
                return false;
            }

            if (groupPost.getUser().getId() != ts) {
                return false;
            }

            groupPostDao.delete(groupPost);
            return true;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public boolean follow(long userId, long groupId) {
        try {
            new UserDaoImpl().insertGroup(userId, groupId);
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    public boolean unFollow(long userId, long groupId) {
        try {
            new UserDaoImpl().deleteGroup(userId, groupId);
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    public boolean isFollowing(long userId, long groupId) {
        try {
            return new UserDaoImpl().isGroupFollowing(userId, groupId);

        } catch (NullPointerException e) {
            return false;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }
}
