package com.kronets.SocialNetwork.logic;

/**
 * @author Volodymyr Grynda
 */
public class Responses {
    public static final String JSON_RESPONSE_TRUE = "{\"status\": true}";
    public static final String JSON_RESPONSE_FALSE = "{\"status\": false}";
    public static final String JSON_RESPONSE_WRONG_LOGIN_PASS ="{\"status\": \"wrong_Login_or_Pass\"}";
    public static final String JSON_RESPONSE_WRONG_INVITE_CODE = "{\"status\": \"wrong_Invite_Code\"}";
    public static final String JSON_RESPONSE_NULL_POINTER =
            "{\"status\":\"NullPointerException. Could not create an Object\"}";
    public static final String JSON_RESPONSE_WRONG_DATA = "{\"status\": \"wrong_data\"}";
    public static final String JSON_RESPONSE_LOGINISALREADYUSED = "{\"status: \"login_is_already_used\"}";
}
