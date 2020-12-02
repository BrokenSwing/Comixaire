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

    public LoginController()
    {

    }

    public LoginController(Button loginButtonStaff, TextField usernameField, PasswordField passwordField,
                           Button loginButtonClient, TextField clientIdField, AuthFacade auth)
    {
        this.loginButtonStaff = loginButtonStaff;
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.loginButtonClient = loginButtonClient;
        this.clientIdField = clientIdField;
        this.auth = auth;
    }

    public void loginStaff() throws IOException
    {
        loginButtonStaff.setDisable(true);
        String username = usernameField.getText();
        String password = passwordField.getText();

        try
        {
            auth.loginStaff(username, password);
            displayActionCenter(loginButtonStaff);
        }
        catch (InternalException e)
        {
            e.printStackTrace();

            displayInternalErrorAlert(e);
        }
        catch (BadCredentialsException e)
        {
            displayBadCredentialsAlert("The username and/or the password you provided are invalid.");
        }
        finally
        {
            loginButtonStaff.setDisable(false);
        }
    }

    protected void displayActionCenter(Button loginButtonStaff) throws IOException
    {
        Scene scene = loginButtonStaff.getScene();
        scene.setRoot(new ActionCenterView());
        scene.getWindow().sizeToScene();
    }

    protected void displayBadCredentialsAlert(String contentMessage)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Authentication error");
        alert.setHeaderText("Bad credentials");
        alert.setContentText(contentMessage);
        alert.showAndWait();
    }

    protected void displayInternalErrorAlert(Exception e)
    {
        new InternalErrorAlert(e).showAndWait();
    }

    public void loginClient() throws IOException
    {
        loginButtonClient.setDisable(true);
        String clientId = clientIdField.getText();

        try
        {
            auth.loginClient(clientId);
            displayActionCenter(loginButtonClient);
        }
        catch (BadCredentialsException e)
        {
            displayBadCredentialsAlert("Unknown client ID.");
        }
        catch (InternalException e)
        {
            e.printStackTrace();

            displayInternalErrorAlert(e);
        }
        finally
        {
            loginButtonClient.setDisable(false);
        }
    }

}
