package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.item.LibraryItemFacade;
import com.github.brokenswing.comixaire.javafx.Alerts;
import com.github.brokenswing.comixaire.javafx.CustomListCell;
import com.github.brokenswing.comixaire.javafx.IntField;
import com.github.brokenswing.comixaire.javafx.NoOpSelectionModel;
import com.github.brokenswing.comixaire.models.*;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ItemsController implements Initializable
{
    private FilteredList<LibraryItem> items = new FilteredList<>(FXCollections.observableArrayList());

    @FXML
    private ListView<LibraryItem> itemsList;
    @FXML
    private IntField itemIDField;
    @FXML
    private TextField itemNameField;
    @FXML
    private ChoiceBox<String> itemTypeFilter;

    @InjectValue
    private LibraryItemFacade itemsFacade;

    @InjectValue
    private ViewLoader loader;

    @FXML
    protected void find()
    {
        Predicate<LibraryItem> predicate = item -> true;

        if (!itemIDField.getText().trim().isEmpty())
        {
            predicate = predicate.and(item -> item.getIdLibraryItem() == itemIDField.getValue());
        }

        if (!itemNameField.getText().trim().isEmpty())
        {
            predicate = predicate.and(item -> item.getTitle().toLowerCase().contains(itemNameField.getText().trim().toLowerCase()));
        }

        switch (itemTypeFilter.getValue())
        {
            case "Book":
                predicate = predicate.and(item -> item instanceof Book);
                break;
            case "Game":
                predicate = predicate.and(item -> item instanceof Game);
                break;
            case "CD":
                predicate = predicate.and(item -> item instanceof CD);
                break;
            case "DVD":
                predicate = predicate.and(item -> item instanceof DVD);
                break;
        }
        this.items.setPredicate(predicate);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.itemsList.setSelectionModel(new NoOpSelectionModel<>());
        this.itemsList.setCellFactory(CustomListCell.factory(loader, Views.Cells.ITEM));

        try
        {
            this.items = new FilteredList<>(FXCollections.observableArrayList(itemsFacade.findAll()));
            this.itemsList.setItems(this.items);
        }
        catch (InternalException e)
        {
            e.printStackTrace();
            Alerts.exception(e);
        }

        itemNameField.textProperty().addListener((obs, oldValue, newValue) -> this.find());
        itemIDField.textProperty().addListener((obs, oldValue, newValue) -> this.find());
        itemTypeFilter.valueProperty().addListener((obs, oldValue, newValue) -> this.find());
    }

}
