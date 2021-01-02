package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.facades.subscriptions.SubscriptionsFacade;
import com.github.brokenswing.comixaire.javafx.CustomListCell;
import com.github.brokenswing.comixaire.javafx.NoOpSelectionModel;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.Subscription;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.Router;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientSubscriptionsController implements Initializable
{

    @ViewParam
    private Client client;

    @FXML
    private Text fullname;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private ListView<Subscription> subscriptions;


    @InjectValue
    private SubscriptionsFacade subscriptionsFacade;
    @InjectValue
    private ClientsFacade clientsFacade;
    @InjectValue
    private Router router;
    @InjectValue
    private ViewLoader loader;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        subscriptions.setSelectionModel(new NoOpSelectionModel<>());
        subscriptions.setCellFactory(CustomListCell.factory(loader, Views.Cells.SUBSCRIPTION));

        fullname.setText(client.getFullname());
        dateFrom.setValue(LocalDate.now());
        dateTo.setValue(LocalDate.now().plusYears(1));
        try
        {
            subscriptions.setItems(new FilteredList<>(FXCollections.observableArrayList(subscriptionsFacade.findAllByCardId(client.getCardId()))));
        }
        catch (InternalException | NoClientFoundException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void createSubscription()
    {
        try
        {
            Subscription sub = new Subscription(java.sql.Date.valueOf(dateFrom.getValue()), java.sql.Date.valueOf(dateTo.getValue()), client);
            subscriptionsFacade.create(sub);
            subscriptions.setItems(new FilteredList<>(FXCollections.observableArrayList(subscriptionsFacade.findAllByCardId(client.getCardId()))));
        }
        catch (InternalException | NoClientFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * NEEDS FACTORISATION IN A FUTURE
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
