package com.kronets.SocialNetwork.dao;

import com.kronets.SocialNetwork.models.Administrator;
import com.kronets.SocialNetwork.models.User;

import java.util.List;

/**
 * @author Volodymyr Grynda
 */
public interface AdministratorDao {
    public void insert(Administrator administrator) throws Exception;

    public void delete(Administrator administrator) throws Exception;

    public List<Administrator> selectAll() throws Exception;

    public Administrator selectById(long id) throws Exception;
}