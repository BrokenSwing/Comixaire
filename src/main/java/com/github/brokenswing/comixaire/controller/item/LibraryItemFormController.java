package com.github.brokenswing.comixaire.controller.item;

import com.github.brokenswing.comixaire.controller.util.ParametrizedController;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.item.LibraryItemFacade;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.utils.FormValidationBuilder;
import com.github.brokenswing.comixaire.view.alert.InternalErrorAlert;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

public abstract class LibraryItemFormController<T extends LibraryItem> implements ParametrizedController<LibraryItem>, Initializable
{

    // TODO: Add field for item condition
    // TODO: Add field for item categories

    private final Class<T> genericClass;
    @FXML
    public Button createButton;
    @FXML
    public Button updateButton;
    @FXML
    protected TextField name;
    @FXML
    protected TextField location;
    @FXML
    protected DatePicker createdOn;
    @FXML
    protected DatePicker releasedOn;
    @FXML
    protected CheckBox available;
    protected T editedItem;
    @InjectValue
    private LibraryItemFacade libraryItemFacade;

    public LibraryItemFormController(Class<T> genericClass)
    {
        this.genericClass = genericClass;
    }

    public void create()
    {
        LibraryItem item = getItemFormFields();
        try
        {
            this.libraryItemFacade.create(item);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("The library item " + item.getTitle() + " was successfully created.");
            alert.showAndWait();
        }
        catch (InternalException e)
        {
            e.printStackTrace();
            new InternalErrorAlert(e).showAndWait();
        }
    }

    public void update()
    {
        LibraryItem item = getItemFormFields();
        try
        {
            this.libraryItemFacade.update(item);
        }
        catch (InternalException e)
        {
            e.printStackTrace();
            new InternalErrorAlert(e).showAndWait();
        }
    }

    /**
     * This method is is called only when form is in a valid state.
     *
     * @return the item from the form's fields
     */
    protected abstract LibraryItem getItemFormFields();

    /**
     * Registers form constraints.
     *
     * @param builder the builder to use
     */
    protected void buildFormValidation(FormValidationBuilder builder)
    {
        builder.notEmpty(name.textProperty())
                .notEmpty(location.textProperty())
                .notNull(createdOn.valueProperty())
                .notNull(releasedOn.valueProperty());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handleViewParam(LibraryItem libraryItem)
    {
        if (genericClass.isInstance(libraryItem))
        {
            this.editedItem = (T) libraryItem;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        FormValidationBuilder builder = new FormValidationBuilder();
        buildFormValidation(builder);
        BooleanBinding formValid = builder.build();
        formValid.addListener((observable, oldValue, newValue) -> System.out.println("Form valid: " + newValue));
        this.createButton.disableProperty().bind(formValid.not());
        this.updateButton.disableProperty().bind(formValid.not());

        if (editedItem != null)
        {
            this.createButton.setVisible(false);

            this.name.setText(editedItem.getTitle());
            this.location.setText(editedItem.getLocation());
            this.createdOn.setValue(editedItem.getCreatedOn().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            this.releasedOn.setValue(editedItem.getReleasedOn().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        else
        {
            this.updateButton.setVisible(false);
        }
    }

}
