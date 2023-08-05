package com.seanmlee.c195.appointmentscheduler.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class handles the logging of each user
 */
public class UserLogger {

    private static final Logger logger = Logger.getLogger("UserLog");

    static {
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("user_log.txt", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.addHandler(fileHandler);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
    }

    public static void stampUserLogin(String userName) throws IOException {
        logger.info(userName + " Signed into the application");

    }
    public static void stampInvalidUser(String userName, String password) throws IOException {
        logger.info("invalid login attempt: " + "UserName: " + userName + " Password: " + password);

    }


}
