package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.facades.item.LibraryItemFacade;
import com.github.brokenswing.comixaire.javafx.NoOpSelectionModel;
import com.github.brokenswing.comixaire.models.CD;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.ConditionType;
import com.github.brokenswing.comixaire.models.LibraryItem;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemsController implements Initializable
{
    private FilteredList<LibraryItem> items = new FilteredList<>(FXCollections.observableArrayList());

    @FXML
    private ListView<LibraryItem> itemsList;

    @InjectValue
    private LibraryItemFacade itemsFacade;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        try
        {
            this.itemsList.setSelectionModel(new NoOpSelectionModel<>());
            //this.itemsList.setCellFactory(lv -> new ClientsController.ClientListCell());
            this.items = new FilteredList<>(FXCollections.observableArrayList(itemsFacade.findAll()));
            itemsList.setItems(this.items);
        }
        catch (InternalException e)
        {
            e.printStackTrace();
        }

    }
}
