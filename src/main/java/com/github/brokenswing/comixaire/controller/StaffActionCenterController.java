package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.view.NewClientView;
import com.github.brokenswing.comixaire.view.Router;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class StaffActionCenterController implements Initializable
{
    @FXML
    private ImageView newUserButton;
    @FXML
    private ImageView bookingsButton;
    @FXML
    private ImageView returnsButton;
    @FXML
    private ImageView newItemButton;
    @FXML
    private ImageView loansButton;

    @InjectValue
    private Router router;

    public StaffActionCenterController() {}

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.newUserButton.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> this.displayNewClientView());
        this.bookingsButton.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> this.displayBookingsView());
        this.returnsButton.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> this.displayReturnsView());
        this.newItemButton.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> this.displayNewItemView());
        this.loansButton.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> this.displayLoansView());
    }

    protected void displayLoansView()
    {
        //TODO: implement
    }

    protected void displayNewItemView()
    {
        //TODO: implement
    }

    protected void displayReturnsView()
    {
        //TODO: implement
    }

    protected void displayBookingsView()
    {
        //TODO: implement
    }

    protected void displayNewClientView()
    {
        router.navigateTo(new NewClientView());
    }
}
