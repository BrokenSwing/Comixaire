package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.view.ActionCenterView;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController
{

    /*
        Staff elements
     */
    @FXML
    private Button loginButtonStaff;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    /*
        Client elements
     */
    @FXML
    private Button loginButtonClient;
    @FXML
    private TextField clientIdField;

    public void loginStaff() throws IOException
    {
        loginButtonStaff.setDisable(true);
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.equals("admin") && password.equals("admin"))
        {
            Scene scene = loginButtonStaff.getScene();
            scene.setRoot(new ActionCenterView());
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Authentication error");
            alert.setHeaderText("Bad credentials");
            alert.setContentText("The username and/or the password you provided are invalid.");
            alert.showAndWait();
            loginButtonStaff.setDisable(false);
        }
    }

    public void loginClient() throws IOException
    {
        loginButtonClient.setDisable(true);
        String clientId = clientIdField.getText();
        if (clientId.equals("123456789"))
        {
            Scene scene = loginButtonClient.getScene();
            scene.setRoot(new ActionCenterView());
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Authentication error");
            alert.setHeaderText("Bad credentials");
            alert.setContentText("Unknown client ID.");
            alert.showAndWait();
            loginButtonClient.setDisable(false);
        }
    }

}
