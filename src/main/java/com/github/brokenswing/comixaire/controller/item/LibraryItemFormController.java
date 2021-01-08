package com.github.brokenswing.comixaire.controller.item;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.item.LibraryItemFacade;
import com.github.brokenswing.comixaire.javafx.Alerts;
import com.github.brokenswing.comixaire.models.ConditionType;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.builder.LibraryItemStep;
import com.github.brokenswing.comixaire.utils.FormValidationBuilder;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.alert.InternalErrorAlert;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.github.brokenswing.comixaire.utils.BindingsHelper.trimmed;

public abstract class LibraryItemFormController<T extends LibraryItem> implements Initializable
{

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
    protected ChoiceBox<ConditionType> condition;
    @FXML
    protected CheckComboBox<String> categories;
    @ViewParam
    protected T editedItem;
    @InjectValue
    protected LibraryItemFacade libraryItemFacade;
    @InjectValue
    protected Router router;

    public LibraryItemFormController(Class<T> genericClass)
    {
        this.genericClass = genericClass;
    }

    protected static Date fromLocalDate(LocalDate date)
    {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    protected void populateBuilder(LibraryItemStep builder)
    {
        builder.available(true)
                .title(name.getText().trim())
                .location(location.getText().trim())
                .createdOn(fromLocalDate(createdOn.getValue()))
                .releasedOn(fromLocalDate(releasedOn.getValue()))
                .condition(condition.getValue())
                .categories(categories.getCheckModel().getCheckedItems().toArray(new String[0]));
    }

    public void create()
    {
        LibraryItem item = getItemFromFields();
        try
        {
            this.libraryItemFacade.create(item);
            Alerts.success("The library item \"" + item.getTitle() + "\" was successfully created.");
            router.navigateTo(Views.ActionCenters.STAFF);
        }
        catch (InternalException e)
        {
            e.printStackTrace();
            Alerts.exception(e);
        }
    }

    public void update()
    {
        LibraryItem item = getItemFromFields();
        try
        {
            this.libraryItemFacade.update(item);
            Alerts.success("The library item \"" + item.getTitle() + "\" was successfully updated.");
        }
        catch (InternalException e)
        {
            Alerts.exception(e);
        }
        router.navigateTo(Views.LibraryItems.LIST);
    }

    /**
     * This method is called only when form is in a valid state.
     *
     * @return the item from the form's fields
     */
    protected abstract LibraryItem getItemFromFields();

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

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        if (!genericClass.isInstance(editedItem))
        {
            editedItem = null;
        }

        FormValidationBuilder builder = new FormValidationBuilder();
        buildFormValidation(builder);
        BooleanBinding formValid = builder.build();
        this.createButton.disableProperty().bind(formValid.not());
        this.updateButton.disableProperty().bind(formValid.not());

        // Populate eligible values

        this.condition.setItems(FXCollections.observableArrayList(ConditionType.values()));
        try
        {
            this.categories.getItems().addAll(Arrays.asList(this.libraryItemFacade.getKnownCategories()));
        }
        catch (InternalException e)
        {
            e.printStackTrace();
        }

        if (editedItem != null)
        {
            this.createButton.setVisible(false);

            this.name.setText(editedItem.getTitle());
            this.location.setText(editedItem.getLocation());
            this.createdOn.setValue(editedItem.getCreatedOn().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            this.releasedOn.setValue(editedItem.getReleasedOn().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            this.condition.setValue(editedItem.getCondition());
            for (String category : editedItem.getCategories())
            {
                this.categories.getCheckModel().check(category);
            }
        }
        else
        {
            this.updateButton.setVisible(false);
            this.condition.setValue(ConditionType.NEW);
        }

    }

    public void addCategory()
    {
        Dialog<ButtonType> d = new Dialog<>();
        d.setTitle("Add category");
        d.setHeaderText("Enter the name of the category to add :");

        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType addButton = new ButtonType("Add category", ButtonBar.ButtonData.OK_DONE);

        d.getDialogPane().getButtonTypes().add(cancelButton);
        d.getDialogPane().getButtonTypes().add(addButton);

        TextField categoryNameField = new TextField();
        categoryNameField.setPromptText("Category name");

        d.getDialogPane().setGraphic(categoryNameField);

        BooleanBinding formValid = new FormValidationBuilder()
                .notEmpty(categoryNameField.textProperty())
                .notIn(trimmed(categoryNameField.textProperty()), categories.getItems(), String::equalsIgnoreCase)
                .build();

        d.getDialogPane().lookupButton(addButton).disableProperty().bind(formValid.not());

        Optional<ButtonType> result = d.showAndWait();
        if (result.isPresent() && result.get() == addButton)
        {
            categories.getItems().add(categoryNameField.getText().trim());
            // Maybe uncomment because we want the new category to be checked, but leads to weird checkboxes issues :
            // categories.getCheckModel().toggleCheckState(categoryNameField.getText().trim());
        }
    }

}
