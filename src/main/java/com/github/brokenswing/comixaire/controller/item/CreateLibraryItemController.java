package com.github.brokenswing.comixaire.controller.item;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.view.item.BookFormView;
import com.github.brokenswing.comixaire.view.item.CDFormView;
import com.github.brokenswing.comixaire.view.item.DVDFormView;
import com.github.brokenswing.comixaire.view.item.GameFormView;
import com.github.brokenswing.comixaire.view.util.View;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class CreateLibraryItemController implements Initializable
{

    @FXML
    private ChoiceBox<LibraryItemType> itemType;
    @FXML
    private AnchorPane formPane;

    @InjectValue
    private ViewLoader loader;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.itemType.setItems(FXCollections.observableArrayList(
                new LibraryItemType(BookFormView::new, "Book"),
                new LibraryItemType(DVDFormView::new, "DVD"),
                new LibraryItemType(CDFormView::new, "CD"),
                new LibraryItemType(GameFormView::new, "Game")
        ));

        this.itemType.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) ->
        {
            Node form = loader.loadView(newValue.getViewSupplier().get());
            formPane.getChildren().clear();
            formPane.getChildren().add(form);
        });

        this.itemType.getSelectionModel().select(0);
    }

    private static class LibraryItemType
    {
        private final Supplier<View> viewSupplier;
        private final String displayName;

        private LibraryItemType(Supplier<View> viewSupplier, String displayName)
        {
            this.viewSupplier = viewSupplier;
            this.displayName = displayName;
        }

        public Supplier<View> getViewSupplier()
        {
            return viewSupplier;
        }

        @Override
        public String toString()
        {
            return this.displayName;
        }
    }

}
