package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.facades.item.LibraryItemFacade;
import com.github.brokenswing.comixaire.javafx.CustomListCell;
import com.github.brokenswing.comixaire.javafx.IntField;
import com.github.brokenswing.comixaire.javafx.NoOpSelectionModel;
import com.github.brokenswing.comixaire.models.*;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.Router;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class BookingController implements Initializable
{

    private FilteredList<Pair<LibraryItem, Client>> items = new FilteredList<>(FXCollections.observableArrayList());

    @ViewParam
    private Client client;

    @FXML
    private Text clientName;
    @FXML
    private Text clientSubscription;
    @FXML
    private Text clientLoans;
    @FXML
    private Text clientFines;
    @FXML
    private Text clientGender;
    @FXML
    private IntField itemIDField;
    @FXML
    private TextField itemTitleField;
    @FXML
    private ChoiceBox<String> itemTypeFilter;
    @FXML
    private ListView<Pair<LibraryItem, Client>> itemsList;

    @InjectValue
    private ClientsFacade clientsFacade;
    @InjectValue
    private LibraryItemFacade itemsFacade;
    @InjectValue
    private Router router;
    @InjectValue
    private ViewLoader loader;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.clientName.setText(client.getFullname());
        this.clientGender.setText(client.getGender());

        this.itemsList.setSelectionModel(new NoOpSelectionModel<>());
        this.itemsList.setCellFactory(CustomListCell.factory(loader, Views.Cells.BOOKING_ITEM));

        try
        {
            if(clientsFacade.validSubscription(client)) {
                this.clientSubscription.setText("Valid");
                ArrayList<Pair<LibraryItem, Client>> pairs = new ArrayList<>();
                for (LibraryItem item : itemsFacade.findAll())
                {
                    pairs.add(new Pair<>(item, client));
                }
                this.items = new FilteredList<>(FXCollections.observableList(pairs));
                this.itemsList.setItems(this.items);
            }
            else{
                this.clientSubscription.setText("Invalid");
                this.itemsList.setPlaceholder(new Text("Invalid subscription"));
            }
            this.clientLoans.setText(Integer.toString(clientsFacade.countLoans(client)));
            this.clientFines.setText(Integer.toString(clientsFacade.countFines(client)));

        }
        catch (InternalException e)
        {
            e.printStackTrace();
        }

        itemTitleField.textProperty().addListener((obs, oldValue, newValue) -> this.find());
        itemIDField.textProperty().addListener((obs, oldValue, newValue) -> this.find());
        itemTypeFilter.valueProperty().addListener((obs, oldValue, newValue) -> this.find());

        this.find();
    }

    public void back()
    {
        router.navigateTo(Views.CLIENT_BOOKINGS);
    }


    public void find()
    {
        Predicate<LibraryItem> predicate = item -> !Arrays.asList(item.getBookings()).contains(client.getIdClient());

        if (!itemIDField.getText().trim().isEmpty())
        {
            predicate = predicate.and(item -> item.getIdLibraryItem() == itemIDField.getValue());
        }

        if (!itemTitleField.getText().trim().isEmpty())
        {
            predicate = predicate.and(item -> item.getTitle().toLowerCase().contains(itemTitleField.getText().trim().toLowerCase()));
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
        Predicate<LibraryItem> finalPredicate = predicate;
        this.items.setPredicate(p -> finalPredicate.test(p.getKey()));
    }
}
