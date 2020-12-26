package com.github.brokenswing.comixaire.controller.item;

import com.github.brokenswing.comixaire.controller.util.ParametrizedController;
import com.github.brokenswing.comixaire.javafx.IntField;
import com.github.brokenswing.comixaire.models.Book;
import com.github.brokenswing.comixaire.models.ConditionType;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.utils.FormValidationBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class BookFormController extends LibraryItemFormController<Book> implements ParametrizedController<LibraryItem>, Initializable
{

    @FXML
    public TextField author;
    @FXML
    public TextField isbn;
    @FXML
    public TextField publisher;
    @FXML
    public IntField pagesCount;

    public BookFormController()
    {
        super(Book.class);
    }

    @Override
    protected LibraryItem getItemFormFields()
    {
        int itemId = editedItem != null ? editedItem.getIdLibraryItem() : -1;
        Integer[] bookings = editedItem != null ? editedItem.getBookings() : new Integer[0];
        String[] categories = editedItem != null ? editedItem.getCategories() : new String[0];

        return new Book(
                itemId,
                name.getText(),
                ConditionType.NEW, // TODO: Change to form field value,
                location.getText(),
                Date.from(createdOn.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(releasedOn.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                bookings,
                categories, // TODO: Change to form field value
                available.isSelected(),
                author.getText(),
                isbn.getText(),
                publisher.getText(),
                pagesCount.getValue()
        );
    }

    @Override
    protected void buildFormValidation(FormValidationBuilder builder)
    {
        super.buildFormValidation(builder);
        builder.notEmpty(author.textProperty())
                .notEmpty(isbn.textProperty())
                .notEmpty(publisher.textProperty())
                .notNull(pagesCount.valueProperty());
        pagesCount.valueProperty().addListener((observable, oldValue, newValue) -> System.out.println(newValue));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        super.initialize(location, resources);
        if (this.editedItem != null)
        {
            this.createButton.setVisible(false);

            this.author.setText(this.editedItem.getAuthor());
            this.isbn.setText(this.editedItem.getISBN());
            this.publisher.setText(this.editedItem.getPublisher());
            this.pagesCount.setValue(this.editedItem.getPagesCount());
        }
        else
        {
            this.updateButton.setVisible(false);
        }
    }

}
