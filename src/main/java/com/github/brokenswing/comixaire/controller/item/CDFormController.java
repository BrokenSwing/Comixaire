package com.github.brokenswing.comixaire.controller.item;

import com.github.brokenswing.comixaire.javafx.IntField;
import com.github.brokenswing.comixaire.models.CD;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.builder.LibraryItemBuilder;
import com.github.brokenswing.comixaire.models.builder.LibraryItemStep;
import com.github.brokenswing.comixaire.utils.FormValidationBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CDFormController extends LibraryItemFormController<CD>
{

    @FXML
    private IntField duration;
    @FXML
    private TextField artist;

    public CDFormController()
    {
        super(CD.class);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        super.initialize(location, resources);

        if (editedItem != null)
        {
            this.artist.setText(editedItem.getArtist());
            this.duration.setValue(editedItem.getDuration());
        }
    }

    @Override
    protected void buildFormValidation(FormValidationBuilder builder)
    {
        super.buildFormValidation(builder);
        builder.notEmpty(artist.textProperty())
                .notNull(duration.valueProperty());
    }

    @Override
    protected LibraryItem getItemFromFields()
    {
        LibraryItemStep builder = editedItem == null ? LibraryItemBuilder.create() : LibraryItemBuilder.from(editedItem);
        super.populateBuilder(builder);
        return builder.cd()
                .duration(duration.getValue())
                .artist(artist.getText())
                .build();
    }

}
