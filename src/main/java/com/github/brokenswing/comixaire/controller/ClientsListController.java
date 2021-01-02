package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.javafx.CustomListCell;
import com.github.brokenswing.comixaire.javafx.NoOpSelectionModel;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ClientsListController implements Initializable
{

    private FilteredList<Client> clients = new FilteredList<>(FXCollections.observableArrayList());

    @FXML
    private TextField clientCardIdField;
    @FXML
    private TextField clientFirstnameField;
    @FXML
    private TextField clientLastnameField;
    @FXML
    private ListView<Client> clientsList;

    @InjectValue
    private ClientsFacade clientsFacade;

    @InjectValue
    private ViewLoader loader;

    private static boolean notEmpty(TextField field)
    {
        return field.getText() != null && !field.getText().trim().isEmpty();
    }

    @FXML
    protected void search()
    {
        Predicate<Client> predicate = client -> true;

        if (notEmpty(clientCardIdField))
        {
            predicate = predicate.and(client -> client.getCardId().toLowerCase().startsWith(clientCardIdField.getText().toLowerCase()));
        }

        if (notEmpty(clientFirstnameField))
        {
            predicate = predicate.and(client -> client.getFirstname().toLowerCase().contains(clientFirstnameField.getText().trim().toLowerCase()));
        }

        if (notEmpty(clientLastnameField))
        {
            predicate = predicate.and(client -> client.getLastname().toLowerCase().contains(clientLastnameField.getText().trim().toLowerCase()));
        }

        clients.setPredicate(predicate);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.clientsList.setSelectionModel(new NoOpSelectionModel<>());
        this.clientsList.setCellFactory(CustomListCell.factory(loader, Views.Cells.CLIENT));

        try
        {
            this.clients = new FilteredList<>(FXCollections.observableArrayList(clientsFacade.findAll()));
            clientsList.setItems(this.clients);
        }
        catch (InternalException e)
        {
            e.printStackTrace(); // TODO: Redirect to an "error page"
        }
    }

}
