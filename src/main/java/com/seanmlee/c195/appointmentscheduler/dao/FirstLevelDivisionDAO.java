package com.seanmlee.c195.appointmentscheduler.dao;

import com.seanmlee.c195.appointmentscheduler.model.FirstLevelDivision;
import com.seanmlee.c195.appointmentscheduler.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FirstLevelDivisionDAO {

    //this will return states WHERE the country ID matches the country ID selected in the combo box

    public static List<FirstLevelDivision> getFirstLevelDivision() throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "SELECT * FROM client_schedule.first_level_divisions";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<FirstLevelDivision> firstLevelDivisions = new ArrayList<>();
        try {
            while (resultSet.next()) {
                long id = resultSet.getLong("Division_ID");
                String divisionName = resultSet.getString("Division");
                Timestamp createDate = resultSet.getTimestamp("Create_Date");
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                long countryId = resultSet.getLong("countryId");
                FirstLevelDivision firstLevelDivision = new FirstLevelDivision(id, divisionName,
                        createDate.toLocalDateTime(), lastUpdate.toLocalDateTime(), createdBy, lastUpdatedBy, countryId);
                firstLevelDivisions.add(firstLevelDivision);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBConnection.closeConnection();
        return firstLevelDivisions;
    }

    public static long getDivisionIdByName(String divisionName) throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "SELECT Division_ID FROM client_schedule.first_level_divisions WHERE Division = ?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setString(1,divisionName);
        ResultSet resultSet = preparedStatement.executeQuery();
        long divisionId = -1;
        if(resultSet.next()) {
            divisionId = resultSet.getLong("Division_ID");
        }
        DBConnection.closeConnection();
        return divisionId;
    }

    public static String getDivisionNameById(long divisionId) throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "SELECT Division FROM client_schedule.first_level_divisions  WHERE Division_ID = ?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setLong(1, divisionId);
        ResultSet resultSet = preparedStatement.executeQuery();
        String divisionName = " ";
        if(resultSet.next()) {
            divisionName = resultSet.getString("Division");
        }
        DBConnection.closeConnection();
        return divisionName;
    }

    public static List<FirstLevelDivision> sortedDivisions(long id) throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "SELECT * FROM client_schedule.first_level_divisions WHERE Country_ID = ?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<FirstLevelDivision> sortedDivisions = new ArrayList<>();
        try {
            while (resultSet.next()) {
                long divisionId = resultSet.getLong("Division_ID");
                String divisionName = resultSet.getString("Division");
                Timestamp createDate = resultSet.getTimestamp("Create_Date");
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                long countryId = resultSet.getLong("Country_Id");
                FirstLevelDivision firstLevelDivision = new FirstLevelDivision(divisionId, divisionName,
                        createDate.toLocalDateTime(), lastUpdate.toLocalDateTime(), createdBy, lastUpdatedBy, countryId);
                sortedDivisions.add(firstLevelDivision);
                System.out.println(firstLevelDivision.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBConnection.closeConnection();
        return sortedDivisions;
    }


}
