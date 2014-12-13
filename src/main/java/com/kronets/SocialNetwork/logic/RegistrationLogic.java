package com.kronets.SocialNetwork.logic;

import com.kronets.SocialNetwork.dao.InviteDao;
import com.kronets.SocialNetwork.dao.UserDao;
import com.kronets.SocialNetwork.dao.impl.InviteDaoImpl;
import com.kronets.SocialNetwork.dao.impl.UserDaoImpl;
import com.kronets.SocialNetwork.models.Invite;
import com.kronets.SocialNetwork.models.Password;
import com.kronets.SocialNetwork.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Volodymyr Grynda
 */
public class RegistrationLogic {

    private final String NO_AVATAR_PATH = "/avatar/noavatar.png";
    private final String DEFAULT_POSITION = "employee";

    private static final Logger LOGGER =
            LogManager.getLogger(RegistrationLogic.class.getName());

    public String register(String name, String surname, String login,
                           String password, String invite){
            String names = new String(name);
            String surnames =new String(surname);
            String logins = new String(login);
            String passwords = new String(password);
            User loginAlreadyUsed = null;
            UserDao userDao = new UserDaoImpl();
            try {
                loginAlreadyUsed = userDao.selectByLogin(login);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(loginAlreadyUsed != null)
            {
                return  Responses.JSON_RESPONSE_LOGINISALREADYUSED;
            }
            try {
                if (!checkInviteCode(invite)) {
                    return Responses.JSON_RESPONSE_WRONG_INVITE_CODE;
                }
                if (!match(login, password, name, surname, invite) && names.length() > 2 && names.length() < 20 && surnames.length() > 2 &&
                        surnames.length() < 20 && logins.length() > 2 && logins.length() < 20 && passwords.length() > 3
                        && passwords.length() < 17) {
                    return Responses.JSON_RESPONSE_WRONG_DATA;
                }
                if (addUser(name, surname, login, password)) {
                    InviteDao inviteDao = new InviteDaoImpl();
                    deleteInvite(invite);
                    return Responses.JSON_RESPONSE_TRUE;
                } else {
                    return Responses.JSON_RESPONSE_FALSE;
                }
            }
            catch (NullPointerException e) {
                return Responses.JSON_RESPONSE_FALSE;
            }
    }


    private boolean addUser(String name, String surname, String login,
                            String password) {
        UserDao userDao = new UserDaoImpl();
        User alreadyUsedLogin = null;
        String salt = "skjngjefwekfpojnzcknlemfsopena87jwea2lm;alnd5lnl441fke123";

        try {
            alreadyUsedLogin = userDao.selectByLogin(login);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (alreadyUsedLogin == null) {
            User user = new User();
            user.setLogin(login);
            user.setName(name);
            user.setSurname(surname);
            user.setPosition(DEFAULT_POSITION);
            user.setBirthday(new Date(0));
            user.setPathToAvatar(NO_AVATAR_PATH);

            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(password.getBytes(), 0, password.length());
                String sha1 = new BigInteger(1, md.digest()).toString(16);
                String sha2 = sha1 + salt;
                MessageDigest md2 = MessageDigest.getInstance("SHA-1");
                md2.update(sha2.getBytes(), 0, sha2.length());
                String sha3 = new BigInteger(1, md2.digest()).toString(16);
                userDao.insert(user, new Password(sha3));
                return true;

            }
            catch (NullPointerException e) {
                return false;
            }
            catch (Exception e) {
                LOGGER.error(e.getMessage());
                return false;
            }

        }
        return false;


    }

    public boolean checkInviteCode(String invite) {
        InviteDao inviteDao = new InviteDaoImpl();
        try {
            Invite currentInvite = inviteDao.selectByInvite(invite);
            if (currentInvite != null) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }

    }

    private void deleteInvite(String invite) {
        InviteDao inviteDao = new InviteDaoImpl();
        try {
            Invite currentInvite = inviteDao.selectByInvite(invite);
            inviteDao.delete(currentInvite);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private boolean match(String login, String password, String name, String surname, String invite) {

        //matching login & password
        Pattern loginPattern = Pattern.
                compile("^([a-z0-9_\\.-]{1,20})@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})$");
        Matcher loginMatcher = loginPattern.matcher(login);
        // At least 4 latin symbols or digits and _ -
        Pattern passwordPattern =
                Pattern.compile("^[a-zA-Z0-9_-]{4,16}$");

        Pattern namePattern = Pattern.compile("^[a-zA-Z0-9_-]{2,20}$");
        Pattern surnamePattern = Pattern.compile("^[a-zA-Z0-9_-]{2,20}$");
        Pattern invitePattern = Pattern.compile("^[a-zA-Z0-9_-]{2,20}$");

        Matcher nameMatcher = namePattern.matcher(name);
        Matcher surnameMatcher = surnamePattern.matcher(surname);
        Matcher inviteMatcher = invitePattern.matcher(invite);
        //At least one upper case, one digit and consist of 4-10 symbols
        // ("^.*(?=.{4,10})(?=.*\\d)(?=.*[a-zA-Z]).*$");

        Matcher passwordMatcher = passwordPattern.matcher(password);

        if (loginMatcher.matches() && passwordMatcher.matches() && surnameMatcher.matches() && nameMatcher.matches()
                && inviteMatcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
