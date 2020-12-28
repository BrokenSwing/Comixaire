package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.controller.util.ParametrizedController;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.utils.PrettyTimeTransformer;
import com.github.brokenswing.comixaire.view.util.Router;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientDetailsController implements ParametrizedController<Client>, Initializable
{
    private Client client;

    @FXML
    private Text fullname;
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
    @InjectValue
    private Router router;

    @Override
    public void handleViewParam(Client client)
    {
        this.client = client;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.fullname.setText(client.getFullname());
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
        }
    }

    public void update(ActionEvent actionEvent)
    {
        //TODO: new view to update current client
    }

    public void fines(ActionEvent actionEvent)
    {
        //TODO: new view to consult fines
    }

    public void subscriptions(ActionEvent actionEvent)
    {
        //TODO: new view to consult subscription
    }

    public void delete(ActionEvent actionEvent)
    {
        //TODO: delete client
    }
}
