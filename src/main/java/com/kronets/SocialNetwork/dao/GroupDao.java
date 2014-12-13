package com.kronets.SocialNetwork.dao;

import com.kronets.SocialNetwork.models.Group;

import java.util.List;

/**
 * @author Volodymyr Grynda
 */
public interface GroupDao {
    public Group selectById(long id) throws Exception;

    public void insert(Group group) throws Exception;

    public void update(Group group) throws Exception;

    public void delete(Group group) throws Exception;

    public List<Group> selectByGroupName(String groupName, int lot)
    throws Exception;

    public long selectCount(long groupId) throws Exception;
}
