package com.github.brokenswing.comixaire.view;

import com.github.brokenswing.comixaire.controller.cell.SubscriptionCellController;
import com.github.brokenswing.comixaire.models.Subscription;
import com.github.brokenswing.comixaire.view.util.ParametrizedView;

public class SubscriptionCellView extends ParametrizedView<SubscriptionCellController, Subscription>
{
    public SubscriptionCellView()
    {
        super("subscription-cell-view.fxml", SubscriptionCellController.class);
    }
}
