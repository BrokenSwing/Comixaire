package com.github.brokenswing.comixaire.view.item;

import com.github.brokenswing.comixaire.controller.item.DVDFormController;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.view.util.ParametrizedView;

public class DVDFormView extends ParametrizedView<DVDFormController, LibraryItem>
{

    public DVDFormView()
    {
        super("library-item/dvd-form.fxml", DVDFormController.class);
    }

}
