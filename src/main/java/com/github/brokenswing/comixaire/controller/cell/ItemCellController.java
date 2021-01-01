package com.github.brokenswing.comixaire.controller.cell;

import com.github.brokenswing.comixaire.controller.util.ParametrizedController;
import com.github.brokenswing.comixaire.models.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
