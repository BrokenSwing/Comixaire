package com.github.brokenswing.comixaire.di;

/**
 * Represents a source that can resolve dependencies.
 * This class is used by the class {@link DependencyInjector}
 * to resolve dependencies.
 */
public interface DependencySource
{

    /**
     * Resolves given dependency (if possible). The returned
     * value should be an instance of the given class (or null).
     *
     * @param dependency The class of the dependency to resolve
     * @param <T>        the type to resolve
     * @return the value resolved for this dependency or null if dependency can't be resolved by this source
     */
    <T> Object resolve(Class<T> dependency);

    /**
     * <p>
     * Dependency sources are called in ascending order
     * based of their priority. The first dependency source
     * that resolves a non-null value is selected as the
     * dependency source.
     * </p>
     *
     * @return the priority for this dependency source
     */
    default int getPriority()
    {
        return 1;
    }

    /**
     * Indicates if the last returned dependency should
     * have its fields be resolved by the dependency injector
     * or not.
     *
     * @return true if last returned dependency's fields must be injected
     */
    default boolean injectRecursively()
    {
        return true;
    }

}
