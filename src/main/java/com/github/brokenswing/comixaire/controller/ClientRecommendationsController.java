package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.controller.util.ParametrizedController;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.recommendations.RecommendationFacade;
import com.github.brokenswing.comixaire.javafx.NoOpSelectionModel;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.view.ClientActionCenterView;
import com.github.brokenswing.comixaire.view.RecommendedItemCellView;
import com.github.brokenswing.comixaire.view.util.Router;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientRecommendationsController implements ParametrizedController<Client>, Initializable
{
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
    public void handleViewParam(Client client)
    {
        this.client = client;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        itemNumberField.setItems(FXCollections.observableArrayList(5,10,20,30));
        itemNumberField.getSelectionModel().select(0);
        try
        {
            this.itemsList.setSelectionModel(new NoOpSelectionModel<>());
            this.itemsList.setCellFactory(lv -> new RecommendedItemListCell());
            this.items = new FilteredList<>(FXCollections.observableArrayList(recommendationFacade.computeRecommendation(itemTypeFilter.getValue(), itemNumberField.getValue(), client)));
            this.itemsList.setItems(this.items);
        }
        catch (InternalException e)
        {
            e.printStackTrace();
        }
    }

    public void back() { router.navigateTo(new ClientActionCenterView()); }

    public void search()
    {
        //TODO: filter recommendations
    }

    private class RecommendedItemListCell extends ListCell<LibraryItem>
    {

        @Override
        protected void updateItem(LibraryItem item, boolean empty)
        {
            super.updateItem(item, empty);
            if (empty)
            {
                setText(null);
                setGraphic(null);
            }
            else
            {
                Node node = loader.loadView(new RecommendedItemCellView(), item);
                setGraphic(node);
            }
        }
    }
}
