package com.github.brokenswing.comixaire.di.impl;

import com.github.brokenswing.comixaire.di.DependencySource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Resolve dependencies by searching an args-less constructor
 * in the dependency class, and if it exists, it creates
 * an instance by calling the found constructor.
 */
public class ReflectionDependencySource implements DependencySource
{

    @Override
    public <T> Object resolve(Class<T> dependency)
    {
        Constructor<T> constructor = getArgsLessConstructor(dependency);
        if (constructor == null)
        {
            return null;
        }
        return createInstance(constructor);
    }

    @Override
    public int getPriority()
    {
        return 1000;
    }

    private <T> Constructor<T> getArgsLessConstructor(Class<T> dependency)
    {
        try
        {
            return dependency.getConstructor();
        }
        catch (NoSuchMethodException e)
        {
            return null;
        }
    }

    private <T> Object createInstance(Constructor<T> c)
    {
        try
        {

            boolean accessible = c.isAccessible();
            if (!accessible)
            {
                c.setAccessible(true);
            }

            Object instance = c.newInstance();

            if (!accessible)
            {
                c.setAccessible(false);
            }

            return instance;

        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            return null;
        }
    }

}
