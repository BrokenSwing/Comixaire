package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.facades.item.LibraryItemFacade;
import com.github.brokenswing.comixaire.facades.loans.LoansFacade;
import com.github.brokenswing.comixaire.javafx.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class StatsController implements Initializable
{
    @FXML
    private Text libraryItemsCounter;
    @FXML
    private Text clientsCounter;
    @FXML
    private Text loansCounter;

    @InjectValue
    private LibraryItemFacade libraryItemFacade;
    @InjectValue
    private ClientsFacade clientsFacade;
    @InjectValue
    private LoansFacade loansFacade;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            libraryItemsCounter.setText(Integer.toString(libraryItemFacade.countAll()));
            clientsCounter.setText(Integer.toString(clientsFacade.countAll()));
            loansCounter.setText(Integer.toString(loansFacade.countAll()));
        }
        catch (InternalException e)
        {
            e.printStackTrace();
            Alerts.exception(e);
        }
    }
}
