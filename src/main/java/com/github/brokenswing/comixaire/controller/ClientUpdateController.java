package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.CardIdAlreadyExist;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.utils.FormValidationBuilder;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.alert.InternalErrorAlert;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class ClientUpdateController implements Initializable
{

    @ViewParam
    private Client client;

    @FXML
    private TextField firstnameInput;
    @FXML
    private TextField lastnameInput;
    @FXML
    private TextField addressInput;
    @FXML
    private TextField cardIdInput;
    @FXML
    private ChoiceBox<String> genderInput;
    @FXML
    private DatePicker birthdateInput;
    @FXML
    private Button updateProfileButton;

    @InjectValue
    private ClientsFacade clientsFacade;
    @InjectValue
    private Router router;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.firstnameInput.setText(client.getFirstname());
        this.lastnameInput.setText(client.getLastname());
        this.addressInput.setText(client.getAddress());
        this.cardIdInput.setText(client.getCardId());
        this.genderInput.setValue(client.getGender());
        this.birthdateInput.setValue(client.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        BooleanBinding isValid = new FormValidationBuilder()
                .notEmpty(firstnameInput.textProperty())
                .notEmpty(lastnameInput.textProperty())
                .notEmpty(addressInput.textProperty())
                .notEmpty(cardIdInput.textProperty())
                .add(Bindings.createBooleanBinding(
                        () -> birthdateInput.getValue() != null && birthdateInput.getValue().isBefore(LocalDate.now()),
                        birthdateInput.valueProperty()
                ))
                .notNull(genderInput.valueProperty())
                .build();
        updateProfileButton.disableProperty().bind(isValid.not());
    }

    public void updateProfile(ActionEvent actionEvent)
    {
        this.client.setFirstname(firstnameInput.getText());
        this.client.setLastname(lastnameInput.getText());
        this.client.setGender(genderInput.getValue());
        this.client.setAddress(addressInput.getText());
        this.client.setCardId(cardIdInput.getText());
        this.client.setBirthdate(java.sql.Date.valueOf(birthdateInput.getValue()));
        try
        {
            clientsFacade.update(client);
            router.navigateTo(Views.ClientManagement.MAIN_FRAME, client);
        }
        catch (InternalException e)
        {
            e.printStackTrace();
            new InternalErrorAlert(e).showAndWait();
        }
        catch (CardIdAlreadyExist e)
        {
            e.printStackTrace(); // TODO: Display error alert
        }
    }

}
