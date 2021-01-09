package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.facades.loans.LoansFacade;
import com.github.brokenswing.comixaire.facades.rating.RatingFacade;
import com.github.brokenswing.comixaire.javafx.CustomListCell;
import com.github.brokenswing.comixaire.javafx.IntField;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Loan;
import com.github.brokenswing.comixaire.models.Rating;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.Router;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

public class ClientBorrowedItemsController implements Initializable
{

    private FilteredList<ObservableRating> filteredList = new FilteredList<>(FXCollections.emptyObservableList());

    @FXML
    private CheckBox unratedField;
    @FXML
    private CheckBox ratedField;
    @FXML
    private IntField idField;
    @FXML
    private TextField titleField;
    @FXML
    private ListView<ObservableRating> itemsList;

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
        Predicate<ObservableRating> predicate = o -> true;

        if (!unratedField.isSelected())
        {
            predicate = predicate.and(o -> o.existsProperty().get());
        }

        if (!ratedField.isSelected())
        {
            predicate = predicate.and(o -> !o.existsProperty().get());
        }

        if (idField.getText() != null && !idField.getText().trim().isEmpty())
        {
            predicate = predicate.and(o -> o.getLibraryItem().getIdLibraryItem() == idField.getValue());
        }

        if (titleField.getText() != null && !titleField.getText().trim().isEmpty())
        {
            predicate = predicate.and(o -> o.getLibraryItem().getTitle().toLowerCase().contains(titleField.getText().trim().toLowerCase()));
        }

        filteredList.setPredicate(predicate);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.itemsList.setCellFactory(CustomListCell.factory(loader, Views.Cells.RATING));
        try
        {
            Loan[] loans = loansFacade.findByCardId(client.getCardId());
            Arrays.sort(loans, Comparator.comparing(l -> l.getLibraryItem().getIdLibraryItem()));

            Rating[] ratings = ratingFacade.getAllForClient(client);
            Arrays.sort(ratings, Comparator.comparing(l -> l.getLibraryItem().getIdLibraryItem()));

            List<ObservableRating> observableList = new LinkedList<>();
            int j = 0;
            int k = -1;
            for (Loan loan : loans)
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
                ObservableRating observableRating = new ObservableRating(client, item, rating == null ? -1 : rating.getNote());
                observableList.add(observableRating);
            }

            filteredList = new FilteredList<>(FXCollections.observableList(observableList));
            itemsList.setItems(filteredList);
        }
        catch (InternalException | NoClientFoundException e)
        {
            e.printStackTrace();
        }


        this.titleField.textProperty().addListener((obs, o, b) -> this.search());
        this.idField.textProperty().addListener((obs, o, b) -> this.search());
        this.ratedField.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> this.search());
        this.unratedField.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> this.search());
    }

    public static class ObservableRating
    {

        private final Client client;
        private final LibraryItem libraryItem;
        private final BooleanBinding exists;
        private final IntegerProperty noteProperty = new SimpleIntegerProperty();

        public ObservableRating(Client client, LibraryItem libraryItem, int note)
        {
            this.client = client;
            this.libraryItem = libraryItem;
            this.noteProperty.setValue(note);
            this.exists = Bindings.createBooleanBinding(() -> noteProperty.intValue() >= 0, noteProperty);
        }

        public LibraryItem getLibraryItem()
        {
            return libraryItem;
        }

        public Client getClient()
        {
            return client;
        }

        public IntegerProperty noteProperty()
        {
            return noteProperty;
        }

        public BooleanBinding existsProperty()
        {
            return exists;
        }

    }

}
