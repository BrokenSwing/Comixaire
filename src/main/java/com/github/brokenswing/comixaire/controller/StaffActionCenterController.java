package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.fxml.FXML;

public class StaffActionCenterController
{

    @InjectValue
    private Router router;

    public StaffActionCenterController() {}

    @FXML
    protected void displayLoansView(){ router.navigateTo(Views.CLIENT_LOANS); }

    @FXML
    protected void displayNewItemView()
    {
        this.router.navigateTo(Views.LibraryItems.CREATION);
    }

    @FXML
    protected void displayReturnsView()
    {
        this.router.navigateTo(Views.CLIENT_RETURNS);
    }

    @FXML
    protected void displayBookingsView()
    {
        this.router.navigateTo(Views.CLIENT_BOOKINGS);
    }

    @FXML
    protected void displayNewClientView()
    {
        router.navigateTo(Views.NEW_CLIENT);
    }

}
