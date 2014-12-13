package com.kronets.SocialNetwork.dao;

import com.kronets.SocialNetwork.models.Administrator;
import com.kronets.SocialNetwork.models.BackOfficeAdmin;

import java.util.List;

/**
 * @author Volodymyr Grynda
 */
public interface BackOfficeAdminDao {
    public void insert(BackOfficeAdmin backOfficeAdmin) throws Exception;

    public void delete(BackOfficeAdmin backOfficeAdmin) throws Exception;

    public List<BackOfficeAdmin> selectAll() throws Exception;

    public BackOfficeAdmin selectById(long id) throws Exception;
}
