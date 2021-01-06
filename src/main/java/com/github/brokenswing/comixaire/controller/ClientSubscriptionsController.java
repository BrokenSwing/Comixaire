package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.facades.subscriptions.SubscriptionsFacade;
import com.github.brokenswing.comixaire.javafx.CustomListCell;
import com.github.brokenswing.comixaire.javafx.NoOpSelectionModel;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.Subscription;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ClientSubscriptionsController implements Initializable
{

    @ViewParam
    private Client client;

    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private ListView<Subscription> subscriptions;

    @InjectValue
    private SubscriptionsFacade subscriptionsFacade;
    @InjectValue
    private ViewLoader loader;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        subscriptions.setSelectionModel(new NoOpSelectionModel<>());
        subscriptions.setCellFactory(CustomListCell.factory(loader, Views.Cells.SUBSCRIPTION));

        dateFrom.setValue(LocalDate.now());
        dateTo.setValue(LocalDate.now().plusYears(1));
        try
        {
            subscriptions.setItems(new FilteredList<>(FXCollections.observableArrayList(subscriptionsFacade.findAllByCardId(client.getCardId()))));
        }
        catch (InternalException e)
        {
            e.printStackTrace();
            Alerts.exception(e);
        }
        catch (NoClientFoundException e)
        {
            Alerts.failure("The client with this card ID is not in our database.");
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
            Alerts.exception(e);
        }
    }

}
