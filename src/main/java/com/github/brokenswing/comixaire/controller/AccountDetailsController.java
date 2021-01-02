package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.UsernameAlreadyExistsException;
import com.github.brokenswing.comixaire.facades.auth.AuthFacade;
import com.github.brokenswing.comixaire.facades.staff.StaffMemberFacade;
import com.github.brokenswing.comixaire.models.StaffMember;
import com.github.brokenswing.comixaire.utils.FormValidationBuilder;
import com.github.brokenswing.comixaire.view.alert.InternalErrorAlert;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountDetailsController implements Initializable
{

    private BooleanProperty committing = new SimpleBooleanProperty(false);

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
    @FXML
    private Text accountTypeText;

    @InjectValue
    private StaffMemberFacade staffMemberFacade;

    @InjectValue
    private AuthFacade authFacade;

    public void updatePassword()
    {
        committing.set(true);

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

        committing.set(false);
    }

    public void updateUsername()
    {
        committing.set(true);

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

        committing.set(false);
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

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.usernameField.setText(this.authFacade.getLoggedInStaff().getUsername());
        this.accountTypeText.setText(this.authFacade.getLoggedInStaff().getRole());

        BooleanBinding updateProfileFormValid = new FormValidationBuilder()
                .notEmpty(this.usernameField.textProperty())
                .add(committing.not())
                .build();

        BooleanBinding passwordUpdateFormValid = new FormValidationBuilder()
                .notEmpty(passwordField.textProperty(), false)
                .add(Bindings.createBooleanBinding(
                        () -> passwordField.textProperty().getValueSafe().equals(passwordConfirmField.getText()),
                        passwordField.textProperty(), passwordConfirmField.textProperty()
                ))
                .add(committing.not())
                .build();

        changeUsernameButton.disableProperty().bind(updateProfileFormValid.not());
        changePasswordButton.disableProperty().bind(passwordUpdateFormValid.not());
    }
}
