package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.controller.util.ParametrizedController;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.exception.NoFineFoundException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.facades.fines.FinesFacade;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.view.*;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;


import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientFinesController implements ParametrizedController<Client>, Initializable
{
    private Client client;

    @FXML
    private Text fullname;
    @FXML
    private ListView finesList;

    @InjectValue
    private ClientsFacade clientsFacade;
    @InjectValue
    private FinesFacade fineFacade;
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
        try
        {
            finesList.setItems(new FilteredList<>(FXCollections.observableArrayList(fineFacade.findByClient(client))));
        }
        catch (InternalException | NoClientFoundException e)
        {
            e.printStackTrace();
        }
        catch (NoFineFoundException e)
        {
            //e.printStackTrace();
        }
    }



    public void back() { router.navigateTo(new ClientsView()); }

    public void infos()
    {
        router.navigateTo(new ClientDetailsView(), client);
    }

    public void update()
    {
        router.navigateTo(new ClientUpdateView(), client);
    }

    public void subscriptions() { router.navigateTo(new ClientSubscriptions(), client); }

    public void fines() { router.navigateTo(new ClientFines(), client); }

    public void delete()
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
