package com.kronets.SocialNetwork.logic;

import com.kronets.SocialNetwork.dao.PrivateMessageDao;
import com.kronets.SocialNetwork.dao.impl.PrivateMessageDaoImpl;
import com.kronets.SocialNetwork.dao.impl.UserDaoImpl;
import com.kronets.SocialNetwork.models.PrivateMessage;
import com.kronets.SocialNetwork.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Volodymyr Grynda
 */
public class PrivateMessageLogic {
    private static final Logger LOGGER =
            LogManager.getLogger(PrivateMessageLogic.class.getName());

    public boolean createPm(long id, long toUserId, String msg) {
        try {
            PrivateMessageDao privateMessageDao = new PrivateMessageDaoImpl();
            User user = new UserDaoImpl().selectById(id);
            User toUser = new UserDaoImpl().selectById(toUserId);
            PrivateMessage pm = new PrivateMessage(user, toUser, msg,
                                                     new Timestamp(new Date()
                                                                           .getTime()));
            privateMessageDao.insert(pm);
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

    public String readPm(long userId, long msgId) {
        try {
            User user = new UserDaoImpl().selectById(userId);
            PrivateMessageDao privateMessageDao = new PrivateMessageDaoImpl();
            PrivateMessage pm = privateMessageDao.selectById(msgId);
            if (pm.getReceiverUser().getId() == userId) {
                pm.setRead(true);
                privateMessageDao.update(pm);
                return pm.getMessage();
            } else if (pm.getSentUser().getId() == userId) {
                return pm.getMessage();
            }

            return "";
        }
        catch (NullPointerException e) {
            return "false}";
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "";
        }
    }
}
