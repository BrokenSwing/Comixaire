package com.github.brokenswing.comixaire.view.util;

import com.github.brokenswing.comixaire.di.ControllerFactoryDI;
import com.github.brokenswing.comixaire.di.DependencyInjector;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.di.sources.ArrayDependencySource;
import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import java.io.IOException;

/**
 * Class that helps to load a JavaFX elements from a FXML file.
 */
public class ViewLoader
{

    private final Callback<Class<?>, Object> baseControllerFactory;

    /**
     * @param di the dependency injector to use to inject dependencies in views' controllers
     */
    public ViewLoader(DependencyInjector di)
    {
        this.baseControllerFactory = new ControllerFactoryDI(di);
    }

    /**
     * Loads the FXML file represented by the given view.
     * It also inject dependencies in any controller required by the view, using
     * {@link DependencyInjector}s.
     *
     * @param viewPath the path to the view to load
     * @param values the values to pass the controller of the given view
     * @param <T>      the type of the root node of the FXML file
     * @return the JavaFX node loaded from the FXML file
     * @see com.github.brokenswing.comixaire.di.InjectValue
     * @see com.github.brokenswing.comixaire.di.ViewParam
     */
    public <T> T loadView(String viewPath, Object... values)
    {
        FXMLLoader loader = new FXMLLoader();

        loader.setControllerFactory(controllerClass ->
        {
            Object controllerInstance = this.baseControllerFactory.call(controllerClass);
            DependencyInjector di = new DependencyInjector(ViewParam.class, true);
            di.addDependencyResolver(new ArrayDependencySource(values));
            di.inject(controllerInstance);
            return controllerInstance;
        });

        try
        {
            loader.setLocation(ViewLoader.class.getClassLoader().getResource("views/" + viewPath));
            return loader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException("Unable to load view " + viewPath, e);
        }
    }

}
