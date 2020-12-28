package com.github.brokenswing.comixaire.view;

import com.github.brokenswing.comixaire.controller.ClientDetailsController;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.view.util.ParametrizedView;

public class ClientDetailsView extends ParametrizedView<ClientDetailsController, Client>
{
    public ClientDetailsView()
    {
        super("client-details.fxml", ClientDetailsController.class);
    }
}
