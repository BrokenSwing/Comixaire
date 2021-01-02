package com.github.brokenswing.comixaire.view;

import com.github.brokenswing.comixaire.controller.cell.FineCellController;
import com.github.brokenswing.comixaire.models.Fine;
import com.github.brokenswing.comixaire.view.util.ParametrizedView;

public class FineCellView extends ParametrizedView<FineCellController, Fine>
{
    public FineCellView()
    {
        super("fine-cell-view.fxml", FineCellController.class);
    }
}
