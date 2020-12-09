package com.github.brokenswing.comixaire.view;

import com.github.brokenswing.comixaire.controller.ParametrizedController;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Router
{

    private ViewLoader viewLoader;
    private final Stage stage;

    public Router(ViewLoader viewLoader, Stage primaryStage)
    {
        this.viewLoader = viewLoader;
        this.stage = primaryStage;
    }

    public <T extends ParametrizedController<U>, U> void navigateTo(ParametrizedView<T, U> view, U data)
    {
        this.stage.setScene(new Scene(viewLoader.loadView(view, data)));
        this.stage.centerOnScreen();
    }

    public void navigateTo(View view)
    {
        this.stage.setScene(new Scene(viewLoader.loadView(view)));
        this.stage.centerOnScreen();
    }

}
