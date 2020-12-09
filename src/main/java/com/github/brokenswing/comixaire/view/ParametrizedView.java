package com.github.brokenswing.comixaire.view;

import com.github.brokenswing.comixaire.controller.ParametrizedController;

public class ParametrizedView<T extends ParametrizedController<U>, U> extends View
{

    private final Class<T> controllerClass;

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
