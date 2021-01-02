package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class LoansController implements Initializable
{

    @ViewParam
    private Client client;

    @FXML
    private ListView loansList;
    @FXML
    private Text fullname;
    @FXML
    private Text subscription;
    @FXML
    private Text loans;
    @FXML
    private Text fines;
    @FXML
    private Text gender;

    @InjectValue
    private ClientsFacade clientsFacade;
    @InjectValue
    private Router router;

    public void back()
    {
        router.navigateTo(Views.CLIENT_LOANS);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        fullname.setText(client.getFullname());
        gender.setText(client.getGender());
        try
        {
            if(clientsFacade.validSubscription(client)) {
                subscription.setText("Valid");
            }
            else{
                subscription.setText("Not valid");
            }
            loans.setText(Integer.toString(clientsFacade.countLoans(client)));
            fines.setText(Integer.toString(clientsFacade.countFines(client)));
        }
        catch (InternalException e)
        {
            e.printStackTrace();
        }

    }

    public void loan()
    {
        System.out.println("Loan clicked !");
    }
}
