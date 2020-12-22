package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.facades.auth.AuthFacade;
import com.github.brokenswing.comixaire.view.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class StaffMenusController implements Initializable
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

    protected void displaySettingsView()
    {
        router.navigateTo(new SettingsView());
    }

    protected void displayLogsView()
    {
        router.navigateTo(new LogsView());
    }

    private void displayActionCenterView() { router.navigateTo(new ActionCenterView()); }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.logoutButton.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> this.logout());
        this.settingsButton.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> this.displaySettingsView());
        this.logsButton.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> this.displayLogsView());
        this.actionCenterButton.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> this.displayActionCenterView());
    }

}
