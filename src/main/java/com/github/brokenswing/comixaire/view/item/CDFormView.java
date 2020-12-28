package com.github.brokenswing.comixaire.view.item;

import com.github.brokenswing.comixaire.controller.item.CDFormController;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.view.util.ParametrizedView;

public class CDFormView extends ParametrizedView<CDFormController, LibraryItem>
{

    public CDFormView()
    {
        super("library-item/cd-form.fxml", CDFormController.class);
    }

}
