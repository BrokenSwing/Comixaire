package com.github.brokenswing.comixaire.di.sources;

import com.github.brokenswing.comixaire.di.DependencySource;
import com.github.brokenswing.comixaire.di.ValueProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p>
 *      Resolves dependencies by scanning object methods.
 *      For a method to be eligible to be picked up, it must
 *      be compliant with the following conditions:
 * </p>
 *      <ul>
 *          <li>Be args-less</li>
 *          <li>Be annotated with the {@link ValueProvider} annotation</li>
 *          <li>Return the same type as the dependency to resolve</li>
 *      </ul>
 * <p>
 *      The first method that matches these requirements is called to retrieve the dependency value.
 * </p>
 *
 * @see ValueProvider
 */
public class AnnotatedDependencySource implements DependencySource
{

    private final Object source;

    public AnnotatedDependencySource(Object source)
    {
        this.source = source;
    }

    @Override
    public <T> Object resolve(Class<T> dependency)
    {
        Method providerMethod = getProviderMethodFromSource(dependency, source);
        if (providerMethod == null)
        {
            return null;
        }
        return getValueFromSourceMethod(dependency, source, providerMethod);
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

}
