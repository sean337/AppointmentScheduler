package com.seanmlee.c195.appointmentscheduler.controller;

import com.seanmlee.c195.appointmentscheduler.Main;
import com.seanmlee.c195.appointmentscheduler.dao.AppointmentDAO;
import com.seanmlee.c195.appointmentscheduler.dao.ContactDAO;
import com.seanmlee.c195.appointmentscheduler.dao.CustomerDAO;
import com.seanmlee.c195.appointmentscheduler.dao.FirstLevelDivisionDAO;
import com.seanmlee.c195.appointmentscheduler.model.*;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Handles all the Report tables
 * @author Sean Lee
 */
public class ReportsController implements Initializable {

    private final ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
    private final ObservableList<Contact> contactsObservableList = FXCollections.observableArrayList();
    private final ObservableList<Report> yearlyGlanceObservableList = FXCollections.observableArrayList();
    private final ObservableList<Report> divisionsOberservableList = FXCollections.observableArrayList();
    private final HashMap<String, Report> monthlyReportMap = new HashMap<String, Report>();
    private final HashMap<String, Report> divisionReportMap = new HashMap<>();
    List<FirstLevelDivision> divisionsList = FirstLevelDivisionDAO.getFirstLevelDivision();
    @FXML
    private TableColumn appointmentType;
    @FXML
    private Text reportsLabel;
    @FXML
    private TableView appointmentTableView;
    @FXML
    private TableColumn apptIdColumn;
    @FXML
    private TableColumn apptTitleColumn;
    @FXML
    private TableColumn apptTypeColumn;
    @FXML
    private TableColumn apptDescriptionColumn;
    @FXML
    private TableColumn apptStartColumn;
    @FXML
    private TableColumn apptEndColumn;
    @FXML
    private TableColumn apptLocationColumn;
    @FXML
    private TableColumn apptCustomerColumn;
    @FXML
    private Button signOutButton;
    @FXML
    private ComboBox contactComboBox;
    @FXML
    private Text contactLabel;
    @FXML
    private TableView yearlyGlanceTable;
    @FXML
    private TableColumn monthCol;
    @FXML
    private TableColumn apptTypeCol2;
    @FXML
    private TableColumn totalAppointments;
    @FXML
    private TableView divisionTable;
    @FXML
    private TableColumn divisionName;
    @FXML
    private TableColumn totalCustomers;
    @FXML
    private Button backButton;
    private Contact selectedContact;
    private List<Appointment> appointments;

    public ReportsController() throws SQLException {
    }

    /**
     * refreshes the appointment table give a list of appointments
     *
     * @param appointments
     */
    public void refreshAppointmentTable(List<Appointment> appointments) {
        appointmentObservableList.clear();
        appointmentObservableList.addAll(appointments);
        appointmentTableView.setItems(appointmentObservableList);
        appointmentTableView.refresh();
    }

    /**
     * Goes back to the welcome controller
     *
     * @param actionEvent
     * @throws IOException
     */
    public void backButtonClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("welcome-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        WelcomeController welcomeController = fxmlLoader.getController();

        stage.setTitle("Appointment Management System - Welcome");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Signs out of the current user, clears the session instance and loads the login page again
     *
     * @param actionEvent
     * @throws IOException
     */
    public void signOutClick(ActionEvent actionEvent) throws IOException {
        UserSession.resetInstance();
        Stage stage = (Stage) signOutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Appointment Management System - Login");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method runs as soon as the class is called to update changes in the tables
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get all contacts from database and add them to an observable list
        try {
            contactsObservableList.setAll(ContactDAO.getContacts());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Add that list to the associated combo box
        contactComboBox.setItems(contactsObservableList);
        // listener allows the tables to refresh any time the contact is changed
        contactComboBox.valueProperty().addListener((observableValue, oldContact, newContact) -> {

            // Clear all data structures when a contact is changed
            appointmentObservableList.clear();
            yearlyGlanceObservableList.clear();
            divisionsOberservableList.clear();
            monthlyReportMap.clear();
            // Gets the selected contact and grabs the appointments from the database
            selectedContact = (Contact) contactComboBox.getSelectionModel().getSelectedItem();
            try {
                appointments = AppointmentDAO.getAppointmentByContactId(selectedContact.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            // sets the appointments in the observable list, adds them to the proper table view and refreshes
            appointmentObservableList.setAll(appointments);
            appointmentTableView.setItems(appointmentObservableList);
            appointmentTableView.refresh();

            // loops through all the appointments extracting the start month, appointment type
            // and uses them to create a string to use a key for the map
            // create a new report with that extracted data
            // If the key already exists in the map we'll add 1 to the appointment count of that report
            for (Appointment appointment : appointments) {
                Month month = appointment.getStart().getMonth();
                String type = appointment.getType();
                String mapKey = month + "-" + type;

                Report report = monthlyReportMap.get(mapKey);
                if (report == null) {
                    report = new Report(month, type, 1);
                    monthlyReportMap.put(mapKey, report);
                } else {
                    report.setAppointmentCount(report.getAppointmentCount() + 1);
                }
            }

            // creates a list to store the reports with the update count values
            List<Report> reports = new ArrayList<>(monthlyReportMap.values());

            // sets that report in the observable list, sets it in the table and refreshes
            yearlyGlanceObservableList.setAll(reports);
            yearlyGlanceTable.setItems(yearlyGlanceObservableList);
            yearlyGlanceTable.refresh();


            // Handles the customers by region report table
            for (FirstLevelDivision division : divisionsList) {
                String divisionName = division.getName();
                Report report = new Report(divisionName, 0);
                divisionsOberservableList.add(report);
                divisionReportMap.put(divisionName, report);
            }
            // loops through all the appointments finding the customer associated with each
            // extracts the division name for that customer and looks for it in the Map
            // if it doesn't exist we'll set it and add 1 to the customer count value in that division
            // set items in observable list and refresh the tables.
            for (Appointment appointment : appointments) {
                try {
                    Customer customer = CustomerDAO.findCustomer(appointment.getCustomerId());
                    String divisionName = FirstLevelDivisionDAO.getDivisionNameById(customer.getDivisionId());
                    Report report = divisionReportMap.get(divisionName);
                    if (report != null) {
                        report.setCustomerCount(report.getCustomerCount() + 1);
                        System.out.println(report.getCustomerCount());
                        System.out.println(report.getDivisionName());
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            divisionTable.setItems(divisionsOberservableList);
            divisionTable.refresh();
        });

        // Pairs all columns to the correct object and attribute
        apptIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));


        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        totalAppointments.setCellValueFactory(new PropertyValueFactory<>("appointmentCount"));
        yearlyGlanceTable.refresh();

        divisionName.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        totalCustomers.setCellValueFactory(new PropertyValueFactory<>("customerCount"));
        divisionTable.refresh();


    }
}
