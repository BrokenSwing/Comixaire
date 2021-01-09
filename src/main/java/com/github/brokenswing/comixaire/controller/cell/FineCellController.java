package com.github.brokenswing.comixaire.controller.cell;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.fines.FinesFacade;
import com.github.brokenswing.comixaire.javafx.Alerts;
import com.github.brokenswing.comixaire.models.Fine;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class FineCellController implements Initializable
{

    @ViewParam
    private Fine fine;

    @FXML
    private Text label;
    @FXML
    private Text price;
    @FXML
    private Button payButton;

    @InjectValue
    private FinesFacade fineFacade;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        label.setText(fine.getFineType().getLabel());
        price.setText(Integer.toString(fine.getFineType().getPrice()));
        payButton.setDisable(fine.isPaid());
    }

    public void pay()
    {
        try
        {
            fineFacade.pay(fine);
            payButton.setDisable(true);
        }
        catch (InternalException e)
        {
            Alerts.failure("Impossible to pay this fine.");
        }
    }
}
