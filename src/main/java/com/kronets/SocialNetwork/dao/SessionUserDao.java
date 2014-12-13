package com.kronets.SocialNetwork.dao;

import com.kronets.SocialNetwork.models.SessionUser;

/**
 * @author Volodymyr Grynda
 */
public interface SessionUserDao {

    public void insert(SessionUser sessionUser) throws Exception;

    public SessionUser selectById(long id) throws Exception;

    public SessionUser selectBySession(String session) throws Exception;

    public void delete(SessionUser sessionUser) throws Exception;
}
