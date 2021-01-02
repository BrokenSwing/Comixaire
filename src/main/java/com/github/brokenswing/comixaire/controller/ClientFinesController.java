package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.facades.fines.FinesFacade;
import com.github.brokenswing.comixaire.javafx.CustomListCell;
import com.github.brokenswing.comixaire.javafx.NoOpSelectionModel;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.Fine;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.Router;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
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

public class ClientFinesController implements Initializable
{

    @ViewParam
    private Client client;

    @FXML
    private Text fullname;
    @FXML
    private ListView<Fine> finesList;

    @InjectValue
    private ClientsFacade clientsFacade;
    @InjectValue
    private FinesFacade fineFacade;
    @InjectValue
    private Router router;
    @InjectValue
    private ViewLoader loader;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        fullname.setText(client.getFullname());
        finesList.setSelectionModel(new NoOpSelectionModel<>());
        finesList.setCellFactory(CustomListCell.factory(loader, Views.Cells.FINE));
        try
        {
            finesList.setItems(new FilteredList<>(FXCollections.observableArrayList(fineFacade.findByClient(client))));

        }
        catch (InternalException | NoClientFoundException e)
        {
            e.printStackTrace();
        }
    }


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
                alert2.setHeaderText("Client successfully removed from our database");
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
