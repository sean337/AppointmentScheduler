package com.seanmlee.c195.appointmentscheduler.dao;

import com.seanmlee.c195.appointmentscheduler.model.Appointment;
import com.seanmlee.c195.appointmentscheduler.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Enables access to the appointments database
 *
 * @author Sean Lee
 */
public class AppointmentDAO {

    public static List<Appointment> getAppointments(long userId) throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "SELECT * FROM client_schedule.appointments WHERE User_ID = ?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setLong(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Appointment> appointmentList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                long appointmentId = resultSet.getLong("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                Timestamp startTimestamp = resultSet.getTimestamp("Start");
                Timestamp endTimeStamp = resultSet.getTimestamp("End");
                Timestamp createTimeStamp = resultSet.getTimestamp("Create_Date");
                LocalDateTime start = startTimestamp.toLocalDateTime();
                LocalDateTime end = endTimeStamp.toLocalDateTime();
                LocalDateTime createDate = createTimeStamp.toLocalDateTime();
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdateTimeStamp = resultSet.getTimestamp("Last_Update");
                LocalDateTime lastUpdate = lastUpdateTimeStamp.toLocalDateTime();
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                long customerId = resultSet.getLong("Customer_ID");
                long userIdCol = resultSet.getLong("User_ID");
                long contactId = resultSet.getLong("Contact_ID");
                Appointment appointment = new Appointment(appointmentId, title, description, location, type, start, end,
                        createDate, lastUpdate, createdBy, lastUpdatedBy, customerId, userIdCol, contactId);
                appointmentList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBConnection.closeConnection();
        return appointmentList;
    }

    /**
     * Returns all appointments in the next 30days
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    public static List<Appointment> getAppointmentsByMonth(long userId) throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "SELECT * FROM client_schedule.appointments WHERE User_ID = ? AND Start >= ? AND Start < ?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setLong(1, userId);

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime thirtyDaysFromToday = today.plusDays(30);
        preparedStatement.setTimestamp(2, Timestamp.valueOf(today));
        preparedStatement.setTimestamp(3, Timestamp.valueOf(thirtyDaysFromToday));

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Appointment> appointmentList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                long appointmentId = resultSet.getLong("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                Timestamp startTimestamp = resultSet.getTimestamp("Start");
                Timestamp endTimeStamp = resultSet.getTimestamp("End");
                Timestamp createTimeStamp = resultSet.getTimestamp("Create_Date");
                LocalDateTime start = startTimestamp.toLocalDateTime();
                LocalDateTime end = endTimeStamp.toLocalDateTime();
                LocalDateTime createDate = createTimeStamp.toLocalDateTime();
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdateTimeStamp = resultSet.getTimestamp("Last_Update");
                LocalDateTime lastUpdate = lastUpdateTimeStamp.toLocalDateTime();
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                long customerId = resultSet.getLong("Customer_ID");
                long userIdCol = resultSet.getLong("User_ID");
                long contactId = resultSet.getLong("Contact_ID");
                Appointment appointment = new Appointment(appointmentId, title, description, location, type, start, end,
                        createDate, lastUpdate, createdBy, lastUpdatedBy, customerId, userIdCol, contactId);
                appointmentList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBConnection.closeConnection();
        return appointmentList;
    }

    /**
     * Returns all appointments in the next 7 days
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    public static List<Appointment> getAppointmentsByWeek(long userId) throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "SELECT * FROM client_schedule.appointments WHERE User_ID = ? AND Start >= ? AND Start < ?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setLong(1, userId);

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime sevenDaysFromToday = today.plusDays(7);
        preparedStatement.setTimestamp(2, Timestamp.valueOf(today));
        preparedStatement.setTimestamp(3, Timestamp.valueOf(sevenDaysFromToday));

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Appointment> appointmentList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                long appointmentId = resultSet.getLong("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                Timestamp startTimestamp = resultSet.getTimestamp("Start");
                Timestamp endTimeStamp = resultSet.getTimestamp("End");
                Timestamp createTimeStamp = resultSet.getTimestamp("Create_Date");
                LocalDateTime start = startTimestamp.toLocalDateTime();
                LocalDateTime end = endTimeStamp.toLocalDateTime();
                LocalDateTime createDate = createTimeStamp.toLocalDateTime();
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdateTimeStamp = resultSet.getTimestamp("Last_Update");
                LocalDateTime lastUpdate = lastUpdateTimeStamp.toLocalDateTime();
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                long customerId = resultSet.getLong("Customer_ID");
                long userIdCol = resultSet.getLong("User_ID");
                long contactId = resultSet.getLong("Contact_ID");
                Appointment appointment = new Appointment(appointmentId, title, description, location, type, start, end,
                        createDate, lastUpdate, createdBy, lastUpdatedBy, customerId, userIdCol, contactId);
                appointmentList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBConnection.closeConnection();
        return appointmentList;
    }


    /**
     * Deletes appointment from database
     *
     * @param appointmentId
     * @throws SQLException
     */
    public static void deleteAppointment(long appointmentId) throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "DELETE FROM client_schedule.appointments WHERE Appointment_ID=?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setLong(1, appointmentId);
        preparedStatement.executeUpdate();
        DBConnection.closeConnection();
    }

    /**
     * Adds appointment to database
     *
     * @param appointment
     * @throws SQLException
     */
    public static void createAppointment(Appointment appointment) throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "INSERT INTO client_schedule.appointments" +
                "(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By," +
                " Customer_ID, User_ID, Contact_ID)" +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setString(1, appointment.getTitle());
        preparedStatement.setString(2, appointment.getDescription());
        preparedStatement.setString(3, appointment.getLocation());
        preparedStatement.setString(4, appointment.getType());
        preparedStatement.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
        preparedStatement.setTimestamp(7, Timestamp.valueOf(appointment.getCreateDate()));
        preparedStatement.setString(8, appointment.getCreatedBy());
        preparedStatement.setTimestamp(9, Timestamp.valueOf(appointment.getLastUpdate()));
        preparedStatement.setString(10, appointment.getLastUpdatedBy());
        preparedStatement.setLong(11, appointment.getCustomerId());
        preparedStatement.setLong(12, appointment.getUserId());
        preparedStatement.setLong(13, appointment.getContactId());
        preparedStatement.executeUpdate();
        DBConnection.closeConnection();
    }

    /**
     * Updates appointment in database
     *
     * @param appointment
     * @throws SQLException
     */
    public static void updateAppointment(Appointment appointment) throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "UPDATE client_schedule.appointments " +
                "set Title = ?, " +
                "Description = ?, " +
                "Location = ?, " +
                "Type = ?, " +
                "Start = ?, " +
                "End = ?," +
                "Last_Update = ?, " +
                "Last_Updated_By = ?, " +
                "Customer_ID = ?, " +
                "User_ID = ?, " +
                "Contact_ID = ? " +
                "where Appointment_ID = ?;";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setString(1, appointment.getTitle());
        preparedStatement.setString(2, appointment.getDescription());
        preparedStatement.setString(3, appointment.getLocation());
        preparedStatement.setString(4, appointment.getType());
        preparedStatement.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
        preparedStatement.setTimestamp(7, Timestamp.valueOf(appointment.getLastUpdate()));
        preparedStatement.setString(8, appointment.getLastUpdatedBy());
        preparedStatement.setLong(9, appointment.getCustomerId());
        preparedStatement.setLong(10, appointment.getUserId());
        preparedStatement.setLong(11, appointment.getContactId());
        preparedStatement.setLong(12, appointment.getId());
        preparedStatement.executeUpdate();
        DBConnection.closeConnection();
    }

    /**
     * Gets all appointments given a certain contact ID
     *
     * @param contactId
     * @return
     * @throws SQLException
     */
    public static List<Appointment> getAppointmentByContactId(long contactId) throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "SELECT * FROM client_schedule.appointments WHERE Contact_ID = ?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setLong(1, contactId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Appointment> appointmentList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                long appointmentId = resultSet.getLong("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                Timestamp startTimestamp = resultSet.getTimestamp("Start");
                Timestamp endTimeStamp = resultSet.getTimestamp("End");
                Timestamp createTimeStamp = resultSet.getTimestamp("Create_Date");
                LocalDateTime start = startTimestamp.toLocalDateTime();
                LocalDateTime end = endTimeStamp.toLocalDateTime();
                LocalDateTime createDate = createTimeStamp.toLocalDateTime();
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdateTimeStamp = resultSet.getTimestamp("Last_Update");
                LocalDateTime lastUpdate = lastUpdateTimeStamp.toLocalDateTime();
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                long customerId = resultSet.getLong("Customer_ID");
                long userIdCol = resultSet.getLong("User_ID");
                long apptContactId = resultSet.getLong("Contact_ID");
                Appointment appointment = new Appointment(appointmentId, title, description, location, type, start, end,
                        createDate, lastUpdate, createdBy, lastUpdatedBy, customerId, userIdCol, apptContactId);
                appointmentList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBConnection.closeConnection();
        return appointmentList;
    }

    /**
     * Gets a list of appointments given a particular customer ID
     *
     * @param customerId
     * @return
     * @throws SQLException
     */
    public static List<Appointment> getAppointmentsByCustomerID(long customerId) throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "SELECT * FROM client_schedule.appointments WHERE Customer_ID = ?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setLong(1, customerId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Appointment> appointmentList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                long appointmentId = resultSet.getLong("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                Timestamp startTimestamp = resultSet.getTimestamp("Start");
                Timestamp endTimeStamp = resultSet.getTimestamp("End");
                Timestamp createTimeStamp = resultSet.getTimestamp("Create_Date");
                LocalDateTime start = startTimestamp.toLocalDateTime();
                LocalDateTime end = endTimeStamp.toLocalDateTime();
                LocalDateTime createDate = createTimeStamp.toLocalDateTime();
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdateTimeStamp = resultSet.getTimestamp("Last_Update");
                LocalDateTime lastUpdate = lastUpdateTimeStamp.toLocalDateTime();
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                long custId = resultSet.getLong("Customer_ID");
                long userIdCol = resultSet.getLong("User_ID");
                long apptContactId = resultSet.getLong("Contact_ID");
                Appointment appointment = new Appointment(appointmentId, title, description, location, type, start, end,
                        createDate, lastUpdate, createdBy, lastUpdatedBy, custId, userIdCol, apptContactId);
                appointmentList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBConnection.closeConnection();
        return appointmentList;
    }
}
