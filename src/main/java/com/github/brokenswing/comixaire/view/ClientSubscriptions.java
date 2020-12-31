package com.github.brokenswing.comixaire.view;

import com.github.brokenswing.comixaire.controller.ClientSubscriptionsController;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.view.util.ParametrizedView;

public class ClientSubscriptions extends ParametrizedView<ClientSubscriptionsController, Client>
{
    public ClientSubscriptions()
    {
        super("client-subscriptions.fxml", ClientSubscriptionsController.class);
    }
}
