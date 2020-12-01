package com.github.brokenswing.comixaire.di;

import javafx.util.Callback;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ControllerFactoryDI implements Callback<Class<?>, Object>
{

    private final Object config;
    private final HashMap<Class<?>, Object> injectionCache = new HashMap<>();

    public ControllerFactoryDI(Object config)
    {
        this.config = config;
    }

    /**
     * Tries to retrieve the value from the injection cache.
     * If the map does not contain the instance, then tries to retrieve it
     * from configuration class.
     *
     * @param valueClass The class of the value to retrieve
     * @return an instance of the value class
     */
    private Object getOrFetchValueFromConfig(Class<?> valueClass)
    {
        if (injectionCache.containsKey(valueClass))
        {
            return injectionCache.get(valueClass);
        }

        Object valueToInject = fetchValueFromConfig(valueClass);
        injectionCache.put(valueClass, valueToInject);
        return valueToInject;
    }

    /**
     * Retrieves the value from the configuration class.
     *
     * @param valueClass The value class to retrieve an instance of
     * @return an instance of the provided value class
     */
    private Object fetchValueFromConfig(Class<?> valueClass)
    {
        Method providerMethod = null;
        Method[] classPublicMethods = this.config.getClass().getMethods();
        for (int i = 0; i < classPublicMethods.length && providerMethod == null; i++)
        {
            Method m = classPublicMethods[i];
            if (m.isAnnotationPresent(ValueProvider.class) && m.getReturnType().equals(valueClass))
            {
                providerMethod = m;
            }
        }

        if (providerMethod == null)
        {
            throw new IllegalStateException("Value " + valueClass.getCanonicalName() +
                    " can't be injected because no injection method exists in configuration class.");
        }

        if (providerMethod.getParameterCount() != 0)
        {
            throw new IllegalStateException(String.format(
                    "%s annotated method must have 0 arguments. Can't inject %s.",
                    ValueProvider.class.getSimpleName(),
                    valueClass.getCanonicalName()
            ));
        }

        try
        {
            return providerMethod.invoke(this.config);
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            throw new IllegalStateException(String.format(
                    "Can't inject %s class. Unable to call %s annotated method %s of class %s.",
                    valueClass.getCanonicalName(),
                    ValueProvider.class.getSimpleName(),
                    providerMethod.getName(),
                    config.getClass().getCanonicalName()
            ), e);
        }
    }

    /**
     * The <code>call</code> method is called when required, and is given a
     * single argument of type P, with a requirement that an object of type R
     * is returned.
     *
     * @param controllerClass The single argument upon which the returned value should be
     *                        determined.
     * @return An object of type R that may be determined based on the provided
     * parameter value.
     */
    @Override
    public Object call(Class<?> controllerClass)
    {
        Object controllerInstance = createController(controllerClass);
        injectFieldsValues(controllerClass, controllerInstance);
        return controllerInstance;
    }

    private void injectFieldsValues(Class<?> controllerClazz, Object controllerInstance)
    {
        for (Field field : controllerClazz.getDeclaredFields())
        {
            if (mustFieldBeInjected(field))
            {
                injectFieldValue(field, controllerInstance);
            }
        }
    }

    private void injectFieldValue(Field field, Object controllerInstance)
    {
        Class<?> valueClass = field.getType();

        if (!field.isAccessible())
        {
            field.setAccessible(true);
        }

        Object value = getOrFetchValueFromConfig(valueClass);
        try
        {
            field.set(controllerInstance, value);
        }
        catch (IllegalAccessException e)
        {
            throw new IllegalStateException(String.format(
                    "Unable to inject value %s in field %s of controller %s",
                    valueClass.getCanonicalName(),
                    field.getName(),
                    controllerInstance.getClass().getCanonicalName()
            ), e);
        }
    }

    private boolean mustFieldBeInjected(Field field)
    {
        return field.isAnnotationPresent(InjectValue.class);
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

}
