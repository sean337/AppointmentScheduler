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
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML private Text reportsLabel;
    @FXML private TableView appointmentTableView;
    @FXML private TableColumn apptIdColumn;
    @FXML private TableColumn apptTitleColumn;
    @FXML private TableColumn apptTypeColumn;
    @FXML private TableColumn apptDescriptionColumn;
    @FXML private TableColumn apptStartColumn;
    @FXML private TableColumn apptEndColumn;
    @FXML private TableColumn apptLocationColumn;
    @FXML private TableColumn apptCustomerColumn;
    @FXML private Button signOutButton;
    @FXML private ComboBox contactComboBox;
    @FXML private Text contactLabel;
    @FXML private TableView yearlyGlanceTable;
    @FXML private TableColumn monthCol;
    @FXML private TableColumn apptTypeCol2;
    @FXML private TableColumn totalAppointments;
    @FXML private TableView divisionTable;
    @FXML private TableColumn divisionName;
    @FXML private TableColumn totalCustomers;
    @FXML private Button backButton;

    private Contact selectedContact;

    private List<Appointment> appointments;

    private  ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
    private  ObservableList<Contact> contactsObservableList = FXCollections.observableArrayList();

    private ObservableList<Report> reportsOberservableList = FXCollections.observableArrayList();

    private ObservableList<Report> divisionsOberservableList = FXCollections.observableArrayList();
    private HashMap<Month, Report> monthlyReportMap = new HashMap<Month, Report>();

    private HashMap<FirstLevelDivision, Report> divisionReportMap = new HashMap<>();

    List<FirstLevelDivision> divisionsList = FirstLevelDivisionDAO.getFirstLevelDivision();

    public ReportsController() throws SQLException {
    }

    public void refreshAppointmentTable(List<Appointment> appointments) {
        appointmentObservableList.clear();
        appointmentObservableList.addAll(appointments);
        appointmentTableView.setItems(appointmentObservableList);
        appointmentTableView.refresh();
    }

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

    public void signOutClick(ActionEvent actionEvent) throws IOException {
        UserSession.resetInstance();
        Stage stage = (Stage) signOutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Appointment Management System - Login");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactsObservableList.setAll(ContactDAO.getContacts());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        contactComboBox.setItems(contactsObservableList);
        contactComboBox.valueProperty().addListener((observableValue, o, t1) -> {
            appointmentObservableList.clear();
            reportsOberservableList.clear();
            monthlyReportMap.clear();

            selectedContact = (Contact) contactComboBox.getSelectionModel().getSelectedItem();
            try {
                appointments = AppointmentDAO.getAppointmentByContactId(selectedContact.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            appointmentObservableList.setAll(appointments);
            appointmentTableView.setItems(appointmentObservableList);
            appointmentTableView.refresh();


            for (Month month : Month.values()) {
                Report report = new Report(month, 0);
                reportsOberservableList.add(report);
                monthlyReportMap.put(month, report);
            }
            for (Appointment appointment : appointments) {
                Month month = appointment.getStart().getMonth();
                Report report = monthlyReportMap.get(month);
                if (report != null) {
                    report.setAppointmentCount(report.getAppointmentCount() + 1);
                }
            }
            yearlyGlanceTable.setItems(reportsOberservableList);
            yearlyGlanceTable.refresh();


            for (FirstLevelDivision division : divisionsList) {
                String divisionName = division.getName();
                Report report = new Report(0, divisionName);
                divisionReportMap.put(division, report);
                divisionsOberservableList.add(report);
                }

            for (Appointment appointment : appointments) {
                try {
                    Customer customer = CustomerDAO.findCustomer(appointment.getCustomerId());
                    String divisionName = FirstLevelDivisionDAO.getDivisionNameById(customer.getDivisionId());
                    Report report = divisionReportMap.get(divisionName);
                    if (report != null) {
                        report.setCustomerCount(report.getCustomerCount() + 1);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                divisionTable.setItems(divisionsOberservableList);
                divisionTable.refresh();
            }
            yearlyGlanceTable.setItems(reportsOberservableList);
            yearlyGlanceTable.refresh();
            divisionTable.setItems(divisionsOberservableList);
            divisionTable.refresh();








        });




        apptIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));


        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        totalAppointments.setCellValueFactory(new PropertyValueFactory<>("appointmentCount"));
        yearlyGlanceTable.refresh();

        divisionName.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        totalCustomers.setCellValueFactory(new PropertyValueFactory<>("customerCount"));
        divisionTable.refresh();


    }
}
