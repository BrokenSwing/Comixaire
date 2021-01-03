package com.github.brokenswing.comixaire.controller.cell;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.utils.PrettyTimeTransformer;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientCellController implements Initializable
{

    @ViewParam
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

    @InjectValue
    private Router router;

    @FXML
    private void more()
    {
        router.navigateTo(Views.ClientManagement.MAIN_FRAME, this.client);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.firstname.setText(this.client.getFirstname());
        this.lastname.setText(this.client.getLastname());
        this.birthdate.setText(PrettyTimeTransformer.prettyDate(this.client.getBirthdate()));
        this.gender.setText(this.client.getGender());
        this.cardId.setText(this.client.getCardId());

    }
}
