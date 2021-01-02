package com.github.brokenswing.comixaire.controller.item;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.models.*;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.Router;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class UpdateLibraryItemController implements Initializable
{

    private static final HashMap<Class<?>, String> ITEM_VIEWS = new HashMap<>();

    static
    {
        ITEM_VIEWS.put(Book.class, Views.LibraryItemsForms.BOOK);
        ITEM_VIEWS.put(CD.class, Views.LibraryItemsForms.CD);
        ITEM_VIEWS.put(DVD.class, Views.LibraryItemsForms.DVD);
        ITEM_VIEWS.put(Game.class, Views.LibraryItemsForms.GAME);
    }

    @FXML
    private AnchorPane formPane;

    @InjectValue
    private ViewLoader loader;
    @InjectValue
    private Router router;

    @ViewParam
    private LibraryItem editingItem;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        String viewPath = ITEM_VIEWS.get(editingItem.getClass());
        if (viewPath == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unknown library item type");
            alert.setContentText("The item your trying to update isn't known by this software.");
            alert.showAndWait();
            router.navigateTo(Views.LIBRARY_ITEMS_LIST);
        }
        else
        {
            formPane.getChildren().add(loader.loadView(viewPath, editingItem));
        }
    }

    public void back()
    {
        router.navigateTo(Views.LIBRARY_ITEMS_LIST);
    }

}
