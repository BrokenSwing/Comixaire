package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.view.NewClientView;
import com.github.brokenswing.comixaire.view.item.NewLibraryItemView;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class StaffActionCenterController
{

    @InjectValue
    private Router router;

    public StaffActionCenterController() {}

    @FXML
    protected void displayLoansView()
    {
        //TODO: implement
    }

    @FXML
    protected void displayNewItemView()
    {
        this.router.navigateTo(new NewLibraryItemView());
    }

    @FXML
    protected void displayReturnsView()
    {
        //TODO: implement
    }

    @FXML
    protected void displayBookingsView()
    {
        //TODO: implement
    }

    @FXML
    protected void displayNewClientView()
    {
        router.navigateTo(new NewClientView());
    }

}
