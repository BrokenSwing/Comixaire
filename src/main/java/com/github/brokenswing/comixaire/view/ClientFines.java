package com.github.brokenswing.comixaire.view;

import com.github.brokenswing.comixaire.controller.ClientFinesController;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.view.util.ParametrizedView;

public class ClientFines extends ParametrizedView<ClientFinesController, Client>
{
    public ClientFines()
    {
        super("client-fines.fxml", ClientFinesController.class);
    }
}
