package com.kronets.SocialNetwork.logic;

import com.kronets.SocialNetwork.dao.impl.UserDaoImpl;
import com.kronets.SocialNetwork.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author Volodymyr Grynda
 */
public class LoginLogic {
    private UserDaoImpl userDaoImpl = new UserDaoImpl();
    private User user;

    private static final Logger LOGGER =
            LogManager.getLogger(LoginLogic.class.getName());

    public User getUser(String login, String password) {

        try {
            user = userDaoImpl.selectByLogin(login);

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes(), 0, password.length());
            String md5 = new BigInteger(1, md.digest()).toString(16);

            if (user != null && md5.equals(user.getPassword().getPassword())) {
                return user;
            } else {
                return null;
            }

        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return null;

    }
}