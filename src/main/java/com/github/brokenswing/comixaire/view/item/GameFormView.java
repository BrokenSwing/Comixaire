package com.github.brokenswing.comixaire.view.item;

import com.github.brokenswing.comixaire.controller.item.GameFormController;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.view.util.ParametrizedView;

public class GameFormView extends ParametrizedView<GameFormController, LibraryItem>
{

    public GameFormView()
    {
        super("library-item/game-form.fxml", GameFormController.class);
    }

}
