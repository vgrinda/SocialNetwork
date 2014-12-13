package com.kronets.SocialNetwork.logic;

import com.kronets.SocialNetwork.dao.impl.UserDaoImpl;
import com.kronets.SocialNetwork.models.Password;
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
        String salt = "skjngjefwekfpojnzcknlemfsopena87jwea2lm;alnd5lnl441fke123";
        try {
            user = userDaoImpl.selectByLogin(login);

            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(password.getBytes(), 0, password.length());
            String sha1 = new BigInteger(1, md.digest()).toString(16);
            String sha2 = sha1 + salt;
            MessageDigest md2 = MessageDigest.getInstance("SHA-1");
            md2.update(sha2.getBytes(), 0, sha2.length());
            String sha3 = new BigInteger(1, md2.digest()).toString(16);

            if (user != null && sha3.equals(user.getPassword().getPassword())) {
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