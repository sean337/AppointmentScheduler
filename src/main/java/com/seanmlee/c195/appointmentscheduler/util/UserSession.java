package com.seanmlee.c195.appointmentscheduler.util;

/**
 * Keeps track of the user currently logged in for the duration of their sign in period
 *
 * @author Sean Lee
 */
public class UserSession {

    private static UserSession instance;
    private final long userId;
    private final String userName;

    public UserSession(long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public static UserSession getInstance(long userId, String userName) {
        if (instance == null) {
            instance = new UserSession(userId, userName);
        }
        return instance;
    }

    public static UserSession getInstance(){
        if(instance == null){
            throw new IllegalStateException("No Instance Initialized");
        }
        return instance;
    }


    public static void resetInstance() {
        instance = null;
    }


    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}
