package com.github.brokenswing.comixaire.controller.item;

import com.github.brokenswing.comixaire.javafx.IntField;
import com.github.brokenswing.comixaire.models.Game;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.builder.LibraryItemBuilder;
import com.github.brokenswing.comixaire.models.builder.LibraryItemStep;
import com.github.brokenswing.comixaire.utils.FormValidationBuilder;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class GameFormController extends LibraryItemFormController<Game>
{

    @FXML
    public TextField publisher;
    @FXML
    public IntField minAge;
    @FXML
    public IntField minPlayers;
    @FXML
    public IntField maxPlayers;
    @FXML
    public TextArea inventory;

    public GameFormController()
    {
        super(Game.class);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        super.initialize(location, resources);

        if (editedItem != null)
        {
            this.publisher.setText(editedItem.getPublisher());
            this.minPlayers.setValue(editedItem.getMinPlayers());
            this.maxPlayers.setValue(editedItem.getMaxPlayers());
            this.minAge.setValue(editedItem.getMinAge());
            this.inventory.setText(editedItem.getContentInventory());
        }
    }

    @Override
    protected void buildFormValidation(FormValidationBuilder builder)
    {
        super.buildFormValidation(builder);
        builder.notEmpty(publisher.textProperty())
                .notEmpty(inventory.textProperty())
                .notEmpty(minAge.textProperty())
                .notEmpty(minPlayers.textProperty())
                .notEmpty(maxPlayers.textProperty())
                .add(Bindings.createBooleanBinding(
                        () -> minPlayers.getValue() <= maxPlayers.getValue(),
                        minPlayers.valueProperty(), maxPlayers.valueProperty())
                );
    }

    @Override
    protected LibraryItem getItemFromFields()
    {
        LibraryItemStep builder = editedItem == null ? LibraryItemBuilder.create() : LibraryItemBuilder.from(editedItem);
        super.populateBuilder(builder);
        return builder.game()
                .publisher(publisher.getText().trim())
                .minPlayers(minPlayers.getValue())
                .maxPlayers(maxPlayers.getValue())
                .minAge(minAge.getValue())
                .inventory(inventory.getText().trim())
                .build();
    }

}
