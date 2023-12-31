package com.seanmlee.c195.appointmentscheduler.controller;

import com.seanmlee.c195.appointmentscheduler.Main;
import com.seanmlee.c195.appointmentscheduler.dao.AppointmentDAO;
import com.seanmlee.c195.appointmentscheduler.dao.CustomerDAO;
import com.seanmlee.c195.appointmentscheduler.model.Appointment;
import com.seanmlee.c195.appointmentscheduler.model.Customer;
import com.seanmlee.c195.appointmentscheduler.util.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainDashboardController implements Initializable {

    private final ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
    private final ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
    @FXML private TableColumn apptCustomerId;
    @FXML
    private Button reportsButton;
    @FXML
    private Button signOutButton;
    @FXML
    private ToggleGroup viewGroup;
    @FXML
    private RadioButton allAppointmentsView;
    @FXML
    private RadioButton currentMonthView;
    @FXML
    private RadioButton currentWeekView;
    @FXML
    private Button addApptButton;
    @FXML
    private Button updateApptButton;
    @FXML
    private Button deleteApptButton;
    @FXML
    private Button addCustomerButton;
    @FXML
    private Button updateCustomerButton;
    @FXML
    private Button deleteCustomerButton;
    @FXML
    private TableView<Appointment> appointmentTableView;
    @FXML
    private TableColumn<Appointment, Long> apptIdColumn;
    @FXML
    private TableColumn<Appointment, String> apptTitleColumn;
    @FXML
    private TableColumn<Appointment, String> apptTypeColumn;
    @FXML
    private TableColumn<Appointment, String> apptDescriptionColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> apptStartColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> apptEndColumn;
    @FXML
    private TableColumn<Appointment, String> apptLocationColumn;
    @FXML
    private TableColumn apptCustomerColumn;
    @FXML
    private TableColumn<Appointment, String> apptCreatedBy;
    @FXML
    private TableColumn<Appointment, String> apptUpdatedBy;
    @FXML
    private TableColumn<Appointment, LocalDateTime> apptLastUpdated;
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn customerIdColumn;
    @FXML
    private TableColumn customerNameColumn;
    @FXML
    private TableColumn customerAddressColumn;
    @FXML
    private TableColumn customerPhoneColumn;
    @FXML
    private TableColumn customerStateColumn;
    @FXML
    private TableColumn customerPostalCodeColumn;

    /**
     * Clears and sets the Appointment observable list
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
     * Clears and sets the Customer observable list and table
     *
     * @param customer
     */
    public void refreshCustomerTable(List<Customer> customer) {

        customerObservableList.clear();
        customerObservableList.addAll(customer);
        customerTableView.setItems(customerObservableList);
        customerTableView.refresh();
    }

    public void onAddApptButtonClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) addApptButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("add-appointment-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        AddAppointmentController addAppointmentController = fxmlLoader.getController();
        stage.setTitle("Appointment Management System - Add an Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void onUpdateApptButtonClick(ActionEvent actionEvent) throws IOException {
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        try {
            Stage stage = (Stage) updateApptButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("update-appointment-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            UpdateAppointmentController updateAppointmentController = fxmlLoader.getController();
            updateAppointmentController.setAppointmentData(selectedAppointment);
            stage.setTitle("Appointment Management System - Update an Appointment");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred. Check your selection and try again.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }

    public void onDeleteApptButtonClick(ActionEvent actionEvent) throws SQLException {
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Action");
            alert.setHeaderText("Are You Sure?");
            alert.setContentText("Once you delete an appointment this action can't be undone!");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                long selectedId = selectedAppointment.getId();
                System.out.println(selectedId);
                AppointmentDAO.deleteAppointment(selectedId);
                appointmentTableView.getItems().remove(selectedAppointment);
                appointmentTableView.refresh();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred. Check your selection and try again.");
            alert.setContentText("error");
            alert.showAndWait();
        }
    }

    public void onAddCustClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) addCustomerButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("add-customer-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        AddCustomerController addCustomerController = fxmlLoader.getController();
        stage.setTitle("Appointment Management System - Add A New Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void onUpdateCustClick(ActionEvent actionEvent) throws IOException, SQLException {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        try {
            Stage stage = (Stage) updateApptButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("update-customer-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            UpdateCustomerController updateCustomerController = fxmlLoader.getController();
            updateCustomerController.setCustomerData(selectedCustomer);

            stage.setTitle("Appointment Management System - Update a Customer");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred. Check your selection and try again.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Handles deleting customer from the database and refreshing the table
     *
     * @param actionEvent
     * @throws SQLException
     */
    public void onDeleteCustClick(ActionEvent actionEvent) throws SQLException {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        try {
            long selectedId = selectedCustomer.getId();
            List<Appointment> customerAppointments = AppointmentDAO.getAppointmentsByCustomerID(selectedId);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Action");
            alert.setHeaderText("Are You Sure?");
            alert.setContentText("Once you delete a customer this action can't be undone! All Customers appointments will be deleted");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                CustomerDAO.deleteCustomer(selectedId);
                customerTableView.getItems().remove(selectedCustomer);
                customerTableView.refresh();
                for (Appointment appointment : customerAppointments) {
                    AppointmentDAO.deleteAppointment(appointment.getId());
                }
                refreshAppointmentTable(AppointmentDAO.getAppointments(UserSession.getInstance().getUserId()));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred. Check your selection and try again.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Signs out of the current user and launches the login screen again.
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

    public void onAllApptSelect(ActionEvent actionEvent) {
    }

    public void onCurrentMonthSelect(ActionEvent actionEvent) throws SQLException {


    }

    public void onCurrentWeekSelect(ActionEvent actionEvent) {
    }

    public void filterByMonth(ObservableList<Appointment> appointments) throws SQLException {
    }

    /**
     * Handles the filtering of the appointments by week, month or all.
     *
     * @throws SQLException
     */
    public void filterAppointments() throws SQLException {
        long userId = UserSession.getInstance().getUserId();
        if (currentMonthView.isSelected()) {
            List<Appointment> nextMonthsAppointments = AppointmentDAO.getAppointmentsByMonth(userId);
            refreshAppointmentTable(nextMonthsAppointments);
        } else if (currentWeekView.isSelected()) {
            List<Appointment> nextWeeksAppointments = AppointmentDAO.getAppointmentsByWeek(userId);
            refreshAppointmentTable(nextWeeksAppointments);
        } else if (allAppointmentsView.isSelected()) {
            List<Appointment> allAppointments = AppointmentDAO.getAppointments(userId);
            refreshAppointmentTable(allAppointments);
        }
    }


    /**
     * Associates the table with the proper object and attributes
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale locale = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("i18n/login", Locale.getDefault());

        //Sets up Appointment Table
        apptIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptCreatedBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        apptUpdatedBy.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        apptLastUpdated.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        apptCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentTableView.refresh();

        //Sets up Customer Table
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerStateColumn.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerTableView.refresh();

        viewGroup.selectedToggleProperty().addListener((observableValue, o, t1) -> {
            try {
                filterAppointments();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


    }


    public void reportsClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) reportsButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("reports-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);


        stage.setTitle("Appointment Management System - Your Appointments");
        stage.setScene(scene);
        stage.show();
    }
}
