package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.javafx.NoOpSelectionModel;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.view.ClientCellView;
import com.github.brokenswing.comixaire.view.util.Router;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientsController implements Initializable
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

    @InjectValue
    private Router router;

    @FXML
    protected void find()
    {
        //Reset the list for each search
        this.clients = new FilteredList<>(FXCollections.observableArrayList());
        clientsList.setItems(this.clients);

        //Search
        try{
            if(!clientCardIdField.getText().trim().equals("")){
                this.clients = new FilteredList<>(FXCollections.observableArrayList(clientsFacade.findByCardId(clientCardIdField.getText().trim())));
            }
            else{
                if(!clientFirstnameField.getText().trim().equals("") && !clientLastnameField.getText().trim().equals("")){
                    this.clients = new FilteredList<>(FXCollections.observableArrayList(clientsFacade.findByName(clientFirstnameField.getText().trim(), clientLastnameField.getText().trim())));
                }
                else if(!clientFirstnameField.getText().trim().equals("")){
                    this.clients = new FilteredList<>(FXCollections.observableArrayList(clientsFacade.findByFirstname(clientFirstnameField.getText().trim())));
                }
                else if(!clientLastnameField.getText().trim().equals("")){
                    this.clients = new FilteredList<>(FXCollections.observableArrayList(clientsFacade.findByLastname(clientLastnameField.getText().trim())));
                }
                else{
                    this.clients = new FilteredList<>(FXCollections.observableArrayList(clientsFacade.findAll()));
                }
            }
            //Update the list
            clientsList.setItems(this.clients);
        }
        catch (InternalException e)
        {
            e.printStackTrace();
        }
        catch (NoClientFoundException e){
            System.out.println("Client not found");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            this.clientsList.setSelectionModel(new NoOpSelectionModel<>());
            this.clientsList.setCellFactory(lv -> new ClientListCell());
            this.clients = new FilteredList<>(FXCollections.observableArrayList(clientsFacade.findAll()));
            clientsList.setItems(this.clients);
        }
        catch (InternalException | NoClientFoundException e)
        {
            e.printStackTrace();
        }
    }

    private class ClientListCell extends ListCell<Client>
    {

        @Override
        protected void updateItem(Client item, boolean empty)
        {
            super.updateItem(item, empty);
            if (empty)
            {
                setText(null);
            }
            else
            {
                Node node = loader.loadView(new ClientCellView(), item);
                setGraphic(node);
            }
        }
    }
}
