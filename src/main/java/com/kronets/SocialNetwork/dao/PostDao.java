package com.kronets.SocialNetwork.dao;

import com.kronets.SocialNetwork.models.BackOfficeAdmin;
import com.kronets.SocialNetwork.models.Post;
import com.kronets.SocialNetwork.models.User;

import java.util.List;

/**
 * Posts Dao
 *
 * @author Volodymyr Grynda
 */
public interface PostDao {
    public Post selectById(long userId) throws Exception;
    public void insert(Post post) throws Exception;
    public void delete(Post post) throws Exception;
    public List selectLastWith(User user, int lot) throws Exception;
    public List<Post> selectLastBeckOffWith (List<BackOfficeAdmin> backOfficeAdmins,
                                             int lot) throws Exception;
}
