package com.github.brokenswing.comixaire.view;

import com.github.brokenswing.comixaire.controller.ParametrizedController;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class allows to change the current {@link View} displayed
 * in the application.
 */
public class Router
{

    private final Stage stage;
    private final ViewLoader viewLoader;

    /**
     * @param viewLoader the loader to use to load views
     * @param primaryStage the stage to change Scene of when navigating
     */
    public Router(ViewLoader viewLoader, Stage primaryStage)
    {
        this.viewLoader = viewLoader;
        this.stage = primaryStage;
    }

    /**
     * Loads the given views using {@link ViewLoader#loadView(ParametrizedView, Object)}
     * forwarding the given data to the method call.
     * Then this method sets the loaded view as the root scene of the
     * stage that is handled by this router.<br />
     * This method also centers the stage on the screen.
     *
     * @param view the view to navigate to
     * @param data the data to pass to the controller of the view
     * @param <T> the type of the controller
     * @param <U> the type of data to pass to the controller
     */
    public <T extends ParametrizedController<U>, U> void navigateTo(ParametrizedView<T, U> view, U data)
    {
        this.stage.setScene(new Scene(viewLoader.loadView(view, data)));
        this.stage.centerOnScreen();
    }

    /**
     * Loads the given view using {@link ViewLoader#loadView(View)} then
     * sets the loaded views as the root scene of the stage that is
     * handled by this router.<br />
     * This method also centers the stage on the screen.
     *
     * @param view the view to navigate to
     */
    public void navigateTo(View view)
    {
        this.stage.setScene(new Scene(viewLoader.loadView(view)));
        this.stage.centerOnScreen();
    }

}
