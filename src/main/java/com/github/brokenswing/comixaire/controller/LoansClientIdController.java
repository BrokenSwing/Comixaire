package com.github.brokenswing.comixaire.controller;


import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.utils.FormValidationBuilder;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class LoansClientIdController implements Initializable
{
    @FXML
    public TextField loanClientIdField;
    @FXML
    public Button loanClientIdButton;

    @InjectValue
    private ClientsFacade clientsFacade;
    @InjectValue
    private Router router;

    public void back()
    {
        router.navigateTo(Views.ActionCenters.STAFF);
    }

    public void select()
    {
        try
        {
            Client client = clientsFacade.findByCardId(loanClientIdField.getText());
            router.navigateTo(Views.LOANS, client);
        }
        catch (InternalException e)
        {
            e.printStackTrace();
        }
        catch (NoClientFoundException e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Client not found");
            alert.setHeaderText("The client with this card is not in our database");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        BooleanBinding isValid = new FormValidationBuilder()
                .notEmpty(loanClientIdField.textProperty())
                .build();
        loanClientIdButton.disableProperty().bind(isValid.not());
    }
}
