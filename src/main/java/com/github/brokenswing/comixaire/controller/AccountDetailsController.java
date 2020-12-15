package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.UsernameAlreadyExistsException;
import com.github.brokenswing.comixaire.facades.auth.AuthFacade;
import com.github.brokenswing.comixaire.facades.staff.StaffMemberFacade;
import com.github.brokenswing.comixaire.models.StaffMember;
import com.github.brokenswing.comixaire.view.InternalErrorAlert;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountDetailsController implements Initializable
{

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField passwordConfirmField;

    @FXML
    private Button changePasswordButton;

    @FXML
    private Button changeUsernameButton;

    @InjectValue
    private StaffMemberFacade staffMemberFacade;

    @InjectValue
    private AuthFacade authFacade;

    private boolean[] disableButtons()
    {
        boolean[] baseState = { changeUsernameButton.isDisable(), changePasswordButton.isDisable() };
        changeUsernameButton.setDisable(true);
        changePasswordButton.setDisable(true);
        return baseState;
    }

    private void enableButtons(boolean[] baseState)
    {
        changeUsernameButton.setDisable(baseState[0]);
        changePasswordButton.setDisable(baseState[1]);
    }

    public void updatePassword()
    {
        boolean[] buttonsState = disableButtons();

        String newPassword = passwordField.getText();
        StaffMember connectedStaff = new StaffMember(authFacade.getLoggedInStaff());
        connectedStaff.setPassword(authFacade.hashPassword(newPassword));
        try
        {
            staffMemberFacade.update(connectedStaff);
            displayPasswordUpdateSuccessAlert();
        }
        catch (InternalException e)
        {
            e.printStackTrace();
            displayInternalErrorAlert(e);
        }
        catch (UsernameAlreadyExistsException e)
        {
            displayUsernameExistsAlert();
        }

        enableButtons(buttonsState);
    }

    public void updateUsername()
    {
        boolean[] buttonsState = disableButtons();

        String newUsername = usernameField.getText().trim();
        StaffMember connectedStaff = new StaffMember(authFacade.getLoggedInStaff());
        connectedStaff.setUsername(newUsername);
        try
        {
            staffMemberFacade.update(connectedStaff);
            authFacade.setLoggedInStaff(connectedStaff);
            displayUsernameUpdateSuccessAlert();
        }
        catch (InternalException e)
        {
            e.printStackTrace();
            displayInternalErrorAlert(e);
        }
        catch (UsernameAlreadyExistsException e)
        {
            displayUsernameExistsAlert();
        }

        enableButtons(buttonsState);
    }

    protected void displayUsernameUpdateSuccessAlert()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("Your username was successfully updated.");
        alert.showAndWait();
    }

    protected void displayPasswordUpdateSuccessAlert()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("Your password was successfully updated.");
        alert.showAndWait();
    }

    protected void displayInternalErrorAlert(Exception e)
    {
        InternalErrorAlert alert = new InternalErrorAlert(e);
        alert.showAndWait();
    }

    protected void displayUsernameExistsAlert()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid username");
        alert.setContentText("The given username already exists.");
        alert.showAndWait();
    }

    private void checkPasswordsMatchAndChangeButtonState()
    {
        boolean passwordsEqual = this.passwordField.getText().equals(this.passwordConfirmField.getText());
        boolean passwordValid = !this.passwordField.getText().isEmpty();
        this.changePasswordButton.setDisable(!passwordsEqual || !passwordValid);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.usernameField.setText(this.authFacade.getLoggedInStaff().getUsername());
        this.usernameField.addEventHandler(InputEvent.ANY,
                e -> this.changeUsernameButton.setDisable(this.usernameField.getText().trim().isEmpty()));
        this.changePasswordButton.setDisable(true);
        this.passwordField.addEventHandler(InputEvent.ANY, e -> this.checkPasswordsMatchAndChangeButtonState());
        this.passwordConfirmField.addEventHandler(InputEvent.ANY, e -> this.checkPasswordsMatchAndChangeButtonState());
    }
}
