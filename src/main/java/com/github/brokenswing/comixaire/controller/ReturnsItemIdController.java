package com.github.brokenswing.comixaire.controller;


import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.exception.NoLibraryItemFoundException;
import com.github.brokenswing.comixaire.exception.NoLoanFoundException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.facades.item.LibraryItemFacade;
import com.github.brokenswing.comixaire.facades.loans.LoansFacade;
import com.github.brokenswing.comixaire.javafx.Alerts;
import com.github.brokenswing.comixaire.javafx.IntField;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Loan;
import com.github.brokenswing.comixaire.utils.FormValidationBuilder;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ReturnsItemIdController implements Initializable
{
    @FXML
    public IntField returnItemIdField;
    @FXML
    public Button returnButton;

    @InjectValue
    private LoansFacade loansFacade;
    @InjectValue
    private Router router;

    public void back()
    {
        router.navigateTo(Views.ActionCenters.STAFF);
    }

    public void select()
    {
        try
        {
            Loan loan = loansFacade.getLatestLoanByItemId(returnItemIdField.getValue());
            router.navigateTo(Views.RETURNS, loan);
        }
        catch (InternalException e)
        {
            e.printStackTrace();
            Alerts.exception(e);
        }
        catch (NoLoanFoundException e)
        {
            Alerts.failure("No loan with this library item can be found.");
        }
        catch (NoLibraryItemFoundException e)
        {
            Alerts.failure("No library item with this ID is in our database.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        BooleanBinding isValid = new FormValidationBuilder()
                .notEmpty(returnItemIdField.textProperty())
                .build();
        returnButton.disableProperty().bind(isValid.not());
    }

    public void selectKeyPressed(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            this.select();
        }
    }
}

