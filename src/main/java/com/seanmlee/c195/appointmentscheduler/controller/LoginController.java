package com.seanmlee.c195.appointmentscheduler.controller;

import com.seanmlee.c195.appointmentscheduler.Main;
import com.seanmlee.c195.appointmentscheduler.dao.UserDAO;
import com.seanmlee.c195.appointmentscheduler.model.User;
import com.seanmlee.c195.appointmentscheduler.util.UserLogger;
import com.seanmlee.c195.appointmentscheduler.util.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Handles the login screen and user validation
 * @author Sean lee
 */
public class LoginController implements Initializable {

    private final ObservableList<String> languagesInEnglish = FXCollections.observableArrayList("English", "French");
    private final ObservableList<String> languageNamesInFrench = FXCollections.observableArrayList("Anglais", "Français");

    @FXML private ComboBox<String> languageSelector;
    @FXML private Text languageLabel;
    @FXML
    private TextField userNameInputField;
    @FXML
    private TextField passwordInputField;
    @FXML
    private Text pageHeaderLabel;
    @FXML
    private Text userNameLabel;
    @FXML
    private Text passwordLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Button clearFormButton;
    @FXML
    private Text languageSettingLabel;
    @FXML
    private Text timeZoneLabel;

    private ResourceBundle rb;
    /**
     * This Method is called when the Login button is clicked.
     * It takes the user input from the username and password text fields, validates the user exists,
     * creates a session instance for that user and displays the welcome controller.
     * If the validation fails it displays an error message
     *
     * @param mouseEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onClickLoginButton(MouseEvent mouseEvent) throws SQLException, IOException {
        String username = userNameInputField.getText();
        String password = passwordInputField.getText();
        User validatedUser = UserDAO.validateUser(username, password);
        //Starting new UserSession instance
        if (validatedUser == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("invalidUserTitle"));
            alert.setContentText(rb.getString("invalidUserContent"));
            alert.setHeaderText(rb.getString("invalidUserHeader"));
            alert.showAndWait();
            UserLogger.stampInvalidUser(username, password);
            return;
        }
        UserSession.getInstance(validatedUser.getId(), validatedUser.getUserName());
        UserLogger.stampUserLogin(username);
        //Set new window
        Stage stage = (Stage) loginButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("welcome-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        WelcomeController welcomeController = fxmlLoader.getController();

        stage.setTitle("Appointment Management System - Welcome");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Clears the form
     * @param mouseEvent
     */
    public void onClickClearFormButton(MouseEvent mouseEvent) {
        userNameInputField.clear();
        passwordInputField.clear();
    }

    public void noUserFoundAlert() {

    }


    /**
     * Updates the language settings properly based on the resource bundle
     * @param language
     */
    public void updateLanguage(String language) {
        rb = ResourceBundle.getBundle("i18n/login", new Locale(language.equals("French") ? "fr" : "en"));

        userNameLabel.setText(rb.getString("userNameLabel"));
        passwordLabel.setText(rb.getString("passwordLabel"));
        languageSettingLabel.setText(rb.getString("languageSettingLabel"));
        loginButton.setText(rb.getString("loginButtonLabel"));
        clearFormButton.setText(rb.getString("clearFormButtonLabel"));
        ZoneId zoneId = ZoneId.systemDefault();
        timeZoneLabel.setText(rb.getString("timeZoneLabel") + " : " + zoneId);
        pageHeaderLabel.setText(rb.getString("pageHeaderLabel"));


    }

    /**
     * Sets the scene data properly
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        languageSelector.setItems(languagesInEnglish);
        Locale defaultLocale = Locale.getDefault();
        if (defaultLocale.getLanguage().equals("fr")){
            languageSelector.setValue("Français");
            updateLanguage("French");
        } else {
            languageSelector.setValue("English");
            updateLanguage("English");
        }

        languageSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateLanguage(newValue);
        });
    }


}
