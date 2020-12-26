package com.github.brokenswing.comixaire.view;

import com.github.brokenswing.comixaire.controller.LogCellController;
import com.github.brokenswing.comixaire.models.Log;
import com.github.brokenswing.comixaire.view.util.ParametrizedView;

public class LogCellView extends ParametrizedView<LogCellController, Log>
{

    public LogCellView()
    {
        super("log-cell-view.fxml", LogCellController.class);
    }

}
