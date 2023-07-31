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
     * Gathers the appointments and lets user know if they have an appointment in the next 15 minutes
     *
     * @param url
     * @param resourceBundle
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
            alert.setTitle("REMINDER!!!");
            alert.setHeaderText("Appointment Starting Soon");
            alert.setContentText("You have " + upcomingAppointments.size() + " appointments in the next 15 minutes");
            alert.showAndWait();
        }
    }
}
