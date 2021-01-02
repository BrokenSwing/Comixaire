package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.Router;

public class ClientBorrowedItemsController
{

    @ViewParam
    private Client client;

    @InjectValue
    private Router router;

    public void back()
    {
        router.navigateTo(Views.CLIENT_ACTION_CENTER);
    }

    public void search()
    {
        //TODO: filter borrowed items
    }

}
