package com.kronets.SocialNetwork.dao;

import com.kronets.SocialNetwork.models.PrivateMessage;
import com.kronets.SocialNetwork.models.User;

import java.util.List;

/**
 * Private messages Dao
 *
 * @author Volodymyr Grynda
 */
public interface PrivateMessageDao {
    public PrivateMessage selectById(long msgId) throws Exception;

    public void update(PrivateMessage privateMessage) throws Exception;

    public void insert(PrivateMessage privateMessage) throws Exception;

    public void delete(PrivateMessage privateMessage) throws Exception;

    public List<PrivateMessage> selectReceivedNextWith(User user, int lot)
    throws Exception;

    public List<PrivateMessage> selectSentNextWith(User user, int lot)
    throws Exception;
}
