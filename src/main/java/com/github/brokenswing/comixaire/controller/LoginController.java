package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.auth.AuthFacade;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.BadCredentialsException;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.view.ActionCenterView;
import com.github.brokenswing.comixaire.view.InternalErrorAlert;
import com.github.brokenswing.comixaire.view.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

    // Even if this constructor looks like unused, it is necessary to this class to be constructed by the
    // controllers factory
    public LoginController()
    {

    }

    /**
     * This constructor should not be used outside of test classes.
     */
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

    /**
     * This method tries to log in a staff member based on the values
     * that are present in the {@link #usernameField} and {@link #passwordField}.
     * Value of the username field will be trimmed before being used.
     * If the authentication fails, a call to either {@link LoginController#displayBadCredentialsAlert(String)}
     * or {@link #displayInternalErrorAlert(Exception)} will be displayed to
     * the user (based on the received exception).<br />
     *
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

    /**
     *  Navigates to the action center view using the {@link #router}.
     */
    protected void displayActionCenter()
    {
        router.navigateTo(new ActionCenterView());
    }

    /**
     * Displays an alert popup to the user indicating the provided
     * credentials are invalid.
     *
     * @param contentMessage the detailed message to display to the user
     */
    protected void displayBadCredentialsAlert(String contentMessage)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Authentication error");
        alert.setHeaderText("Bad credentials");
        alert.setContentText(contentMessage);
        alert.showAndWait();
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
     * If the authentication fails, a call to either {@link LoginController#displayBadCredentialsAlert(String)}
     * or {@link #displayInternalErrorAlert(Exception)} will be displayed to
     * the user (based on the received exception).<br />
     *
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
            displayActionCenter();
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
