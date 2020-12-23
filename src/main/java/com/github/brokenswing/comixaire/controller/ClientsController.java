package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.view.Router;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ClientsController
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
            }
            //Update the list
            clientsList.setItems(this.clients);
        }
        catch (InternalException e)
        {
            e.printStackTrace();
        }
        catch (NoClientFoundException e)
        {
            displayNoClientFoundAlert(e.getMessage());
        }
    }

    protected void displayNoClientFoundAlert(String contentMessage)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("No client found");
        alert.setHeaderText("Your search did not yield anything");
        alert.setContentText(contentMessage);
        alert.showAndWait();
    }

}
