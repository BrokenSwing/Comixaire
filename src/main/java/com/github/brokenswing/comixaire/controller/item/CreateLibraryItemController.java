package com.github.brokenswing.comixaire.controller.item;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.Router;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateLibraryItemController implements Initializable
{

    @FXML
    private ChoiceBox<LibraryItemType> itemType;
    @FXML
    private AnchorPane formPane;

    @InjectValue
    private ViewLoader loader;
    @InjectValue
    private Router router;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.itemType.setItems(FXCollections.observableArrayList(
                new LibraryItemType(Views.LibraryItems.Forms.BOOK, "Book"),
                new LibraryItemType(Views.LibraryItems.Forms.DVD, "DVD"),
                new LibraryItemType(Views.LibraryItems.Forms.CD, "CD"),
                new LibraryItemType(Views.LibraryItems.Forms.GAME, "Game")
        ));

        this.itemType.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) ->
        {
            Node form = loader.loadView(newValue.getView());
            formPane.getChildren().clear();
            formPane.getChildren().add(form);
        });

        this.itemType.getSelectionModel().select(0);
    }

    public void back(MouseEvent mouseEvent)
    {
        router.navigateTo(Views.ActionCenters.STAFF);
    }

    private static class LibraryItemType
    {
        private final String viewPath;
        private final String displayName;

        private LibraryItemType(String viewPath, String displayName)
        {
            this.viewPath = viewPath;
            this.displayName = displayName;
        }

        public String getView()
        {
            return viewPath;
        }

        @Override
        public String toString()
        {
            return this.displayName;
        }

    }

}
