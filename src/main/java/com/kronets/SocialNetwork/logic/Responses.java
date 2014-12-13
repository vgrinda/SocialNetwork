package com.kronets.SocialNetwork.logic;

/**
 * @author Volodymyr Grynda
 */
public class Responses {
    public static final String JSON_RESPONSE_TRUE = "{\"status\": true}";
    public static final String JSON_RESPONSE_FALSE = "{\"status\": false}";
    public static final String JSON_RESPONSE_WRONG_LOGIN_PASS ="{\"status\": \"wrongLoginPass\"}";
    public static final String JSON_RESPONSE_WRONG_INVITE_CODE = "{\"status\": \"wrongInviteCode\"}";
    public static final String JSON_RESPONSE_NULL_POINTER =
            "{\"status\":\"NullPointerException. Could not create an Object\"}";
}
