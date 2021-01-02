package com.github.brokenswing.comixaire.controller.cell;

import com.github.brokenswing.comixaire.controller.util.ParametrizedController;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.Subscription;
import com.github.brokenswing.comixaire.utils.PrettyTimeTransformer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class SubscriptionCellController implements ParametrizedController<Subscription>, Initializable
{
    private Subscription subscription;

    @FXML
    private Text from;
    @FXML
    private Text to;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        from.setText(PrettyTimeTransformer.prettyDate(subscription.getFrom()));
        to.setText(PrettyTimeTransformer.prettyDate(subscription.getTo()));
    }

    @Override
    public void handleViewParam(Subscription subscription)
    {
        this.subscription = subscription;
    }
}
