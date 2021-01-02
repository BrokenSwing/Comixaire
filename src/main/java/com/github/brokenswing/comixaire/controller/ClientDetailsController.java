package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.utils.PrettyTimeTransformer;
import com.github.brokenswing.comixaire.view.*;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientDetailsController implements Initializable
{

    @ViewParam
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


    /**
     * TODO: NEEDS FACTORISATION IN A FUTURE
     */

    public void back()
    {
        router.navigateTo(Views.CLIENTS_LIST);
    }

    public void infos()
    {
        router.navigateTo(Views.CLIENT_DETAILS, client);
    }

    public void update()
    {
        router.navigateTo(Views.CLIENT_DETAILS, client);
    }

    public void subscriptions()
    {
        router.navigateTo(Views.CLIENT_SUBSCRIPTIONS, client);
    }

    public void fines()
    {
        router.navigateTo(Views.CLIENT_FINES, client);
    }

    public void delete()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure ?");
        alert.setHeaderText("Do you really want to delete the client: " + client.getFullname() + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            try
            {
                clientsFacade.delete(client);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Success");
                alert.setHeaderText("Client successfully removed from our database");
                alert2.showAndWait();
                router.navigateTo(Views.CLIENTS_LIST);
            }
            catch (InternalException e)
            {
                e.printStackTrace();
            }
        }
    }
}
