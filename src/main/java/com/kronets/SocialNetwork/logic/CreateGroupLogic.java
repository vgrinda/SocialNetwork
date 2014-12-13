package com.kronets.SocialNetwork.logic;

import com.kronets.SocialNetwork.dao.GroupDao;
import com.kronets.SocialNetwork.dao.UserDao;
import com.kronets.SocialNetwork.dao.impl.GroupDaoImpl;
import com.kronets.SocialNetwork.dao.impl.UserDaoImpl;
import com.kronets.SocialNetwork.models.Group;
import com.kronets.SocialNetwork.models.User;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CreateGroupLogic
 *
 * @author Volodymyr Grynda
 */

public class CreateGroupLogic {
    @JsonIgnore
    private Group group = new Group();

    private String response;

    @JsonIgnore
    private static final org.apache.logging.log4j.Logger LOGGER =
            LogManager.getLogger(CreateGroupLogic.class.getName());

    /**
     * Creating group instance when rest is calling logic
     * Initialization proceeds in class constructor which change response of
     * creating group action.
     *
     * @param name - group name;
     * @param description - group description;
     * @param user_id - group authors Id.
     */
     public CreateGroupLogic(String name, String description, long user_id) {

        try {
            UserDao userDao = new UserDaoImpl();
            User user = userDao.selectById(user_id);
            this.group.setName(name);
            this.group.setDescription(description);
            this.group.setAuthorId(user);
            if (Match(name)) {
                boolean isCreated = addGroup(group);
                response = "{\"status\": " + "\"" + isCreated + "\"}";
            }
            else  { response = "{\"status\": "
                               + "\"groupName is incorrect\"}";
            }
        }
        catch (NullPointerException e) {
            response = "{\"status\":\"Could not create an object\"}";
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

    }

    public boolean addGroup(Group group) {
        try {
            GroupDao groupDao = new GroupDaoImpl();
            groupDao.insert(group);
            return true;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public boolean Match (String name) {

        Pattern namePattern = Pattern.compile("([\\w]|[\\s])+");
        Matcher matcher = namePattern.matcher(name);
        return matcher.matches();
    }

    public String getResponse() { return response; }

    public Group getGroup() { return group; }
}