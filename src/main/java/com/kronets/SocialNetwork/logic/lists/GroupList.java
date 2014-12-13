package com.kronets.SocialNetwork.logic.lists;

import com.kronets.SocialNetwork.dao.impl.GroupDaoImpl;
import com.kronets.SocialNetwork.dao.impl.UserDaoImpl;
import com.kronets.SocialNetwork.models.Group;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Collection;
import java.util.List;

/**
 * @author Volodymyr Grynda
 */
public class GroupList {
    private Collection<Group> groups;

    @JsonIgnore
    private static final Logger LOGGER =
            LogManager.getLogger(GroupPostList.class.getName());

    public GroupList(long id, int page) {
        try {
            groups = new UserDaoImpl().selectUserGroupsNext(id, page);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public GroupList(String groupName, int page) {
        try {
            groups = new GroupDaoImpl().selectByGroupName(groupName, page);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public Collection<Group> getGroups() {
        return groups;
    }
}
