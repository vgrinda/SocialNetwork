package com.kronets.SocialNetwork.logic.lists;

import com.kronets.SocialNetwork.dao.PrivateMessageDao;
import com.kronets.SocialNetwork.dao.impl.PrivateMessageDaoImpl;
import com.kronets.SocialNetwork.dao.impl.UserDaoImpl;
import com.kronets.SocialNetwork.models.PrivateMessage;
import com.kronets.SocialNetwork.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Collection;

/**
 * @author Volodymyr Grynda
 */
public class SentMessageList {
    private Collection<PrivateMessage> receivedMessages;

    @JsonIgnore
    private boolean resolved;

    @JsonIgnore
    private static final Logger LOGGER = LogManager.getLogger(SentMessageList.class.getName());

    public SentMessageList(long id, int page) {
        PrivateMessageDao privateMessageDao = new PrivateMessageDaoImpl();
        try {
            User user = new UserDaoImpl().selectById(id);
            receivedMessages =
                    privateMessageDao.selectSentNextWith(user, page);
            resolved =true;
        }
        catch (NullPointerException e) {
            resolved = false;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            resolved = false;
        }
    }

    public Collection<PrivateMessage> getPrivateMessages() {
        return receivedMessages;
    }

    public boolean isResolved() {
        return resolved;
    }
}


