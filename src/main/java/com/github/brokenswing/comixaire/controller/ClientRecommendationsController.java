package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.recommendations.RecommendationFacade;
import com.github.brokenswing.comixaire.javafx.Alerts;
import com.github.brokenswing.comixaire.javafx.CustomListCell;
import com.github.brokenswing.comixaire.javafx.NoOpSelectionModel;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.Router;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientRecommendationsController implements Initializable
{

    @ViewParam
    private Client client;
    private FilteredList<LibraryItem> items = new FilteredList<>(FXCollections.observableArrayList());

    @FXML
    private ChoiceBox<String> itemTypeFilter;
    @FXML
    private ChoiceBox<Integer> itemNumberField;
    @FXML
    private ListView<LibraryItem> itemsList;

    @InjectValue
    private Router router;
    @InjectValue
    private RecommendationFacade recommendationFacade;
    @InjectValue
    private ViewLoader loader;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        itemNumberField.setItems(FXCollections.observableArrayList(5, 10, 15, 20));
        itemNumberField.getSelectionModel().select(0);
        try
        {
            this.itemsList.setSelectionModel(new NoOpSelectionModel<>());
            this.itemsList.setCellFactory(CustomListCell.factory(loader, Views.Cells.ITEM_SUMMARY));
            this.items = new FilteredList<>(FXCollections.observableArrayList(recommendationFacade.computeRecommendation(client)));
            this.itemsList.setItems(this.items);
        }
        catch (InternalException e)
        {
            e.printStackTrace();
            Alerts.exception(e);
        }
    }

    public void back()
    {
        router.navigateTo(Views.ActionCenters.CLIENT);
    }

    public void search()
    {
        //TODO: filter recommendations
    }

}
