package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.javafx.Alerts;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.utils.PrettyTimeTransformer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientDetailsController implements Initializable
{

    @ViewParam
    private Client client;

    @FXML
    private Text cardId;
    @FXML
    private Text gender;
    @FXML
    private Text birthdate;
    @FXML
    private Text loans;
    @FXML
    private Text votes;
    @FXML
    private Text currentLoans;
    @FXML
    private Text fines;
    @FXML
    private Text address;

    @InjectValue
    private ClientsFacade clientsFacade;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.cardId.setText(client.getCardId());
        this.gender.setText(client.getGender());
        this.address.setText(client.getAddress());
        this.birthdate.setText(PrettyTimeTransformer.prettyDate(client.getBirthdate()));
        try
        {
            this.loans.setText(Integer.toString(clientsFacade.countLoans(client)));
            this.fines.setText(Integer.toString(clientsFacade.countFines(client)));
            this.votes.setText(Integer.toString(clientsFacade.countVotes(client)));
            this.currentLoans.setText(Integer.toString(clientsFacade.countCurrentLoans(client)));
        }
        catch (InternalException e)
        {
            e.printStackTrace();
            Alerts.exception(e);
        }
    }

}
