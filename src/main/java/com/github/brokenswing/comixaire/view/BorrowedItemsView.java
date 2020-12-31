package com.github.brokenswing.comixaire.view;

import com.github.brokenswing.comixaire.controller.ClientBorrowedItemsController;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.view.util.ParametrizedView;

public class BorrowedItemsView extends ParametrizedView<ClientBorrowedItemsController, Client>
{
    public BorrowedItemsView()
    {
        super("user-borrowed-items.fxml", ClientBorrowedItemsController.class);
    }
}
