package com.github.brokenswing.comixaire.view.util;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class allows to change the current view displayed
 * in the application.
 */
public class Router
{

    private final Stage stage;
    private final ViewLoader viewLoader;

    /**
     * @param viewLoader   the loader to use to load views
     * @param primaryStage the stage to change Scene of when navigating
     */
    public Router(ViewLoader viewLoader, Stage primaryStage)
    {
        this.viewLoader = viewLoader;
        this.stage = primaryStage;
    }

    /**
     * Loads the given view using {@link ViewLoader#loadView(String, Object[])} then
     * sets the loaded views as the root scene of the stage that is
     * handled by this router.<br>
     * This method also centers the stage on the screen.
     *
     * @param viewPath the view to navigate to
     * @param args     the arguments to pass to the view
     */
    public void navigateTo(String viewPath, Object... args)
    {
        this.stage.setScene(new Scene(viewLoader.loadView(viewPath, args)));
        this.stage.centerOnScreen();
    }

}
