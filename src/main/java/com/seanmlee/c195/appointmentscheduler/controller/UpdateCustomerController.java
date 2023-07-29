package com.seanmlee.c195.appointmentscheduler.controller;

import com.seanmlee.c195.appointmentscheduler.Main;
import com.seanmlee.c195.appointmentscheduler.dao.*;
import com.seanmlee.c195.appointmentscheduler.model.Appointment;
import com.seanmlee.c195.appointmentscheduler.model.Country;
import com.seanmlee.c195.appointmentscheduler.model.Customer;
import com.seanmlee.c195.appointmentscheduler.model.FirstLevelDivision;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {
    @FXML
    private Text nameLabel;
    @FXML
    private TextField nameTextField;
    @FXML
    private Text addresslabel;
    @FXML
    private TextField addressTextField;
    @FXML
    private Text phoneLabel;
    @FXML
    private TextField phoneTextField;
    @FXML
    private Text countryLabel;
    @FXML
    private Text stateLabel;
    @FXML
    private Text postalCodeLabel;
    @FXML
    private ComboBox countrySelector;
    @FXML
    private ComboBox stateSelector;
    @FXML
    private TextField postalCodeTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private Customer selectedCustomer;

    private ObservableList<String> countryNames = FXCollections.observableArrayList();

    private ObservableList<String> firstLevelDivisions = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            for (Country country : CountryDAO.getCountries()) {
                countryNames.add(String.valueOf(country.getCountry()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        countrySelector.setItems(countryNames);
        countrySelector.valueProperty().addListener((observableValue, o, t1) -> {
            try {
                firstLevelDivisionSwitch();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

        public void onSaveClick (ActionEvent actionEvent) throws SQLException, IOException {
            long userId = UserSession.getInstance().getUserId();
            String currentUserName = UserDAO.getLoggedInUserName();
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());

            String name = nameTextField.getText();
            String address = addressTextField.getText();
            String phone = phoneTextField.getText();
            String postalCode = postalCodeTextField.getText();

            String divisionName = (String) stateSelector.getSelectionModel().getSelectedItem();
            long divisionId = FirstLevelDivisionDAO.getDivisionIdByName(divisionName);
            Customer customer = new Customer(name, address, postalCode, phone, now.toLocalDateTime(), now.toLocalDateTime(),
                    currentUserName, currentUserName, divisionId);
            customer.setId(selectedCustomer.getId());
            CustomerDAO.updateCustomer(customer);

            Stage stage = (Stage) saveButton.getScene().getWindow();
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

        public void setCustomerData (Customer customer) throws SQLException {
            selectedCustomer = customer;
            nameTextField.setText(customer.getName());
            addressTextField.setText(customer.getAddress());
            phoneTextField.setText(customer.getPhoneNumber());
            String divisionName = FirstLevelDivisionDAO.getDivisionNameById(customer.getDivisionId());
            System.out.println(divisionName);
            String countryName = CountryDAO.getCountryNameByDivisionName(divisionName);
            countrySelector.setValue(countryName);
            stateSelector.setValue(divisionName);
            postalCodeTextField.setText(customer.getPostalCode());
        }

        public void onCancelClick (ActionEvent actionEvent) throws SQLException, IOException {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
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
    public void firstLevelDivisionSwitch() throws SQLException {
        List<FirstLevelDivision> divisions = null;
        firstLevelDivisions.clear();
        try {

            if (countrySelector.getSelectionModel().getSelectedItem().equals("U.S")) {
                divisions = FirstLevelDivisionDAO.sortedDivisions(1);
                for (FirstLevelDivision division : divisions) {
                    firstLevelDivisions.add(division.getName());
                }
                stateSelector.setItems(firstLevelDivisions);

            } else if (countrySelector.getValue().equals("UK")) {
                divisions = FirstLevelDivisionDAO.sortedDivisions(2);
                for (FirstLevelDivision division : divisions) {
                    firstLevelDivisions.add(division.getName());
                }
                stateSelector.setItems(firstLevelDivisions);
            } else if (countrySelector.getValue().equals("Canada")) {
                divisions = FirstLevelDivisionDAO.sortedDivisions(3);
                for (FirstLevelDivision division : divisions) {
                    firstLevelDivisions.add(division.getName());
                }
                stateSelector.setItems(firstLevelDivisions);
            } else {
                divisions = new ArrayList();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
