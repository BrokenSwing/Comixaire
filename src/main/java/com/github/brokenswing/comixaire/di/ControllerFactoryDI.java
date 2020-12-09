package com.github.brokenswing.comixaire.di;

import javafx.util.Callback;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ControllerFactoryDI implements Callback<Class<?>, Object>
{

    private DependencyInjector di;

    public ControllerFactoryDI(DependencyInjector di)
    {
        this.di = di;
    }

    private Object createController(Class<?> controllerClazz)
    {
        try
        {
            Constructor<?> c = controllerClazz.getConstructor();
            return c.newInstance();
        }
        catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            throw new IllegalStateException(String.format(
                    "Unable to create an instance of %s controller. Controllers must have a public args-less constructor.",
                    controllerClazz.getCanonicalName()
            ), e);
        }
    }

    @Override
    public Object call(Class<?> controllerClass)
    {
        Object controller = createController(controllerClass);
        this.di.inject(controller);
        return controller;
    }

}
