package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.BadCredentialsException;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.auth.AuthFacade;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.alert.InternalErrorAlert;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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

    // TODO: Change field name to cardIdField (in class and FXML file) and change javadoc referencing this field
    @FXML
    private TextField clientIdField;

    @InjectValue
    private AuthFacade auth;
    @InjectValue
    private Router router;

    /**
     * This method tries to log in a staff member based on the values
     * that are present in the {@link #usernameField} and {@link #passwordField}.
     * Value of the username field will be trimmed before being used.
     * If the authentication fails, a call to either {@link Alerts#failure(String)}
     * or {@link #displayInternalErrorAlert(Exception)} will be displayed to
     * the user (based on the received exception).<br>
     * <p>
     * This method is called by a click event triggered by the
     * {@link #loginButtonStaff} button.
     */
    public void loginStaff()
    {
        loginButtonStaff.setDisable(true);
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        try
        {
            auth.loginStaff(username, password);
            displayActionCenter();
        }
        catch (InternalException e)
        {
            e.printStackTrace();
            Alerts.exception(e);
        }
        catch (BadCredentialsException e)
        {
            Alerts.failure("The username and/or the password you provided are invalid.");
        }
        finally
        {
            loginButtonStaff.setDisable(false);
        }
    }

    /**
     * Navigates to the staff action center view using the {@link #router}.
     */
    protected void displayActionCenter()
    {
        router.navigateTo(Views.ActionCenters.STAFF);
    }

    /**
     * Navigates to the client action center view using the {@link #router}.
     */
    protected void displayClientActionCenter()
    {
        router.navigateTo(Views.ActionCenters.CLIENT);
    }

    /**
     * Displays an alert popup to the user indicating an expected
     * error occurred.
     *
     * @param e the exception to show the detail of in the popup
     */
    protected void displayInternalErrorAlert(Exception e)
    {
        new InternalErrorAlert(e).showAndWait();
    }

    /**
     * This method tries to log in a client based on the value present in the {@link #clientIdField}.
     * Value of the client ID field will be trimmed before being used.
     * If the authentication fails, a call to either {@link Alerts#failure(String)}
     * or {@link #displayInternalErrorAlert(Exception)} will be displayed to
     * the user (based on the received exception).<br>
     * <p>
     * This method is called by a click event triggered by the
     * {@link #loginButtonStaff} button.
     */
    public void loginClient()
    {
        loginButtonClient.setDisable(true);
        String clientId = clientIdField.getText();

        try
        {
            auth.loginClient(clientId);
            displayClientActionCenter();
        }
        catch (BadCredentialsException e)
        {
            Alerts.failure("The username and/or the password you provided are invalid.");
        }
        catch (InternalException e)
        {
            e.printStackTrace();
            Alerts.exception(e);
        }
        finally
        {
            loginButtonClient.setDisable(false);
        }
    }

    @FXML
    private void handleKeyPressStaff(KeyEvent event)
    {
        if (event.getCode() == KeyCode.ENTER)
        {
            this.loginStaff();
        }
    }

    @FXML
    private void handleKeyPressClient(KeyEvent event)
    {
        if (event.getCode() == KeyCode.ENTER)
        {
            this.loginClient();
        }
    }
}
