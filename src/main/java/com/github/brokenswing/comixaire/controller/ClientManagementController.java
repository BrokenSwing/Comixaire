package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.javafx.Alerts;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.Router;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class ClientManagementController implements Initializable
{

    @FXML
    private AnchorPane subPane;
    @FXML
    private Text fullName;

    @ViewParam
    private Client client;

    @InjectValue
    private Router router;
    @InjectValue
    private ViewLoader loader;

    @InjectValue
    private ClientsFacade clientsFacade;

    private void displayTab(String view)
    {
        this.subPane.getChildren().setAll(Collections.singleton(loader.loadView(view, client)));
    }

    public void back()
    {
        router.navigateTo(Views.CLIENTS_LIST);
    }

    public void displaySummaryTab()
    {
        displayTab(Views.ClientManagement.DETAILS_SUB_FRAME);
    }

    public void displayUpdateTab()
    {
        displayTab(Views.ClientManagement.UPDATE_SUB_FRAME);
    }

    public void displaySubscriptionsTab()
    {
        displayTab(Views.ClientManagement.SUBSCRIPTIONS_SUB_FRAME);
    }

    public void displayFinesTab()
    {
        displayTab(Views.ClientManagement.FINES_SUB_FRAME);
    }

    public void deleteClient()
    {
        boolean result = Alerts.confirm("Need confirmation", "Are you sure ?", "Do you really want to delete the client: " + client.getFullname() + " ?");
        if (result)
        {
            try
            {
                clientsFacade.delete(client);
                Alerts.success("Client successfully removed from our database");
                router.navigateTo(Views.CLIENTS_LIST);
            }
            catch (InternalException e)
            {
                e.printStackTrace();
                Alerts.exception(e);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.fullName.setText(client.getFullname());
        displaySummaryTab();
    }

}
