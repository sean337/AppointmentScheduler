package com.seanmlee.c195.appointmentscheduler.controller;

import com.seanmlee.c195.appointmentscheduler.Main;
import com.seanmlee.c195.appointmentscheduler.dao.AppointmentDAO;
import com.seanmlee.c195.appointmentscheduler.dao.ContactDAO;
import com.seanmlee.c195.appointmentscheduler.model.Appointment;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
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


    private final ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();

    public void updateTablesByContact() throws SQLException {

//        try {
//
//            if (contactComboBox.getSelectionModel().getSelectedItem().equals("U.S")) {
//                appointmentObservableList.clear();
//                appointmentObservableList.addAll(AppointmentDAO.getAppointmentByContactId(1));
//                appointmentTableView.setItems(appointmentObservableList);
//
//            } else if (countrySelector.getValue().equals("UK")) {
//                divisions = FirstLevelDivisionDAO.sortedDivisions(2);
//                for (FirstLevelDivision division : divisions) {
//                    firstLevelDivisions.add(division.getName());
//                }
//                stateSelector.setItems(firstLevelDivisions);
//            } else if (countrySelector.getValue().equals("Canada")) {
//                divisions = FirstLevelDivisionDAO.sortedDivisions(3);
//                for (FirstLevelDivision division : divisions) {
//                    firstLevelDivisions.add(division.getName());
//                }
//                stateSelector.setItems(firstLevelDivisions);
//            } else {
//                divisions = new ArrayList();
//            }
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
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

        apptIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentTableView.refresh();

//        try {
//            contactComboBox.setItems();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }


    }
}
