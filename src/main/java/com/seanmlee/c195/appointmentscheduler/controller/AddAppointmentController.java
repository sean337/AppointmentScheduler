package com.seanmlee.c195.appointmentscheduler.controller;

import com.seanmlee.c195.appointmentscheduler.Main;
import com.seanmlee.c195.appointmentscheduler.dao.AppointmentDAO;
import com.seanmlee.c195.appointmentscheduler.dao.ContactDAO;
import com.seanmlee.c195.appointmentscheduler.dao.CustomerDAO;
import com.seanmlee.c195.appointmentscheduler.dao.UserDAO;
import com.seanmlee.c195.appointmentscheduler.model.Appointment;
import com.seanmlee.c195.appointmentscheduler.model.Contact;
import com.seanmlee.c195.appointmentscheduler.model.Customer;
import com.seanmlee.c195.appointmentscheduler.model.User;
import com.seanmlee.c195.appointmentscheduler.util.DateTimeUtil;
import com.seanmlee.c195.appointmentscheduler.util.FormValidator;
import com.seanmlee.c195.appointmentscheduler.util.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Sean Lee
 * This class handles adding an appointment to the database
 */
public class AddAppointmentController implements Initializable {

    ObservableList appointmentTypes = FXCollections.observableArrayList();
    @FXML
    private ComboBox apptTypeComboBox;
    @FXML
    private Text apptTitleLabel;
    @FXML
    private TextField apptTitleTextField;
    @FXML
    private Text appTypetLabel;
    @FXML
    private TextField apptTypeTextField;
    @FXML
    private Text apptDescriptionLabel;
    @FXML
    private TextField apptDescriptionTextField;
    @FXML
    private Text apptLocationLabel;
    @FXML
    private TextField apptLocationTextField;
    @FXML
    private Button saveApptButton;
    @FXML
    private Button cancelApptButton;
    @FXML
    private Text apptStartDateLabel;
    @FXML
    private DatePicker apptStartDatePicker;
    @FXML
    private Text apptEndDateLabel;
    @FXML
    private DatePicker apptEndDatePicker;
    @FXML
    private Text apptStartTimeLabel;
    @FXML
    private ComboBox apptStartTimeComboBox;
    @FXML
    private Text timeFormatLabel1;
    @FXML
    private Text apptEndTimeLabel;
    @FXML
    private ComboBox apptEndTimeComboBox;
    @FXML
    private Text timeFormatLabel2;
    @FXML
    private Text apptCustIdLabel;
    @FXML
    private ComboBox apptCustomerComboBox;
    @FXML
    private Text apptUserIdLabel;
    @FXML
    private ComboBox apptUserComboBox;
    @FXML
    private Text apptContactLabel;
    @FXML
    private ComboBox apptContactComboBox;
    @FXML
    private Text newAppointmentTitle;
    private final ObservableList<Customer> customers = FXCollections.observableArrayList();

    private final ObservableList<Contact> contacts = FXCollections.observableArrayList();

    private final ObservableList<User> users = FXCollections.observableArrayList();

    /**
     * This method saves an appointment by collecting the data from the user input fields
     * assigning it to a new appointment attribute, pushing it to the database and launching the main dashboard again
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onSaveApptButtonClick(ActionEvent actionEvent) throws SQLException, IOException {


        boolean result = FormValidator.emptyAppointmentFieldCheck(apptTitleTextField, apptDescriptionTextField,
                apptTypeComboBox, apptLocationTextField, apptStartDatePicker, apptEndDatePicker,
                apptStartTimeComboBox, apptEndTimeComboBox, apptCustomerComboBox, apptUserComboBox,
                apptContactComboBox);

        if (result) {
            FormValidator.showAlert("Warning", "Empty fields found!", "All fields must be filled out" +
                    " to schedule an appointment");
            return;
        }
        String currentUserName = UserDAO.getLoggedInUserName();
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        Contact selectedContact = (Contact) apptContactComboBox.getValue();
        Customer selectedCustomer = (Customer) apptCustomerComboBox.getValue();
        User selectedUser = (User) apptUserComboBox.getValue();
        String title = apptTitleTextField.getText();
        String type = (String) apptTypeComboBox.getValue();
        String description = apptDescriptionTextField.getText();
        String location = apptLocationTextField.getText();
        String createdBy = currentUserName;
        String lastUpdatedBy = currentUserName;
        LocalDateTime start = DateTimeUtil.parseDateTime(apptStartDatePicker, apptStartTimeComboBox);
        LocalDateTime end = DateTimeUtil.parseDateTime(apptEndDatePicker, apptEndTimeComboBox);
        long userId = selectedUser.getId();
        long contactId = selectedContact.getId();
        long customerId = selectedCustomer.getId();

        boolean startCheck = FormValidator.startDateCheck(start);
        boolean endCheck = FormValidator.endDateCheck(start, end);
        boolean overlapCheck = FormValidator.appointmentOverlaps(customerId,
                start, end);

        if (endCheck) {
            FormValidator.showAlert("Warning", "Invalid Selection", "Your end date " +
                    "is before your start date");
        } else if (startCheck) {
            FormValidator.showAlert("Warning", "Invalid Selection", "Your appointment Start " +
                    "must begin after the current date and time");
        } else if (overlapCheck) {
            FormValidator.showAlert("Warning", "Overlapping Appointment Data", "Selected " +
                    "customer has overlapping appointments for the date and time selected.");
        } else {
            Appointment appointment = new Appointment(title, description, location, type, start, end, currentLocalDateTime,
                    LocalDateTime.now(), createdBy, lastUpdatedBy, customerId, userId,
                    contactId);
            AppointmentDAO.createAppointment(appointment);

            Stage stage = (Stage) saveApptButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-dashboard-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 1191, 670);

            MainDashboardController mainDashboardController = fxmlLoader.getController();
            List<Appointment> userAppointmentList = AppointmentDAO.getAppointments(userId);
            mainDashboardController.refreshAppointmentTable(userAppointmentList);
            mainDashboardController.refreshCustomerTable(CustomerDAO.getCustomers());

            stage.setTitle("Appointment Management System - Your Appointments");
            stage.setScene(scene);
            stage.show();

        }
    }

    /**
     * Cancels request to make a new appointment and returns back to the main dashboard
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onCancelApptButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        Stage stage = (Stage) cancelApptButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-dashboard-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        MainDashboardController mainDashboardController = fxmlLoader.getController();
        long userId = UserSession.getInstance().getUserId();
        List<Appointment> userAppointmentList = AppointmentDAO.getAppointments(UserSession.getInstance().getUserId());
        List<Customer> allCustomersList = CustomerDAO.getCustomers();
        mainDashboardController.refreshAppointmentTable(userAppointmentList);
        mainDashboardController.refreshCustomerTable(allCustomersList);
        stage.setTitle("Appointment Management System - Your Appointments");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets the scene with all the appropriate data
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            contacts.setAll(ContactDAO.getContacts());
            customers.setAll(CustomerDAO.getCustomers());
            users.setAll(UserDAO.getUsers());
            apptContactComboBox.setItems(contacts);
            apptCustomerComboBox.setItems(customers);
            apptStartTimeComboBox.setItems(DateTimeUtil.generateTimeSlots());
            apptEndTimeComboBox.setItems(DateTimeUtil.generateTimeSlots());
            apptUserComboBox.setItems(users);
            appointmentTypes.setAll("Planning", "De-Briefing", "Brainstorming", "Progress Review");
            apptTypeComboBox.setItems(appointmentTypes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
