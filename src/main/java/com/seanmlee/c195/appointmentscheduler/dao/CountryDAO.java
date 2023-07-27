package com.seanmlee.c195.appointmentscheduler.dao;

import com.seanmlee.c195.appointmentscheduler.model.Country;
import com.seanmlee.c195.appointmentscheduler.model.Customer;
import com.seanmlee.c195.appointmentscheduler.util.DBConnection;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO {

    public static List<Country> getCountries() throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "SELECT * FROM client_schedule.countries";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Country> countries = new ArrayList<>();
        try {
            while (resultSet.next()) {
                long countryId = resultSet.getLong("Country_ID");
                String countryName = resultSet.getString("Country");
                Timestamp dateCreated = resultSet.getTimestamp("Create_Date");
                Timestamp lastUpdated = resultSet.getTimestamp("Last_Update");
                String createdBy = resultSet.getString("Created_By");
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");

                Country country = new Country(countryId, countryName, dateCreated.toLocalDateTime(),lastUpdated.toLocalDateTime(),createdBy,lastUpdatedBy);
                countries.add(country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBConnection.closeConnection();
        return countries;
    }

    public static String getCountryNameByDivisionName(String divisionName) throws SQLException {
        DBConnection.openConnection();
        String countryName = " ";
        String sqlStatement = "SELECT countries.Country " +
                "FROM countries INNER JOIN " +
                "first_level_divisions ON " +
                "countries.Country_ID = first_level_divisions.Country_ID " +
                "WHERE first_level_divisions.Division = ?;";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setString(1, divisionName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            countryName = resultSet.getString("Country");
        }
        DBConnection.closeConnection();
        return countryName;
    }


}
