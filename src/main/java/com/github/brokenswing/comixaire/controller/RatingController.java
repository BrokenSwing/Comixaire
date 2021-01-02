package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.facades.rating.RatingFacade;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Rating;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RatingController implements Initializable
{

    @ViewParam
    private Rating rating;
    private LibraryItem item;

    @FXML
    private Button searchButton;
    @FXML
    private TextField name;
    @FXML
    private CheckBox unratedItems;
    @FXML
    private CheckBox ratedItems;
    @FXML
    private Button popupButton;
    @FXML
    private Button popupRateButton;
    @FXML
    private Slider popupSlider;
    @FXML
    private Button popupClose;

    @InjectValue
    private RatingFacade ratingFacade;
    @InjectValue
    private Router router;

    private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();
    private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.name.setText(item.getTitle());
        configureCheckBox(unratedItems);
        configureCheckBox(ratedItems);
        searchButton.setDisable(true);
        popupButton.setDisable(true);
        popupRateButton.setDisable(true);
        popupClose.setDisable(true);
        popupSlider.setMin(0);
        popupSlider.setMax(5);
        popupSlider.setShowTickLabels(true);
        popupSlider.setShowTickMarks(true);
    }

    private void configureCheckBox(CheckBox checkBox)
    {

        if (checkBox.isSelected())
        {
            selectedCheckBoxes.add(checkBox);
        }
        else
        {
            unselectedCheckBoxes.add(checkBox);
        }

        checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) ->
        {
            if (isNowSelected)
            {
                unselectedCheckBoxes.remove(checkBox);
                selectedCheckBoxes.add(checkBox);
            }
            else
            {
                selectedCheckBoxes.remove(checkBox);
                unselectedCheckBoxes.add(checkBox);
            }

        });

    }
}
