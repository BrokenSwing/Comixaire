package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.facades.auth.AuthFacade;
import com.github.brokenswing.comixaire.view.*;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.fxml.FXML;

public class StaffMenusController
{

    @InjectValue
    private AuthFacade auth;

    @InjectValue
    private Router router;

    public void logout()
    {
        auth.logout();
        displayLoginView();
    }

    protected void displayLoginView()
    {
        router.navigateTo(Views.LOGIN);
    }

    @FXML
    protected void displaySettingsView()
    {
        router.navigateTo(Views.SETTINGS);
    }

    @FXML
    protected void displayLogsView()
    {
        router.navigateTo(Views.LOGS);
    }

    @FXML
    protected void displayItemsView() { router.navigateTo(Views.LIBRARY_ITEMS_LIST); }

    @FXML
    protected void displayStatsView()
    {
        //router.navigateTo(new StatsView());
    }

    @FXML
    private void displayActionCenterView() { router.navigateTo(Views.ACTION_CENTER); }

    @FXML
    private void displayClientsView() { router.navigateTo(Views.CLIENTS_LIST); }

}
