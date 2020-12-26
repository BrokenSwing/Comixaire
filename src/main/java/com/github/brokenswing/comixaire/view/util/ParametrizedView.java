package com.github.brokenswing.comixaire.view.util;

import com.github.brokenswing.comixaire.controller.ParametrizedController;

/**
 * Represents a {@link View} that has a controller that needs a data
 * to be passed to it when loaded by {@link ViewLoader}.
 *
 * @param <T> the type of the controller that needs data
 * @param <U> the data to be passed to the controller
 */
public class ParametrizedView<T extends ParametrizedController<U>, U> extends View
{

    private final Class<T> controllerClass;

    /**
     * @param viewName        the name of the FXML file for this view
     * @param controllerClass the class of the controller the data will be passed
     */
    public ParametrizedView(String viewName, Class<T> controllerClass)
    {
        super(viewName);
        this.controllerClass = controllerClass;
    }

    public Class<T> getControllerClass()
    {
        return controllerClass;
    }

}
