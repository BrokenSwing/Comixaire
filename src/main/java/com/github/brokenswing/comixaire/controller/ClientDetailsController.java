package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.controller.util.ParametrizedController;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.utils.PrettyTimeTransformer;
import com.github.brokenswing.comixaire.view.ClientDetailsView;
import com.github.brokenswing.comixaire.view.ClientSubscriptions;
import com.github.brokenswing.comixaire.view.ClientUpdateView;
import com.github.brokenswing.comixaire.view.ClientsView;
import com.github.brokenswing.comixaire.view.util.Router;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Optional;
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



    /**
     *  NEEDS FACTORISATION IN A FUTURE
     */

    public void infos(ActionEvent actionEvent)
    {
        router.navigateTo(new ClientDetailsView(), client);
    }

    public void update(ActionEvent actionEvent)
    {
        router.navigateTo(new ClientUpdateView(), client);
    }

    public void subscriptions(ActionEvent actionEvent) { router.navigateTo(new ClientSubscriptions(), client); }

    public void fines(ActionEvent actionEvent)
    {
        //TODO: new view to consult fines
    }

    public void delete(ActionEvent actionEvent)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure ?");
        alert.setHeaderText("Do you really want to delete the client: " + client.getFullname() + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            try
            {
                clientsFacade.delete(client);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Success");
                alert.setHeaderText("Client successfully removed from our database");
                alert2.showAndWait();
                router.navigateTo(new ClientsView());
            }
            catch (InternalException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void back() { System.out.println("clicked");router.navigateTo(new ClientsView()); }
}
