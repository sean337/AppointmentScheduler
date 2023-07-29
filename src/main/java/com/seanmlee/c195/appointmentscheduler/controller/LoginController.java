package com.seanmlee.c195.appointmentscheduler.controller;

import com.seanmlee.c195.appointmentscheduler.Main;
import com.seanmlee.c195.appointmentscheduler.dao.UserDAO;
import com.seanmlee.c195.appointmentscheduler.model.User;
import com.seanmlee.c195.appointmentscheduler.util.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
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
    @FXML
    private ChoiceBox<String> languageSelectorBox;
    private final String[] languages = {"English", "French"};


    /**
     * This Method is called when the Login button is clicked.
     *  It takes the user input from the username and password text fields, validates the user exists,
     *  creates a session instance for that user and displays the welcome controller.
     *  If the validation fails it displays an error message
     *
     * @param mouseEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onClickLoginButton(MouseEvent mouseEvent) throws SQLException, IOException {
        String username = userNameInputField.getText();
        String password = passwordInputField.getText();
        User validatedUser = UserDAO.validateUser(username,password);
        //Starting new UserSession instance
        if (validatedUser == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid User");
            alert.setContentText("This username and password can't be found");
            alert.showAndWait();
            return;
        }
        UserSession.getInstance(validatedUser.getId(), validatedUser.getUserName());
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

    public void onClickClearFormButton(MouseEvent mouseEvent) {
        userNameInputField.clear();
        passwordInputField.clear();
    }


    public void updateLanguage(ResourceBundle resourceBundle) {
        if (languageSelectorBox.valueProperty().equals("French")){
            System.out.println("French!");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Locale locale = Locale.getDefault();

        rb = ResourceBundle.getBundle("i18n/login", Locale.getDefault());
        languageSelectorBox.getItems().addAll(languages);
        languageSelectorBox.setValue("English");

        userNameLabel.setText(rb.getString("userNameLabel"));
        passwordLabel.setText(rb.getString("passwordLabel"));
        languageSettingLabel.setText(rb.getString("languageSettingLabel"));
        loginButton.setText(rb.getString("loginButtonLabel"));
        clearFormButton.setText(rb.getString("clearFormButtonLabel"));
        ZoneId zoneId = ZoneId.systemDefault();
        timeZoneLabel.setText("Time Zone: " + zoneId.toString());
        //updateLanguage(rb);
    }


}
