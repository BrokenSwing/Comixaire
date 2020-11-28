package com.github.brokenswing.comixaire.view;

import com.github.brokenswing.comixaire.Configuration;
import com.github.brokenswing.comixaire.di.ControllerFactoryDI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;

import java.io.IOException;

public class ViewLoader
{

    private static ViewLoader instance = null;

    public static ViewLoader getInstance()
    {
        if (instance == null)
        {
            instance = new ViewLoader();
        }

        return instance;
    }

    private final Callback<Class<?>, Object> controllerFactory;

    private ViewLoader()
    {
        this.controllerFactory = new ControllerFactoryDI(createConfiguration());
    }

    private Object createConfiguration()
    {
        return new Configuration();
    }

    public Parent loadView(String viewName) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(this.controllerFactory);
        return loader.load(ViewLoader.class.getClassLoader().getResourceAsStream("views/" + viewName));
    }

}
