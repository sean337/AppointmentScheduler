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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateAppointmentController implements Initializable {


    @FXML private Text updateAppointmentTitle;
    @FXML private Text apptTitleLabel;
    @FXML private TextField apptTitleTextField;
    @FXML private Text appTypetLabel;
    @FXML private TextField apptTypeTextField;
    @FXML private Text apptDescriptionLabel;
    @FXML private TextField apptDescriptionTextField;
    @FXML private Text apptLocationLabel;
    @FXML private TextField apptLocationTextField;
    @FXML private Button saveModdedApptButton;
    @FXML private Button cancelChangesButton;
    @FXML private Text apptStartDateLabel;
    @FXML private DatePicker apptStartDatePicker;
    @FXML private Text apptEndDateLabel;
    @FXML private DatePicker apptEndDatePicker;
    @FXML private Text apptStartTimeLabel;
    @FXML private ComboBox apptStartTimeComboBox;
    @FXML private Text timeFormatLabel1;
    @FXML private Text apptEndTimeLabel;
    @FXML private ComboBox apptEndTimeComboBox;
    @FXML private Text apptCustIdLabel;
    @FXML private Text timeFormatLabel2;
    @FXML private ComboBox apptCustomerComboBox;
    @FXML private Text apptUserIdLabel;
    @FXML private ComboBox apptUserComboBox;
    @FXML private Text apptContactLabel;
    @FXML private ComboBox apptContactComboBox;

    private ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private ObservableList<User> users = FXCollections.observableArrayList();
    private Appointment selectedAppointment;

    @Override @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            contacts.addAll(ContactDAO.getContacts());
            customers.addAll(CustomerDAO.getCustomers());
            users.addAll(UserDAO.getUsers());
            apptContactComboBox.setItems(contacts);
            apptUserComboBox.setItems(users);
            apptCustomerComboBox.setItems(customers);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        apptStartTimeComboBox.setItems(DateTimeUtil.generateTimeSlots());
        apptEndTimeComboBox.setItems(DateTimeUtil.generateTimeSlots());

    }

    public void setAppointmentData(Appointment appointment) {
        selectedAppointment = appointment;
        apptTitleTextField.setText(appointment.getTitle());
        apptTypeTextField.setText(appointment.getType());
        apptDescriptionTextField.setText(appointment.getDescription());
        apptLocationTextField.setText(appointment.getLocation());
        apptStartDatePicker.setValue(appointment.getStart().toLocalDate());
        apptStartTimeComboBox.setValue(appointment.getStart().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        apptEndDatePicker.setValue(appointment.getEnd().toLocalDate());
        apptEndTimeComboBox.setValue(appointment.getEnd().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));

        long customerId = appointment.getCustomerId();
        for (Customer customer : customers) {
            if (customer.getId() == customerId) {
                apptCustomerComboBox.setValue(customer);
                break;
            }
        }

        long userId = appointment.getUserId();
        for (User user : users) {
            if (user.getId() == userId) {
                apptUserComboBox.setValue(user);
                break;
            }
        }

        long contactId = appointment.getContactId();
        for (Contact contact : contacts) {
            if (contact.getId() == contactId) {
                apptContactComboBox.setValue(contact);
                break;
            }
        }

    }

    public void onSaveModsButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        boolean result = FormValidator.emptyAppointmentFieldCheck(apptTitleTextField, apptDescriptionTextField,
                apptTypeTextField, apptLocationTextField, apptStartDatePicker, apptEndDatePicker,
                apptStartTimeComboBox, apptEndTimeComboBox, apptCustomerComboBox, apptUserComboBox,
                apptContactComboBox);

        if (result) {
            FormValidator.showAlert("Warning", "Empty fields found!", "All fields must be filled out" +
                    "to schedule an appointment");
            return;
        }
            String title = apptTitleTextField.getText();
            String type = apptTypeTextField.getText();
            String description = apptDescriptionTextField.getText();
            String location = apptLocationTextField.getText();
            LocalDateTime start = DateTimeUtil.parseDateTime(apptStartDatePicker, apptStartTimeComboBox);
            LocalDateTime end = DateTimeUtil.parseDateTime(apptEndDatePicker, apptEndTimeComboBox);
            LocalDateTime createDate = selectedAppointment.getCreateDate();
            String createdBy = selectedAppointment.getCreatedBy();
            String lastUpdatedBy = UserDAO.getLoggedInUserName();
            Contact selectedContact = (Contact) apptContactComboBox.getValue();
            Customer selectedCustomer = (Customer) apptCustomerComboBox.getValue();
            User selectedUser = (User) apptUserComboBox.getValue();
            long customerId = selectedCustomer.getId();
            long userId = selectedUser.getId();
            long contactId = selectedContact.getId();

            boolean startCheck = FormValidator.startDateCheck(start);
            boolean endCheck = FormValidator.endDateCheck(start, end);
            boolean overlapCheck = FormValidator.appointmentOverlaps(customerId, userId,
                    start, end, selectedAppointment.getId());

            if (endCheck) {
                FormValidator.showAlert("Warning", "Invalid Selection", "Your end date " +
                        "is before your start date");
            } else if (startCheck) {
                FormValidator.showAlert("Warning", "Invalid Selection", "Your appointment Start " +
                        "must begin after the current date and time");
            } else if (overlapCheck) {
                FormValidator.showAlert("Warning", "Overlapping Appointment Data", "Selected user or " +
                        "customer has overlapping appointments for the date and time selected.");
            } else {
                Appointment appointment = new Appointment(title, description, location, type, start, end, createDate,
                        LocalDateTime.now(), createdBy, lastUpdatedBy, customerId, userId,
                        contactId);
                appointment.setId(selectedAppointment.getId());

                AppointmentDAO.updateAppointment(appointment);

                Stage stage = (Stage) saveModdedApptButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-dashboard-view.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root, 1191, 670);

                MainDashboardController mainDashboardController = fxmlLoader.getController();
                List<Appointment> userAppointmentList = AppointmentDAO.getAppointments(UserSession.getInstance().getUserId());
                mainDashboardController.refreshAppointmentTable(userAppointmentList);
                mainDashboardController.refreshCustomerTable(CustomerDAO.getCustomers());
                stage.setTitle("Appointment Management System - Your Appointments");
                stage.setScene(scene);
                stage.show();
            }

        }


    public void onCancelChangesButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        Stage stage = (Stage) cancelChangesButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-dashboard-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        MainDashboardController mainDashboardController = fxmlLoader.getController();
        long userId = UserSession.getInstance().getUserId();
        List<Appointment> userAppointmentList = AppointmentDAO.getAppointments(userId);
        mainDashboardController.refreshAppointmentTable(userAppointmentList);

        stage.setTitle("Appointment Management System - Your Appointments");
        stage.setScene(scene);
        stage.show();
    }

}
