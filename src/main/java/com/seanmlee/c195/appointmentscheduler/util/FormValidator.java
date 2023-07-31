package com.seanmlee.c195.appointmentscheduler.util;

import com.seanmlee.c195.appointmentscheduler.dao.AppointmentDAO;
import com.seanmlee.c195.appointmentscheduler.model.Appointment;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Handles the exceptions for each form that might get filled out
 *
 * @author Sean Lee
 */
public class FormValidator {

    public static boolean emptyAppointmentFieldCheck(TextField title, TextField description, ComboBox type,
                                                     TextField location, DatePicker startDay, DatePicker endDay,
                                                     ComboBox startTime, ComboBox endTime, ComboBox customer,
                                                     ComboBox user, ComboBox contact) {

        return Stream.of(title.getText(), description.getText(), type.getValue(), location.getText(), startDay.getValue(),
                endDay.getValue(), startTime.getValue(), endTime.getValue(), customer.getValue(), user.getValue(),
                contact.getValue()).anyMatch(Objects::isNull) || Stream.of(title.getText(), description.getText(),
                location.getText()).anyMatch(String::isEmpty);

    }

    public static boolean emptyCustomerFieldCheck(TextField name, TextField address, TextField phone, TextField postalCode,
                                                  ComboBox country, ComboBox state) {
        return Stream.of(name.getText(), address.getText(), phone.getText(), postalCode.getText(), country.getValue(),
                state.getValue()).anyMatch(Objects::isNull) || Stream.of(name.getText(), address.getText(), phone.getText(),
                postalCode.getText()).anyMatch(String::isEmpty);

    }

    public static boolean startDateCheck(LocalDateTime startDay) {
        return startDay.isBefore(ChronoLocalDateTime.from(LocalDateTime.now()));
    }

    public static boolean endDateCheck(LocalDateTime startDay, LocalDateTime endDay) {
        return endDay.isBefore(startDay);
    }

    public static boolean appointmentOverlaps(long customerId, long userId, LocalDateTime start, LocalDateTime end,
                                              Long appointmentId) throws SQLException {
        List<Appointment> existingAppointments = AppointmentDAO.getAppointments(userId);
        existingAppointments.addAll(AppointmentDAO.getAppointmentsByCustomerID(customerId));
        for (Appointment appointment : existingAppointments) {
            if (start.isBefore(appointment.getEnd()) && end.isAfter(appointment.getStart()) &&
                    appointment.getId() != appointmentId) {
                return true;
            }
        }
        return false;
    }

    public static boolean appointmentOverlaps(long customerId, long userId, LocalDateTime start, LocalDateTime end) throws SQLException {
        List<Appointment> existingAppointments = AppointmentDAO.getAppointments(userId);
        existingAppointments.addAll(AppointmentDAO.getAppointmentsByCustomerID(customerId));
        for (Appointment appointment : existingAppointments) {
            if (start.isBefore(appointment.getEnd()) && end.isAfter(appointment.getStart())) {
                return true;
            }
        }
        return false;
    }

    public static void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
