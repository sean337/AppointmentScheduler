package com.seanmlee.c195.appointmentscheduler.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Provides database connection
 */
public abstract class DBConnection {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    //private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone=SERVER"; // LOCAL
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone=UTC"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static final String password = "Passw0rd!"; // Password
    private static Connection connection;  // Connection Interface
    private static PreparedStatement preparedStatement;


    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public static void setPreparedStatement(PreparedStatement preparedStatement) {
        DBConnection.preparedStatement = preparedStatement;
    }

    public static void openConnection() {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }


    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }


    public static Connection getConnection() {
        return connection;
    }


}
