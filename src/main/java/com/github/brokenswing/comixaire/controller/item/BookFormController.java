package com.github.brokenswing.comixaire.controller.item;

import com.github.brokenswing.comixaire.controller.util.ParametrizedController;
import com.github.brokenswing.comixaire.javafx.IntField;
import com.github.brokenswing.comixaire.models.Book;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.builder.LibraryItemBuilder;
import com.github.brokenswing.comixaire.models.builder.LibraryItemStep;
import com.github.brokenswing.comixaire.utils.FormValidationBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
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
        LibraryItemStep builder = editedItem == null ? LibraryItemBuilder.create() : LibraryItemBuilder.from(editedItem);
        super.populateBuilder(builder);
        return builder.book()
                .author(author.getText().trim())
                .isbn(isbn.getText().trim())
                .publisher(publisher.getText().trim())
                .pagesCount(pagesCount.getValue())
                .build();
    }

    @Override
    protected void buildFormValidation(FormValidationBuilder builder)
    {
        super.buildFormValidation(builder);
        builder.notEmpty(author.textProperty())
                .notEmpty(isbn.textProperty())
                .notEmpty(publisher.textProperty())
                .notNull(pagesCount.valueProperty());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        super.initialize(location, resources);
        if (this.editedItem != null)
        {
            this.author.setText(this.editedItem.getAuthor());
            this.isbn.setText(this.editedItem.getISBN());
            this.publisher.setText(this.editedItem.getPublisher());
            this.pagesCount.setValue(this.editedItem.getPagesCount());
        }
    }

}
