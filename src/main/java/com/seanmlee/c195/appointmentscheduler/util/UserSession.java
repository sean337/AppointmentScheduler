package com.seanmlee.c195.appointmentscheduler.util;

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
