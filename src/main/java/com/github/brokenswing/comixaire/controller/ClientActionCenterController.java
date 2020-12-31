package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.facades.auth.AuthFacade;
import com.github.brokenswing.comixaire.view.BorrowedItemsView;
import com.github.brokenswing.comixaire.view.util.Router;

public class ClientActionCenterController
{
    @InjectValue
    private AuthFacade auth;
    @InjectValue
    private Router router;

    public void recommandations()
    {
        //TODO: navigate to recommandations
        //router.navigateTo(new RecommandationsView(), auth.getLoggedInClient());
    }

    public void borrowedItems()
    {
        router.navigateTo(new BorrowedItemsView(), auth.getLoggedInClient());
    }
}
