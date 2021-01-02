package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.controller.util.ParametrizedController;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.view.ClientActionCenterView;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientBorrowedItemsController implements ParametrizedController<Client>, Initializable
{
    private Client client;

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
        //TODO: implement
    }

    public void back() { router.navigateTo(new ClientActionCenterView()); }

    public void search()
    {
        //TODO: filter borrowed items
    }
}
