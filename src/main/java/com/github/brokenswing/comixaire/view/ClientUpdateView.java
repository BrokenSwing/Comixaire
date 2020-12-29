package com.github.brokenswing.comixaire.view;

import com.github.brokenswing.comixaire.controller.ClientUpdateController;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.view.util.ParametrizedView;

public class ClientUpdateView extends ParametrizedView<ClientUpdateController, Client>
{
    public ClientUpdateView()
    {
        super("client-update.fxml", ClientUpdateController.class);
    }
}
