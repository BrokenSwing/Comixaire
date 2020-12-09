package com.github.brokenswing.comixaire.di;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Singleton that manages the dependency injection in the system.
 * This class is mainly to inject dependencies in JavaFX controller
 * through the controllers factory.<br />
 * <p>
 * Calling {@link #inject(Object)} will inject values in the fields
 * of the given object that are annotated with {@link InjectValue}
 * getting those values in the sources objects added through
 * {@link #addSource(Object)}. This sources objects provides
 * values through methods annotated with {@link ValueProvider}.<br />
 * <p>
 * Any injected by this dependency injection system is a singleton,
 * even if methods of the sources return a new instance at each call,
 * because values provided by these sources are cached and reused
 * instead of recalling sources methods.
 *
 * @see InjectValue
 * @see ValueProvider
 */
public class DependencyInjector
{

    private static DependencyInjector instance = null;

    private final List<Object> sources;
    private final HashMap<Class<?>, Object> injectionCache = new HashMap<>();

    private DependencyInjector()
    {
        this.sources = new LinkedList<>();
    }

    /**
     * Returns the instance of the singleton. <br />
     * The access to this instance is not thread-safe.
     */
    public static DependencyInjector getInstance()
    {
        if (instance == null)
        {
            instance = new DependencyInjector();
        }
        return instance;
    }

    /**
     * Add a source to get dependencies values from.<br />
     * In order to provide values, the source must have methods
     * that are annotated with {@link ValueProvider} annotation.
     * If multiple methods in the same source object provides the same
     * class, then the first method is used.<br />
     * When trying to resolve a dependency and that a cache-miss happens,
     * the sources are inspected in the same order as they were added to
     * the dependency injection system through this method.
     *
     * @param source an instance of a class providing dependencies values
     */
    public void addSource(Object source)
    {
        this.sources.add(source);
    }

    /**
     * Injects dependencies in the given object.<br />
     * This fields annotated with {@link InjectValue} then
     * tries to find a value that fits the type. If no value
     * can be found be to injected, a {@link IllegalStateException}
     * is thrown.
     *
     * @param injectionTarget the object to inject dependencies to
     */
    public void inject(Object injectionTarget)
    {
        injectFieldsValues(injectionTarget);
    }

    private Object getValueFromCache(Class<?> valueClass)
    {
        return injectionCache.get(valueClass);
    }

    private Object getValueFromSources(Class<?> valueClass)
    {
        Object value = null;
        Iterator<Object> it = this.sources.listIterator();
        while (it.hasNext() && value == null)
        {
            Object source = it.next();
            Method providerMethod = getProviderMethodFromSource(valueClass, source);

            if (providerMethod != null)
            {
                value = getValueFromSourceMethod(valueClass, source, providerMethod);
                injectionCache.put(valueClass, value);
            }
        }

        if (value == null)
        {
            throw new IllegalStateException("Value " + valueClass.getCanonicalName() +
                    " can't be injected because no injection method exists in source classes.");
        }

        return value;
    }

    private Object getValueFromSourceMethod(Class<?> valueClass, Object source, Method providerMethod)
    {
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
            return providerMethod.invoke(source);
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            throw new IllegalStateException(String.format(
                    "Can't inject %s class. Unable to call %s annotated method %s of class %s.",
                    valueClass.getCanonicalName(),
                    ValueProvider.class.getSimpleName(),
                    providerMethod.getName(),
                    source.getClass().getCanonicalName()
            ), e);
        }
    }

    private Method getProviderMethodFromSource(Class<?> valueClass, Object source)
    {
        Method providerMethod = null;
        Method[] classPublicMethods = source.getClass().getMethods();
        for (int i = 0; i < classPublicMethods.length && providerMethod == null; i++)
        {
            Method m = classPublicMethods[i];
            if (m.isAnnotationPresent(ValueProvider.class) && m.getReturnType().equals(valueClass))
            {
                providerMethod = m;
            }
        }

        return providerMethod;
    }

    private Object getValueFromCacheOrSources(Class<?> valueClass)
    {
        Object value = getValueFromCache(valueClass);
        if (value == null)
        {
            value = getValueFromSources(valueClass);
        }
        return value;
    }

    private void injectFieldsValues(Object injectionTarget)
    {
        for (Field field : injectionTarget.getClass().getDeclaredFields())
        {
            if (mustFieldBeInjected(field))
            {
                injectFieldValue(field, injectionTarget);
            }
        }
    }

    private void injectFieldValue(Field field, Object controllerInstance)
    {
        Class<?> valueClass = field.getType();

        boolean wasAccessible = field.isAccessible();
        if (!wasAccessible)
        {
            field.setAccessible(true);
        }

        Object value = getValueFromCacheOrSources(valueClass);
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

        if (!wasAccessible)
        {
            field.setAccessible(false);
        }
    }

    private boolean mustFieldBeInjected(Field field)
    {
        return field.isAnnotationPresent(InjectValue.class);
    }

}
