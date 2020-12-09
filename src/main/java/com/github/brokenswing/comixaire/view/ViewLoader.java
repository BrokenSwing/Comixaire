package com.github.brokenswing.comixaire.view;

import com.github.brokenswing.comixaire.controller.ParametrizedController;
import com.github.brokenswing.comixaire.di.ControllerFactoryDI;
import com.github.brokenswing.comixaire.di.DependencyInjector;
import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import java.io.IOException;

public class ViewLoader
{

    private final Callback<Class<?>, Object> controllerFactory;

    public ViewLoader(DependencyInjector di)
    {
        this.controllerFactory = new ControllerFactoryDI(di);
    }

    public <V, T extends ParametrizedController<U>, U> V loadView(ParametrizedView<T, U> view, U data)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(clazz -> {
            Object controller = controllerFactory.call(clazz);
            if (view.getControllerClass().isAssignableFrom(controller.getClass()))
            {
                ParametrizedController<U> c = (ParametrizedController<U>) controller;
                c.handleViewParam(data);
            }
            return controller;
        });
        loader.setLocation(ViewLoader.class.getClassLoader().getResource("views/" + view.getViewName()));
        V node;
        try
        {
            node = loader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException("Unable to load view " + view.getViewName(), e);
        }

        return node;
    }

    public <T> T loadView(View view)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(this.controllerFactory);
        try
        {
            return loader.load(ViewLoader.class.getClassLoader().getResourceAsStream("views/" + view.getViewName()));
        }
        catch (IOException e)
        {
            throw new RuntimeException("Unable to load view " + view.getViewName(), e);
        }
    }

}
