package com.github.brokenswing.comixaire.controller.dialog;

import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.models.LibraryItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class RatingDialogController implements Initializable
{

    @FXML
    private Text title;

    @ViewParam
    private LibraryItem item;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        title.setText(item.getTitle());
    }
}
