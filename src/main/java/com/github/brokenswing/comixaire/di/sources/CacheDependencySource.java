package com.github.brokenswing.comixaire.di.sources;

import com.github.brokenswing.comixaire.di.DependencySource;

import java.util.HashMap;
import java.util.Map;

public class CacheDependencySource implements DependencySource
{

    private final Map<Class<?>, Object> cache = new HashMap<>();

    public void addToCache(Class<?> dependency, Object instance)
    {
        this.cache.putIfAbsent(dependency, instance);
    }

    @Override
    public <T> Object resolve(Class<T> dependency)
    {
        return cache.get(dependency);
    }

    @Override
    public int getPriority()
    {
        return 0;
    }

    @Override
    public boolean injectRecursively()
    {
        return false;
    }

}
