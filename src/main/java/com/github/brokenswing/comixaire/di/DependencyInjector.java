package com.github.brokenswing.comixaire.di;

import com.github.brokenswing.comixaire.di.impl.CacheDependencySource;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.stream.Stream;

/**
 * <p>
 * Singleton that manages the dependency injection in the system.
 * This class is mainly to inject dependencies in JavaFX controller
 * through the controllers factory.
 * </p>
 *
 * <p>
 * Calling {@link #inject(Object)} will inject values in the fields
 * of the given object that are annotated with {@link InjectValue}
 * resolving those values from the sources added through
 * {@link #addDependencyResolver(DependencySource)}.
 * </p>
 *
 * @see InjectValue
 * @see DependencySource
 */
public class DependencyInjector
{

    private static DependencyInjector instance = null;

    private final Collection<DependencySource> sources =
            new PriorityQueue<>(Comparator.comparing(DependencySource::getPriority).reversed());

    private final CacheDependencySource cache = new CacheDependencySource();

    private DependencyInjector()
    {
        this.sources.add(cache);
    }

    /**
     * Returns the instance of the singleton. <br>
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
     * Add a source to resolve dependencies from.<br>
     * When trying to resolve a dependency and that a cache-miss happens,
     * the sources are inspected in the same order as they were added to
     * the dependency injection system through this method.
     *
     * @param source an instance of a class providing dependencies values
     */
    public void addDependencyResolver(DependencySource source)
    {
        this.sources.add(source);
    }

    /**
     * Injects dependencies in the given object.<br>
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

    private void injectFieldsValues(Object injectionTarget)
    {
        getInjectableFields(injectionTarget).forEach(field -> injectFieldValue(field, injectionTarget));
    }

    private Stream<Field> getInjectableFields(Object injectionTarget)
    {
        Stream.Builder<Field> s = Stream.builder();
        Class<?> currentClass = injectionTarget.getClass();
        while (!currentClass.equals(Object.class))
        {
            for (Field f : currentClass.getDeclaredFields())
            {
                if (mustFieldBeInjected(f))
                {
                    s.add(f);
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        return s.build();
    }

    private Object getValueFromSources(Class<?> dependency)
    {
        Iterator<DependencySource> it = this.sources.iterator();
        Object dependencyInstance = null;
        DependencySource source = null;
        while (dependencyInstance == null && it.hasNext())
        {
            source = it.next();
            dependencyInstance = source.resolve(dependency);
        }

        if (dependencyInstance == null)
        {
            throw new IllegalStateException(String.format("Dependency %s can't be resolved.", dependency.getSimpleName()));
        }

        cache.addToCache(dependency, dependencyInstance);

        if (source.injectRecursively())
        {
            inject(dependencyInstance);
        }

        return dependencyInstance;
    }

    private void injectFieldValue(Field field, Object instance)
    {
        Class<?> valueClass = field.getType();

        boolean wasAccessible = field.isAccessible();
        if (!wasAccessible)
        {
            field.setAccessible(true);
        }

        Object value = getValueFromSources(valueClass);
        try
        {
            field.set(instance, value);
        }
        catch (IllegalAccessException e)
        {
            throw new IllegalStateException(String.format(
                    "Unable to inject value %s in field %s of controller %s",
                    valueClass.getCanonicalName(),
                    field.getName(),
                    instance.getClass().getCanonicalName()
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
