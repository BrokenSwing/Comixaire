package com.github.brokenswing.comixaire.controller.cell;

import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Rating;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;

public class RatingCellController implements Initializable
{
    @FXML
    private Text title;
    @FXML
    private org.controlsfx.control.Rating rate;
    @FXML
    private Button rateButton;
    @FXML
    private Text type;

    @ViewParam
    private Pair<LibraryItem, Rating> params;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.title.setText(params.getKey().getTitle());
        this.type.setText(params.getKey().getClass().getSimpleName());
        if (params.getValue() == null)
        {
            rate.setVisible(false);
        }
        else
        {
            rateButton.setVisible(false);
        }
    }
}
