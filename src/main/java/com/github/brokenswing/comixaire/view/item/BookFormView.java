package com.github.brokenswing.comixaire.view.item;

import com.github.brokenswing.comixaire.controller.item.BookFormController;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.view.util.ParametrizedView;

public class BookFormView extends ParametrizedView<BookFormController, LibraryItem>
{

    public BookFormView()
    {
        super("library-item/book-form.fxml", BookFormController.class);
    }

}
