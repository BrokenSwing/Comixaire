package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.facades.item.LibraryItemFacade;
import com.github.brokenswing.comixaire.facades.loans.LoansFacade;
import com.github.brokenswing.comixaire.facades.rating.RatingFacade;
import com.github.brokenswing.comixaire.javafx.CustomListCell;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Loan;
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
import java.util.*;

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
    private LoansFacade loansFacade;

    @InjectValue
    private RatingFacade ratingFacade;


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
            Loan[] items = loansFacade.findByCardId(client.getCardId());
            Arrays.sort(items, Comparator.comparing(l -> l.getLibraryItem().getIdLibraryItem()));
            Rating[] ratings = ratingFacade.getAllForClient(client);
            Arrays.sort(ratings, Comparator.comparing(l -> l.getLibraryItem().getIdLibraryItem()));
            List<Pair<LibraryItem, Rating>> observableList = new LinkedList<>();
            int j = 0;
            int k = -1;
            for (Loan loan : items)
            {
                LibraryItem item = loan.getLibraryItem();
                if (k == item.getIdLibraryItem())
                {
                    continue;
                }
                k = item.getIdLibraryItem();
                Rating rating = null;
                if (j < ratings.length && ratings[j].getLibraryItem().getIdLibraryItem() == item.getIdLibraryItem())
                {
                    rating = ratings[j++];
                }
                observableList.add(new Pair<>(item, rating));
            }
            filteredList = new FilteredList<>(FXCollections.observableList(observableList));
            itemsList.setItems(filteredList);
        }
        catch (InternalException | NoClientFoundException e)
        {
            e.printStackTrace();
        }
    }
}
