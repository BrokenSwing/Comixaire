package com.github.brokenswing.comixaire.view.util;

import com.github.brokenswing.comixaire.controller.util.ParametrizedController;
import com.github.brokenswing.comixaire.di.ControllerFactoryDI;
import com.github.brokenswing.comixaire.di.DependencyInjector;
import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import java.io.IOException;

/**
 * Class that helps to load a JavaFX elements from a FXML file.
 */
public class ViewLoader
{

    private final Callback<Class<?>, Object> controllerFactory;

    /**
     * @param di the dependency injector to use to inject dependencies in views' controllers
     */
    public ViewLoader(DependencyInjector di)
    {
        this.controllerFactory = new ControllerFactoryDI(di);
    }

    /**
     * Loads the FXML file represented by the given view then passes the given data to
     * the controller of the view. This also inject dependencies in the controller using
     * {@link DependencyInjector}.
     *
     * @param view the view to load
     * @param data the data to pass to the {@link ParametrizedController} T
     * @param <V>  the type of JavaFX element that is at the root of the FXML file that represented by the view
     * @param <T>  the type of the controller
     * @param <U>  the type of data that must be passed to the controller
     * @return the javafx node loaded from the FXML file
     * @see com.github.brokenswing.comixaire.di.InjectValue
     */
    @SuppressWarnings("unchecked")
    public <V, T extends ParametrizedController<U>, U> V loadView(ParametrizedView<T, U> view, U data)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(clazz ->
        {
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

    /**
     * Loads the FXML file represented by the given view.
     * It also inject dependencies in any controller required by the view, using
     * {@link DependencyInjector}.
     *
     * @param view the view to load
     * @param <T> the type of the root node of the FXML file
     * @return the JavaFX node loaded from the FXML file
     * @see com.github.brokenswing.comixaire.di.InjectValue
     */
    public <T> T loadView(View view)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(this.controllerFactory);
        try
        {
            loader.setLocation(ViewLoader.class.getClassLoader().getResource("views/" + view.getViewName()));
            return loader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException("Unable to load view " + view.getViewName(), e);
        }
    }

}
