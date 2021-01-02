package com.github.brokenswing.comixaire.controller;


import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.view.ActionCenterView;
import com.github.brokenswing.comixaire.view.LoansView;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class LoansClientIdController
{
    @FXML
    public TextField loanClientIdField;

    @InjectValue
    private ClientsFacade clientsFacade;
    @InjectValue
    private Router router;

    public void back()
    {
        router.navigateTo(new ActionCenterView());
    }

    public void select()
    {
        try
        {
            Client client = clientsFacade.findByCardId(loanClientIdField.getText());
            router.navigateTo(new LoansView(), client);
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
}
