package com.github.brokenswing.comixaire.view.item;

import com.github.brokenswing.comixaire.controller.item.UpdateLibraryItemController;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.view.util.ParametrizedView;

public class UpdateLibraryItemView extends ParametrizedView<UpdateLibraryItemController, LibraryItem>
{

    public UpdateLibraryItemView()
    {
        super("update-item.fxml", UpdateLibraryItemController.class);
    }

}
