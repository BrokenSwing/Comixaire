package com.github.brokenswing.comixaire.controller.cell;

import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.models.LibraryItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class RecommendedItemCellController implements Initializable
{

    @ViewParam
    private LibraryItem libraryItem;

    @FXML
    private Text itemTitle;
    @FXML
    private Text itemLocation;
    @FXML
    private Text itemType;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.itemTitle.setText(this.libraryItem.getTitle());
        this.itemLocation.setText("Location : " + this.libraryItem.getLocation());
        this.itemType.setText("Type : " + libraryItem.getClass().getSimpleName());
    }
}
