package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.javafx.IntField;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.Loan;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReturnsController implements Initializable
{
    private FilteredList<Loan> loans = new FilteredList<>(FXCollections.observableArrayList());

    @ViewParam
    private Client client;

    @FXML
    private Text clientName;
    @FXML
    private Text clientSubscription;
    @FXML
    private Text clientLoans;
    @FXML
    private Text clientFines;
    @FXML
    private Text clientGender;
    @FXML
    private IntField itemIDField;
    @FXML
    private TextField itemTitleField;
    @FXML
    private ChoiceBox<String> itemTypeFilter;
    @FXML
    private ListView<Loan> currentLoansList;

    @InjectValue
    private ClientsFacade clientsFacade;
    @InjectValue
    private Router router;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.clientName.setText(client.getFullname());
        this.clientGender.setText(client.getGender());

        try
        {
            if(clientsFacade.validSubscription(client))
            {
                this.clientSubscription.setText("Valid");
            }
            else
            {
                this.clientSubscription.setText("Invalid");
                this.currentLoansList.setPlaceholder(new Text("Invalid subscription"));
            }
            this.clientLoans.setText(Integer.toString(clientsFacade.countLoans(client)));
            this.clientFines.setText(Integer.toString(clientsFacade.countFines(client)));

        }
        catch (InternalException e)
        {
            e.printStackTrace();
        }

        itemTitleField.textProperty().addListener((obs, oldValue, newValue) -> this.find());
        itemIDField.textProperty().addListener((obs, oldValue, newValue) -> this.find());
        itemTypeFilter.valueProperty().addListener((obs, oldValue, newValue) -> this.find());
    }

    public void back()
    {
        router.navigateTo(Views.CLIENT_RETURNS);
    }

    public void find()
    {

    }
}
