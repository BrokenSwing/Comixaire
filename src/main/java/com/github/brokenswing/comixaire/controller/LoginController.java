package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.view.ActionCenterView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController
{

    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void login(ActionEvent event) throws IOException
    {
        loginButton.setDisable(true);
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.equals("admin") && password.equals("admin"))
        {
            Scene scene = loginButton.getScene();
            scene.setRoot(new ActionCenterView());
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Authentication error");
            alert.setHeaderText("Bad credentials");
            alert.setContentText("The username and/or the password you provided are invalid.");
            alert.showAndWait();
            loginButton.setDisable(false);
        }
    }

}
