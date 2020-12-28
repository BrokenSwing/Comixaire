package com.github.brokenswing.comixaire.view;

import com.github.brokenswing.comixaire.controller.cell.ClientCellController;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.view.util.ParametrizedView;

public class ClientCellView extends ParametrizedView<ClientCellController, Client>
{
    public ClientCellView()
    {
        super("client-cell-view.fxml", ClientCellController.class);
    }
}
