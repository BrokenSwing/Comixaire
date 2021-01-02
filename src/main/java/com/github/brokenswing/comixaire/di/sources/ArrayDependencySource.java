package com.github.brokenswing.comixaire.di.sources;

import com.github.brokenswing.comixaire.di.DependencySource;

public class ArrayDependencySource implements DependencySource
{

    private final Object[] objects;

    public ArrayDependencySource(Object[] objects)
    {
        this.objects = objects;
    }

    @Override
    public <T> Object resolve(Class<T> dependency)
    {
        for (Object o : objects)
        {
            if (dependency.isAssignableFrom(o.getClass()))
            {
                return o;
            }
        }
        return null;
    }

    @Override
    public boolean injectRecursively()
    {
        return false;
    }

}
