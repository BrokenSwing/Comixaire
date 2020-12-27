package com.github.brokenswing.comixaire.controller.item;

import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.javafx.IntField;
import com.github.brokenswing.comixaire.models.DVD;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.builder.LibraryItemBuilder;
import com.github.brokenswing.comixaire.models.builder.LibraryItemStep;
import com.github.brokenswing.comixaire.utils.FormValidationBuilder;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.github.brokenswing.comixaire.utils.BindingsHelper.trimmed;

public class DVDFormController extends LibraryItemFormController<DVD>
{

    @FXML
    private TextField producer;
    @FXML
    private CheckComboBox<String> casting;
    @FXML
    private IntField duration;

    public DVDFormController()
    {
        super(DVD.class);
    }

    @Override
    protected LibraryItem getItemFromFields()
    {
        LibraryItemStep builder = editedItem == null ? LibraryItemBuilder.create() : LibraryItemBuilder.from(editedItem);
        super.populateBuilder(builder);
        return builder.dvd()
                .casting(casting.getCheckModel().getCheckedItems().toArray(new String[0]))
                .duration(duration.getValue())
                .producer(producer.getText().trim())
                .build();
    }

    @Override
    protected void buildFormValidation(FormValidationBuilder builder)
    {
        super.buildFormValidation(builder);
        builder.notEmpty(producer.textProperty());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        super.initialize(location, resources);

        if (editedItem != null)
        {
            this.producer.setText(editedItem.getProducer());
            this.duration.setValue(editedItem.getDuration());
            this.casting.getItems().setAll(editedItem.getCasting());
        }

        try
        {
            this.casting.getItems().addAll(Arrays.asList(libraryItemFacade.getKnownCastings()));
        }
        catch (InternalException e)
        {
            e.printStackTrace();
        }
    }

    public void addCasting()
    {
        Dialog<ButtonType> d = new Dialog<>();
        d.setTitle("Add casting");
        d.setHeaderText("Enter the name of the actor to add :");

        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType addButton = new ButtonType("Add actor", ButtonBar.ButtonData.OK_DONE);

        d.getDialogPane().getButtonTypes().add(cancelButton);
        d.getDialogPane().getButtonTypes().add(addButton);

        TextField actorName = new TextField();
        actorName.setPromptText("Actor name");

        d.getDialogPane().setGraphic(actorName);

        BooleanBinding formValid = new FormValidationBuilder()
                .notEmpty(actorName.textProperty())
                .notIn(trimmed(actorName.textProperty()), casting.getItems(), String::equalsIgnoreCase)
                .build();

        d.getDialogPane().lookupButton(addButton).disableProperty().bind(formValid.not());

        Optional<ButtonType> result = d.showAndWait();
        if (result.isPresent() && result.get() == addButton)
        {
            casting.getItems().add(actorName.getText().trim());
            // Maybe uncomment because we want the new casting to be checked, but leads to weird checkboxes issues :
            // casting.getCheckModel().toggleCheckState(actorName.getText().trim());
        }
    }

}
