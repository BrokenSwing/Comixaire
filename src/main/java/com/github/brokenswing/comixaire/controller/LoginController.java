package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.auth.AuthFacade;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.BadCredentialsException;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.view.ActionCenterView;
import com.github.brokenswing.comixaire.view.InternalErrorAlert;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController
{

    /* Staff elements */
    @FXML
    private Button loginButtonStaff;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    /* Client elements */
    @FXML
    private Button loginButtonClient;
    @FXML
    private TextField clientIdField;

    @InjectValue
    private AuthFacade auth;

    public void loginStaff() throws IOException
    {
        loginButtonStaff.setDisable(true);
        String username = usernameField.getText();
        String password = passwordField.getText();

        try
        {
            auth.loginStaff(username, password);
            Scene scene = loginButtonStaff.getScene();
            scene.setRoot(new ActionCenterView());
            scene.getWindow().sizeToScene();
        }
        catch (InternalException e)
        {
            e.printStackTrace();

            Alert alert = new InternalErrorAlert(e);
            alert.showAndWait();
        }
        catch (BadCredentialsException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Authentication error");
            alert.setHeaderText("Bad credentials");
            alert.setContentText("The username and/or the password you provided are invalid.");
            alert.showAndWait();
        }
        finally
        {
            loginButtonStaff.setDisable(false);
        }
    }

    public void loginClient() throws IOException
    {
        loginButtonClient.setDisable(true);
        String clientId = clientIdField.getText();

        try
        {
            auth.loginClient(clientId);
            Scene scene = loginButtonClient.getScene();
            scene.setRoot(new ActionCenterView());
            scene.getWindow().sizeToScene();
        }
        catch (BadCredentialsException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Authentication error");
            alert.setHeaderText("Bad credentials");
            alert.setContentText("Unknown client ID.");
            alert.showAndWait();
        }
        catch (InternalException e)
        {
            e.printStackTrace();
            Alert alert = new InternalErrorAlert(e);
            alert.showAndWait();
        }
        finally
        {
            loginButtonClient.setDisable(false);
        }
    }

}
