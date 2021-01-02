package com.github.brokenswing.comixaire.view;

import com.github.brokenswing.comixaire.controller.cell.RecommendedItemCellController;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.view.util.ParametrizedView;

public class RecommendedItemCellView extends ParametrizedView<RecommendedItemCellController, LibraryItem>
{

    public RecommendedItemCellView()
    {
        super("recommended-item-cell-view.fxml", RecommendedItemCellController.class);
    }

}
