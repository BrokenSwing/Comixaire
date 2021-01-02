package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.controller.util.ParametrizedController;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.view.LoansClientIdView;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;


import java.net.URL;
import java.util.ResourceBundle;

public class LoansController implements ParametrizedController<Client>, Initializable
{
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

    @Override
    public void handleViewParam(Client client)
    {
        this.client = client;
    }

    public void back() { router.navigateTo(new LoansClientIdView()); }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        fullname.setText(client.getFullname());
        gender.setText(client.getGender());
        this.subscription.setText("Not valid");//TODO: change
        try
        {
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
