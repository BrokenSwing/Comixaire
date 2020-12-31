package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.facades.auth.AuthFacade;
import com.github.brokenswing.comixaire.view.LoginView;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable
{
    @FXML
    private Text clientID;

    @InjectValue
    private AuthFacade auth;
    @InjectValue
    private Router router;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        clientID.setText("Client ID: " + auth.getLoggedInClient().getCardId());
    }

    public void logout()
    {
        auth.logout();
        router.navigateTo(new LoginView());
    }
}
