package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.CardIdAlreadyExist;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.models.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class NewUserController
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

    public NewUserController() {}


    /**
     * This constructor should not be used outside of test classes.
     */
    public NewUserController(TextField firstname, TextField lastname, TextField idCard, DatePicker birthdate, Button createUserButton, ChoiceBox<String> genderField, TextField addressField){
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
                //TODO: return to action center and print success alert
                System.out.println("success");//temporary
            }
            catch (CardIdAlreadyExist e)
            {
                //TODO: display error alert
            }
            catch (InternalException e)
            {
                e.printStackTrace();
                //TODO: display error alert
            }
            finally
            {
                newUserButton.setDisable(false);
            }
        }
    }

}
