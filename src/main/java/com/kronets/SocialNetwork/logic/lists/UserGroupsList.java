package com.kronets.SocialNetwork.logic.lists;

import com.kronets.SocialNetwork.dao.GroupDao;
import com.kronets.SocialNetwork.dao.UserDao;
import com.kronets.SocialNetwork.dao.impl.GroupDaoImpl;
import com.kronets.SocialNetwork.dao.impl.UserDaoImpl;
import com.kronets.SocialNetwork.models.Group;
import com.kronets.SocialNetwork.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;

/**
 * @author Volodymyr Grynda
 */
public class UserGroupsList {
    private List<Group> groups;

    @JsonIgnore
    private boolean resolved;

    @JsonIgnore
    private static final Logger LOGGER =
            LogManager.getLogger(UserGroupsList.class.getName());

    public UserGroupsList(long id, int page) {
        UserDao userDao = new UserDaoImpl();
        try {
            groups = userDao.selectUserGroupsNext(id, page);
        }
        catch (NullPointerException e) {
            resolved = false;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            resolved = false;
        }
        resolved = true;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public boolean isResolved() {
        return resolved;
    }
}