package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.controller.util.ParametrizedController;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.CardIdAlreadyExist;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.utils.FormValidationBuilder;
import com.github.brokenswing.comixaire.utils.PrettyTimeTransformer;
import com.github.brokenswing.comixaire.view.ClientDetailsView;
import com.github.brokenswing.comixaire.view.ClientUpdateView;
import com.github.brokenswing.comixaire.view.ClientsView;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.awt.event.ActionListener;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientUpdateController implements ParametrizedController<Client>, Initializable
{
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
    public void handleViewParam(Client client)
    {
        this.client = client;
    }

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
                        ()-> birthdateInput.getValue() != null && birthdateInput.getValue().isBefore(LocalDate.now()),
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
            System.out.println(client.getSubscriptionId());//Error with subscriptionId
            clientsFacade.update(client);
            //TODO: redirect to infos view
            System.out.println("Ok!");
        }
        catch (InternalException e)
        {
            e.printStackTrace();
        }
        catch (CardIdAlreadyExist cardIdAlreadyExist)
        {
            cardIdAlreadyExist.printStackTrace();
        }
    }

    /**
     *  NEEDS FACTORISATION IN A FUTURE
     */

    public void infos(ActionEvent actionEvent)
    {
        router.navigateTo(new ClientDetailsView(), client);
    }

    public void update(ActionEvent actionEvent)
    {
        router.navigateTo(new ClientUpdateView(), client);
    }

    public void fines(ActionEvent actionEvent)
    {
        //TODO: new view to consult fines
    }

    public void subscriptions(ActionEvent actionEvent)
    {
        //TODO: new view to consult subscription
    }

    public void delete(ActionEvent actionEvent)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure ?");
        alert.setHeaderText("Do you really want to delete the client: " + client.getFullname() + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            try
            {
                clientsFacade.delete(client);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Success");
                alert.setHeaderText("Client successfully removed from our database");
                alert2.showAndWait();
                router.navigateTo(new ClientsView());
            }
            catch (InternalException e)
            {
                e.printStackTrace();
            }
        }
    }
}
