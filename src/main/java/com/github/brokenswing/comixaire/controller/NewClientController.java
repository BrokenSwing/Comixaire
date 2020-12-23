package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.CardIdAlreadyExist;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.view.ActionCenterView;
import com.github.brokenswing.comixaire.view.InternalErrorAlert;
import com.github.brokenswing.comixaire.view.Router;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class NewClientController
{

    @FXML
    private Button newUserButton;
    @FXML
    private TextField newUserFirstnameField;
    @FXML
    private TextField newUserLastnameField;
    @FXML
    private TextField newUserCardIdField;
    @FXML
    private DatePicker newUserBirthdateField;
    @FXML
    private TextField newUserAddressField;
    @FXML
    private ChoiceBox<String> newUserGenderField;

    @InjectValue
    private ClientsFacade clientsFacade;
    @InjectValue
    private Router router;

    public NewClientController() {}


    /**
     * This constructor should not be used outside of test classes.
     */
    public NewClientController(TextField firstname, TextField lastname, TextField idCard, DatePicker birthdate, Button createUserButton, ChoiceBox<String> genderField, TextField addressField){
        this.newUserButton = createUserButton;
        this.newUserFirstnameField = firstname;
        this.newUserLastnameField = lastname;
        this.newUserCardIdField = idCard;
        this.newUserBirthdateField = birthdate;
        this.newUserGenderField = genderField;
        this.newUserAddressField = addressField;
    }

    @FXML
    private void createClient()
    {
        String firstname = newUserFirstnameField.getText();
        String lastname = newUserLastnameField.getText();
        String idCard = newUserCardIdField.getText();
        String address = newUserAddressField.getText();
        String gender = newUserGenderField.getValue();
        LocalDate localDate = newUserBirthdateField.getValue();

        if(localDate != null && !firstname.equals("") && !lastname.equals("") && !idCard.equals("") && !address.equals("") && (gender.equals("Homme") || gender.equals("Femme"))){
            newUserButton.setDisable(true);

            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date birthdate = Date.from(instant);

            try
            {
                clientsFacade.create(new Client(firstname, lastname, idCard, gender, address, birthdate));
                displayClientCreatedAlert("Welcome to " + firstname + " " + lastname + " our new client !");
                displayActionCenter();
            }
            catch (CardIdAlreadyExist e)
            {
                displayCardIdAlreadyUsedAlert("The card id provided is already use by another client.");
            }
            catch (InternalException e)
            {
                displayInternalErrorAlert(e);
            }
            finally
            {
                newUserButton.setDisable(false);
            }
        }
    }

    protected void displayCardIdAlreadyUsedAlert(String contentMessage)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Client creation error");
        alert.setHeaderText("Card id already in use");
        alert.setContentText(contentMessage);
        alert.showAndWait();
    }

    protected void displayClientCreatedAlert(String contentMessage)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Client successfully created");
        alert.setHeaderText("New client join us !");
        alert.setContentText(contentMessage);
        alert.showAndWait();
    }

    protected void displayInternalErrorAlert(Exception e)
    {
        new InternalErrorAlert(e).showAndWait();
    }

    protected void displayActionCenter()
    {
        router.navigateTo(new ActionCenterView());
    }
}
