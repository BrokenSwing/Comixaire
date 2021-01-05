package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.item.LibraryItemFacade;
import com.github.brokenswing.comixaire.javafx.CustomListCell;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Rating;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.Router;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientBorrowedItemsController implements Initializable
{
    private FilteredList<Pair<LibraryItem, Rating>> filteredList;

    @FXML
    private ListView<Pair<LibraryItem, Rating>> itemsList;

    @ViewParam
    private Client client;

    @InjectValue
    private Router router;

    @InjectValue
    private ViewLoader loader;

    @InjectValue
    private LibraryItemFacade itemsFacade;


    public void back()
    {
        router.navigateTo(Views.ActionCenters.CLIENT);
    }

    public void search()
    {
        //TODO: filter borrowed items
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.itemsList.setCellFactory(CustomListCell.factory(loader, Views.Cells.RATING));
        try
        {
            LibraryItem[] items = itemsFacade.findAll();
            ObservableList<Pair<LibraryItem, Rating>> observableList = FXCollections.observableArrayList();
            for (LibraryItem item : items){
                observableList.add(new Pair<>(item, null));
            }
        }
        catch (InternalException e)
        {
            e.printStackTrace();
        }
    }
}
