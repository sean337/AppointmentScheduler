package com.seanmlee.c195.appointmentscheduler.dao;

import com.seanmlee.c195.appointmentscheduler.model.User;
import com.seanmlee.c195.appointmentscheduler.util.DBConnection;
import com.seanmlee.c195.appointmentscheduler.util.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides access to users in the database
 */
public class UserDAO {

    public UserDAO(Long id, String userName, String password) {

    }

    public UserDAO() {

    }

    /**
     * Validates the user
     *
     * @param userNameInput
     * @param passwordInput
     * @return
     * @throws SQLException
     */
    public static User validateUser(String userNameInput, String passwordInput) throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "SELECT * FROM client_schedule.users WHERE user_name = ? AND password = ?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setString(1, userNameInput);
        preparedStatement.setString(2, passwordInput);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            System.out.println("Not Found");
            DBConnection.closeConnection();
            return null;
        } else {
            long id = resultSet.getLong("User_ID");
            String userName = resultSet.getString("User_Name");
            String password = resultSet.getString("Password");
            User newUser = new User(id, userName, password);
            DBConnection.closeConnection();
            return newUser;
        }
    }

    /**
     * Gets a list of all the users
     *
     * @return
     * @throws SQLException
     */
    public static List<User> getUsers() throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "SELECT * FROM client_schedule.users";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> users = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Long userId = resultSet.getLong("User_ID");
                String userName = resultSet.getString("User_Name");
                String password = resultSet.getString("Password");
                User user = new User(userId, userName, password);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBConnection.closeConnection();
        return users;
    }

    public static ObservableList<Long> getUserIds() throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "SELECT User_ID FROM client_schedule.users";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Long> userIds = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                long userId = resultSet.getLong("User_Id");
                userIds.add(userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBConnection.closeConnection();
        return userIds;
    }

    public static String getLoggedInUserName() throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "SELECT User_Name FROM client_schedule.users WHERE User_Id = ?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setLong(1, UserSession.getInstance().getUserId());
        ResultSet resultSet = preparedStatement.executeQuery();
        String userName = " ";
        try {
            if (resultSet.next()) {
                userName = resultSet.getString("User_Name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userName;
    }


}
