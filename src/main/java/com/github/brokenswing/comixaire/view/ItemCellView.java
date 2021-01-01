package com.github.brokenswing.comixaire.view;

import com.github.brokenswing.comixaire.controller.cell.ItemCellController;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.view.util.ParametrizedView;

public class ItemCellView extends ParametrizedView<ItemCellController, LibraryItem>
{

    public ItemCellView()
    {
        super("item-cell-view.fxml", ItemCellController.class);
    }

}
