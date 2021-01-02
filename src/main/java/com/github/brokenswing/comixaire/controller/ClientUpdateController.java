package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.CardIdAlreadyExist;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.utils.FormValidationBuilder;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientUpdateController implements Initializable
{

    @ViewParam
    private Client client;

    @FXML
    private Text fullname;
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
        this.fullname.setText(client.getFullname());
        this.firstnameInput.setText(client.getFirstname());
        this.lastnameInput.setText(client.getLastname());
        this.addressInput.setText(client.getAddress());
        this.cardIdInput.setText(client.getCardId());
        this.genderInput.setValue(client.getGender());
        this.birthdateInput.setValue(new java.sql.Date(client.getBirthdate().getTime()).toLocalDate());

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
            router.navigateTo(Views.CLIENT_DETAILS, client);
        }
        catch (InternalException | CardIdAlreadyExist e)
        {
            e.printStackTrace();
        }
    }

    /**
     * TODO: NEEDS FACTORISATION IN A FUTURE
     */

    public void back()
    {
        router.navigateTo(Views.CLIENTS_LIST);
    }

    public void infos()
    {
        router.navigateTo(Views.CLIENT_DETAILS, client);
    }

    public void update()
    {
        router.navigateTo(Views.CLIENT_UPDATE, client);
    }

    public void subscriptions()
    {
        router.navigateTo(Views.CLIENT_SUBSCRIPTIONS, client);
    }

    public void fines()
    {
        router.navigateTo(Views.CLIENT_FINES, client);
    }

    public void delete()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure ?");
        alert.setHeaderText("Do you really want to delete the client: " + client.getFullname() + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            try
            {
                clientsFacade.delete(client);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Success");
                alert.setHeaderText("Client successfully removed from our database");
                alert2.showAndWait();
                router.navigateTo(Views.CLIENTS_LIST);
            }
            catch (InternalException e)
            {
                e.printStackTrace();
            }
        }
    }
}
