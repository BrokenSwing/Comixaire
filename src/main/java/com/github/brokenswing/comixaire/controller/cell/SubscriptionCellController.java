package com.github.brokenswing.comixaire.controller.cell;

import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.models.Subscription;
import com.github.brokenswing.comixaire.utils.PrettyTimeTransformer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class SubscriptionCellController implements Initializable
{

    @ViewParam
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

}
