package com.github.brokenswing.comixaire.controller.cell;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.item.LibraryItemFacade;
import com.github.brokenswing.comixaire.javafx.Alerts;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemCellController implements Initializable
{

    @ViewParam
    private LibraryItem libraryItem;

    @FXML
    private Text itemTitle;
    @FXML
    private Text itemLocation;
    @FXML
    private Text itemType;
    @FXML
    private Text itemId;

    @InjectValue
    private LibraryItemFacade itemsFacade;

    @InjectValue
    private Router router;

    @FXML
    protected void update()
    {
        router.navigateTo(Views.LibraryItems.UPDATE, libraryItem);
    }

    @FXML
    protected void delete()
    {
        boolean confirm = Alerts.confirm("Item deletion", "", "Do you really want to delete the " + libraryItem.getClass().getSimpleName() + " named " + libraryItem.getTitle() + ".");
        if (confirm)
        {
            try
            {
                this.itemsFacade.delete(libraryItem);
                Alerts.success("The library item " + libraryItem.getTitle() + " was deleted.");
            }
            catch (InternalException e)
            {
                e.printStackTrace();
                Alerts.exception(e);
            }
            router.navigateTo(Views.LibraryItems.LIST);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.itemTitle.setText(this.libraryItem.getTitle());
        this.itemLocation.setText("Location: " + this.libraryItem.getLocation());
        this.itemType.setText("Type: " + libraryItem.getClass().getSimpleName());
        this.itemId.setText("Id: " + libraryItem.getIdLibraryItem());
    }
}
