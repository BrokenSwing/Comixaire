package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.facades.auth.AuthFacade;
import com.github.brokenswing.comixaire.view.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class StaffMenusController
{

    @FXML
    private ImageView logoutButton;

    @FXML
    private ImageView settingsButton;

    @FXML
    private ImageView logsButton;

    @FXML
    private ImageView clientsButton;

    @FXML
    private ImageView libraryItemsButton;

    @FXML
    private ImageView actionCenterButton;

    @FXML
    private ImageView statsButton;

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
        router.navigateTo(new LoginView());
    }

    @FXML
    protected void displaySettingsView()
    {
        router.navigateTo(new SettingsView());
    }

    @FXML
    protected void displayLogsView()
    {
        router.navigateTo(new LogsView());
    }

    @FXML
    protected void displayItemsView() { router.navigateTo(new ItemsView()); }

    @FXML
    protected void displayStatsView()
    {
        //router.navigateTo(new StatsView());
    }

    @FXML
    private void displayActionCenterView() { router.navigateTo(new ActionCenterView()); }

    @FXML
    private void displayClientsView() { router.navigateTo(new ClientsView()); }

}
