package com.kronets.SocialNetwork.dao;

import com.kronets.SocialNetwork.models.Group;
import com.kronets.SocialNetwork.models.GroupPost;

import java.util.List;

/**
 * @author Volodymyr Grynda
 */
public interface GroupPostDao {
    public GroupPost selectById(long id) throws Exception;
    public void insert(GroupPost group) throws Exception;
    public void update(GroupPost group) throws Exception;
    public void delete(GroupPost group) throws Exception;
    public List selectLastWith(Group group, int lot) throws Exception;
}
