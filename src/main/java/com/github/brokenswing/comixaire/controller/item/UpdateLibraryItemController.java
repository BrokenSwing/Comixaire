package com.github.brokenswing.comixaire.controller.item;

import com.github.brokenswing.comixaire.controller.util.ParametrizedController;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.models.*;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.item.BookFormView;
import com.github.brokenswing.comixaire.view.item.CDFormView;
import com.github.brokenswing.comixaire.view.item.DVDFormView;
import com.github.brokenswing.comixaire.view.item.GameFormView;
import com.github.brokenswing.comixaire.view.util.Router;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class UpdateLibraryItemController implements Initializable, ParametrizedController<LibraryItem>
{

    private static final HashMap<Class<?>, Supplier<ParametrizedView<? extends ParametrizedController<LibraryItem>, LibraryItem>>> ITEM_VIEWS = new HashMap<>();

    static
    {
        ITEM_VIEWS.put(Book.class, BookFormView::new);
        ITEM_VIEWS.put(CD.class, CDFormView::new);
        ITEM_VIEWS.put(DVD.class, DVDFormView::new);
        ITEM_VIEWS.put(Game.class, GameFormView::new);
    }

    @FXML
    private AnchorPane formPane;

    @InjectValue
    private ViewLoader loader;
    @InjectValue
    private Router router;

    private LibraryItem editingItem;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Supplier<ParametrizedView<? extends ParametrizedController<LibraryItem>, LibraryItem>> viewSupplier = ITEM_VIEWS.get(editingItem.getClass());
        if (viewSupplier == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unknown library item type");
            alert.setContentText("The item your trying to update isn't known by this software.");
            alert.showAndWait();
            router.navigateTo(Views.LIBRARY_ITEMS_LIST);
        }
        else
        {
            formPane.getChildren().add(loader.loadView(viewSupplier.get(), editingItem));
        }
    }

    public void back()
    {
        router.navigateTo(Views.LIBRARY_ITEMS_LIST);
    }

    @Override
    public void handleViewParam(LibraryItem editingItem)
    {
        this.editingItem = editingItem;
    }

}
