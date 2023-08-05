package com.seanmlee.c195.appointmentscheduler.controller;

import com.seanmlee.c195.appointmentscheduler.Main;
import com.seanmlee.c195.appointmentscheduler.dao.AppointmentDAO;
import com.seanmlee.c195.appointmentscheduler.dao.CustomerDAO;
import com.seanmlee.c195.appointmentscheduler.model.Appointment;
import com.seanmlee.c195.appointmentscheduler.model.Customer;
import com.seanmlee.c195.appointmentscheduler.util.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Welcome Screen
 *
 * @author Sean Lee
 */
public class WelcomeController implements Initializable {
    @FXML
    private Text noAppointmentsLabel;
    @FXML
    private Text welcomeLabel;
    @FXML
    private Button viewAppointmentsButton;
    @FXML
    private Button viewCustomersButton;
    @FXML
    private Button reportsButton;
    @FXML
    private Button signOutButton;

    /**
     * Sends user to the main dashboard
     *
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onViewApptButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        Stage stage = (Stage) viewAppointmentsButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-dashboard-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1191, 670);
        MainDashboardController mainDashboardController = fxmlLoader.getController();
        long userId = UserSession.getInstance().getUserId();
        List<Appointment> userAppointmentList = AppointmentDAO.getAppointments(userId);
        List<Customer> allCustomersList = CustomerDAO.getCustomers();
        mainDashboardController.refreshAppointmentTable(userAppointmentList);
        mainDashboardController.refreshCustomerTable(allCustomersList);
        stage.setTitle("Appointment Management System - Your Appointments");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Sends user to reports dashboard
     *
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void onViewReportsButtonClick(ActionEvent actionEvent) throws IOException, SQLException {
        Stage stage = (Stage) reportsButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("reports-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);


        stage.setTitle("Appointment Management System - Your Appointments");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Signs out of the current user and returns to the login screen
     *
     * @param actionEvent
     * @throws IOException
     */
    public void onSignOutButtonClick(ActionEvent actionEvent) throws IOException {
        UserSession.resetInstance();
        Stage stage = (Stage) signOutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Appointment Management System - Login");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializes the GUI components when the view is loaded and checks for upcoming appointments.
     *
     * This method is called after all @FXML annotated members have been injected. It retrieves the appointments for the
     * current user from the database and checks if there are any appointments starting in the next 15 minutes. If there
     * are, it displays an alert to the user.
     *
     * The lambda expression in this method is used in a stream pipeline to filter the list of appointments. The lambda
     * expression defines the filter condition: an appointment is included in the resulting list if its start time is
     * after the current time and before 15 minutes from now. Using a lambda expression here allows us to define this
     * condition directly within the call to filter(), making the code more concise and easier to read.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     * @throws RuntimeException if an SQLException occurs while retrieving appointments from the database.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Appointment> userAppointmentList = null;
        try {
            userAppointmentList = AppointmentDAO.getAppointments(UserSession.getInstance().getUserId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Appointment> upcomingAppointments = userAppointmentList.stream()
                .filter(appointment -> appointment.getStart().isAfter(LocalDateTime.now())
                        && appointment.getStart().isBefore(LocalDateTime.now().plusMinutes(15))).toList();
        if (!upcomingAppointments.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("REMINDER! Appointment stating soon!");
            alert.setHeaderText("You have " + upcomingAppointments.size() + " appointments in the next 15 minutes");
            StringBuilder appointmentStarts = new StringBuilder();
            for (Appointment appointment : upcomingAppointments) {
                appointmentStarts.append("\nAppointment ID: " + appointment.getId() +
                        "\nAppointment Start Time: " + appointment.getStart().toString());
            }
            alert.setContentText(appointmentStarts.toString());
            alert.showAndWait();
        } else {
            noAppointmentsLabel.setVisible(true);
        }
    }
}
