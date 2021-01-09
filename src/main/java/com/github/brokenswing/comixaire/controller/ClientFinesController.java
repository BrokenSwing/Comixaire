package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.facades.fines.FinesFacade;
import com.github.brokenswing.comixaire.javafx.Alerts;
import com.github.brokenswing.comixaire.javafx.CustomListCell;
import com.github.brokenswing.comixaire.javafx.NoOpSelectionModel;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.Fine;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientFinesController implements Initializable
{

    @ViewParam
    private Client client;

    @FXML
    private ListView<Fine> finesList;

    @InjectValue
    private FinesFacade fineFacade;

    @InjectValue
    private ViewLoader loader;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        finesList.setSelectionModel(new NoOpSelectionModel<>());
        finesList.setCellFactory(CustomListCell.factory(loader, Views.Cells.FINE));
        try
        {
            finesList.setItems(FXCollections.observableArrayList(fineFacade.findByClient(client)));
        }
        catch (InternalException | NoClientFoundException e)
        {
            e.printStackTrace();
            Alerts.exception(e);
        }
    }

}
