package com.github.brokenswing.comixaire.controller.cell;

import com.github.brokenswing.comixaire.controller.util.ParametrizedController;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.view.ActionCenterView;
import com.github.brokenswing.comixaire.view.ClientDetailsView;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientCellController implements ParametrizedController<Client>, Initializable
{
    private Client client;

    @FXML
    private Text firstname;
    @FXML
    private Text lastname;
    @FXML
    private Text birthdate;
    @FXML
    private Text gender;
    @FXML
    private Text cardId;
    @FXML
    private Text subscription;

    @InjectValue
    private Router router;

    @FXML
    private void more(){
        router.navigateTo(new ClientDetailsView(), this.client);
    }

    @Override
    public void handleViewParam(Client client) { this.client = client; }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.firstname.setText(this.client.getFirstname());
        this.lastname.setText(this.client.getLastname());
        this.birthdate.setText(this.client.getBirthdate().toString());
        this.gender.setText(this.client.getGender());
        this.cardId.setText(this.client.getCardId());
        if (this.client.getSubscriptionId() > -1)
        {
            this.subscription.setText("Valid");
        }
        else
        {
            this.subscription.setText("Not valid");
        }

    }
}
