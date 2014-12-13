package com.kronets.SocialNetwork.dao;

import com.kronets.SocialNetwork.models.Password;

/**
 * @author Volodymyr Grynda
 */
public interface PasswordDao {
    public Password selectById(long id) throws Exception;
    public void update(Password password) throws Exception;
}
