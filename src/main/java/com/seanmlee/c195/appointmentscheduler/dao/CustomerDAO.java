package com.seanmlee.c195.appointmentscheduler.dao;

import com.seanmlee.c195.appointmentscheduler.model.Customer;
import com.seanmlee.c195.appointmentscheduler.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public static List<Customer> getCustomers() throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "SELECT * FROM client_schedule.customers";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Customer> customers = new ArrayList<>();
        try {
            while (resultSet.next()) {
                long customerId = resultSet.getLong("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                String address = resultSet.getString("Address");
                String postalCode = resultSet.getString("Postal_Code");
                String phoneNumber = resultSet.getString("Phone");
                Timestamp createDate = resultSet.getTimestamp("Create_Date");
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                long divisionId = resultSet.getLong("Division_ID");

                Customer customer = new Customer(customerId, customerName, address, postalCode, phoneNumber, createDate.toLocalDateTime(),
                        lastUpdate.toLocalDateTime(), createdBy, lastUpdatedBy, divisionId);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBConnection.closeConnection();
        return customers;
    }


    public static int deleteCustomer(long customerId) throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "DELETE FROM client_schedule.customers WHERE Customer_ID=?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setLong(1, customerId);
        int result = preparedStatement.executeUpdate();
        DBConnection.closeConnection();
        return result;
    }

    public static void createCustomer(Customer customer) throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "INSERT INTO client_schedule.customers" +
                "(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)" +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setString(1, customer.getName());
        preparedStatement.setString(2, customer.getAddress());
        preparedStatement.setString(3, customer.getPostalCode());
        preparedStatement.setString(4, customer.getPhoneNumber());
        preparedStatement.setTimestamp(5, (Timestamp.valueOf(customer.getCreateDate())));
        preparedStatement.setString(6, customer.getCreatedBy());
        preparedStatement.setTimestamp(7, (Timestamp.valueOf(customer.getLastUpdate())));
        preparedStatement.setString(8, customer.getLastUpdatedBy());
        preparedStatement.setLong(9, customer.getDivisionId());
        preparedStatement.executeUpdate();
        DBConnection.closeConnection();
    }

    public static void updateCustomer(Customer customer) throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "UPDATE client_schedule.customers " +
                "set Customer_Name = ?, " +
                "Address = ?, " +
                "Postal_Code = ?, " +
                "Phone = ?, " +
                "Create_Date = ?, " +
                "Created_By = ?," +
                "Last_Update = ?, " +
                "Last_Updated_By = ?, " +
                "Division_ID = ? " +
                "WHERE Customer_ID = ?;";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setString(1, customer.getName());
        preparedStatement.setString(2, customer.getAddress());
        preparedStatement.setString(3, customer.getPostalCode());
        preparedStatement.setString(4, customer.getPhoneNumber());
        preparedStatement.setTimestamp(5, (Timestamp.valueOf(customer.getCreateDate())));
        preparedStatement.setString(6, customer.getCreatedBy());
        preparedStatement.setTimestamp(7, (Timestamp.valueOf(customer.getLastUpdate())));
        preparedStatement.setString(8, customer.getLastUpdatedBy());
        preparedStatement.setLong(9, customer.getDivisionId());
        preparedStatement.setLong(10, customer.getId());
        preparedStatement.executeUpdate();
    }

    public static Customer findCustomer(long customerId) throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "SELECT * FROM client_schedule.customers WHERE Customer_ID = ?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setLong(1, customerId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Customer> customers = new ArrayList<>();
        Customer customer = null;
        if (resultSet.next()) {
            long id = resultSet.getLong("Customer_ID");
            String customerName = resultSet.getString("Customer_Name");
            String address = resultSet.getString("Address");
            String postalCode = resultSet.getString("Postal_Code");
            String phoneNumber = resultSet.getString("Phone");
            Timestamp createDate = resultSet.getTimestamp("Create_Date");
            String createdBy = resultSet.getString("Created_By");
            Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");
            long divisionId = resultSet.getLong("Division_ID");
            customer = new Customer(id, customerName, address, postalCode, phoneNumber, createDate.toLocalDateTime(),
                    lastUpdate.toLocalDateTime(), createdBy, lastUpdatedBy, divisionId);
        }
        DBConnection.closeConnection();
        return customer;
    }


    }
