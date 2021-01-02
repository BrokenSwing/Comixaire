package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.facades.auth.AuthFacade;
import com.github.brokenswing.comixaire.view.BorrowedItemsView;
import com.github.brokenswing.comixaire.view.RecommendationsView;
import com.github.brokenswing.comixaire.view.util.Router;

public class ClientActionCenterController
{
    @InjectValue
    private AuthFacade auth;
    @InjectValue
    private Router router;

    public void recommandations()
    {
        router.navigateTo(new RecommendationsView(), auth.getLoggedInClient());
    }

    public void borrowedItems()
    {
        router.navigateTo(new BorrowedItemsView(), auth.getLoggedInClient());
    }
}
