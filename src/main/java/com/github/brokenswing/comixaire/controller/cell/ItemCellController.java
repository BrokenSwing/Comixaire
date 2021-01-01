package com.github.brokenswing.comixaire.controller.cell;

import com.github.brokenswing.comixaire.controller.util.ParametrizedController;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.item.LibraryItemFacade;
import com.github.brokenswing.comixaire.models.*;
import com.github.brokenswing.comixaire.view.ClientDetailsView;
import com.github.brokenswing.comixaire.view.ItemsView;
import com.github.brokenswing.comixaire.view.alert.InternalErrorAlert;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemCellController implements ParametrizedController<LibraryItem>, Initializable
{

    private LibraryItem libraryItem;

    @FXML
    private Text itemTittle;
    @FXML
    private Text itemLocation;
    @FXML
    private Text itemType;

    @InjectValue
    private LibraryItemFacade itemsFacade;

    @InjectValue
    private Router router;

    @FXML
    protected void update()
    {

    }

    @FXML
    protected void delete()
    {
        try
        {
            this.itemsFacade.delete(libraryItem);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("The library item \"" + libraryItem.getTitle() + "\" was successfully deleted.");
            alert.showAndWait();
        }
        catch (InternalException e)
        {
            e.printStackTrace();
            new InternalErrorAlert(e).showAndWait();
        }
        router.navigateTo(new ItemsView());
    }

    @Override
    public void handleViewParam(LibraryItem item)
    {
        this.libraryItem = item;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.itemTittle.setText(this.libraryItem.getTitle());
        this.itemLocation.setText("Location : " + this.libraryItem.getLocation());
        if (libraryItem instanceof Book)
        {
            this.itemType.setText("Type : " + "Book");
        }
        else if (libraryItem instanceof CD)
        {
            this.itemType.setText("Type : " + "CD");
        }
        else if (libraryItem instanceof DVD)
        {
            this.itemType.setText("Type : " + "DVD");
        }
        else if (libraryItem instanceof Game)
        {
            this.itemType.setText("Type : " + "Game");
        }
        else
        {
            this.itemType.setText("Type : " + "None");
        }
    }
}
