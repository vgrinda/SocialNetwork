package com.kronets.SocialNetwork.logic.lists;

import com.kronets.SocialNetwork.dao.GroupDao;
import com.kronets.SocialNetwork.dao.GroupPostDao;
import com.kronets.SocialNetwork.dao.impl.GroupDaoImpl;
import com.kronets.SocialNetwork.dao.impl.GroupPostDaoImpl;
import com.kronets.SocialNetwork.models.Group;
import com.kronets.SocialNetwork.models.GroupPost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Collection;
import java.sql.Date;

/**
 * @author Volodymyr Grynda
 */
public class GroupPostList {
    private Collection<GroupPost> groupPosts;

    @JsonIgnore
    private static final Logger LOGGER =
            LogManager.getLogger(GroupPostList.class.getName());

    @JsonIgnore
    private boolean created;

    public GroupPostList(long id, int page) {
        try {
            GroupPostDao groupPostDao = new GroupPostDaoImpl();
            Group group = new GroupDaoImpl().selectById(id);
            groupPosts = groupPostDao.selectLastWith(group, page);
            created = true;
        } catch (NullPointerException e) {
            created = false;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            created = false;
        }
    }

    public Collection<GroupPost> getGroupPosts() {
        return groupPosts;
    }

    public boolean isCreated() { return created; }
}
