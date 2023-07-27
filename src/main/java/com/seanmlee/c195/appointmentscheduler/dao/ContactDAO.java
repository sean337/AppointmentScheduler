package com.seanmlee.c195.appointmentscheduler.dao;

import com.seanmlee.c195.appointmentscheduler.model.Contact;
import com.seanmlee.c195.appointmentscheduler.util.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {

    public static List<String> getContactNames() throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "SELECT Contact_Name FROM client_schedule.contacts";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> contactNames = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                String contact = resultSet.getString("Contact_Name");
                contactNames.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBConnection.closeConnection();
        return contactNames;
    }

    public static long getContactIdByName(String contactName) throws SQLException {
        long contact = -1;
        DBConnection.openConnection();
        String sqlStatement = "SELECT Contact_ID FROM client_schedule.contacts WHERE Contact_Name = ?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setString(1, contactName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            contact = resultSet.getLong("Contact_ID");
            }
        DBConnection.closeConnection();
        return contact;
    }

    public static List<Contact> getContacts() throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "SELECT * FROM client_schedule.contacts";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Contact> contacts = new ArrayList<>();
        try {
            while (resultSet.next()) {
                long contactId = resultSet.getLong("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");
                String contactEmail = resultSet.getString("Email");

                Contact contact = new Contact(contactId,contactName,contactEmail);
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBConnection.closeConnection();
        return contacts;
    }



}
