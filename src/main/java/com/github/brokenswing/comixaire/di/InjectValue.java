package com.github.brokenswing.comixaire.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a field must be populated when passing
 * the instance of an object to {@link DependencyInjector#inject(Object)}
 * method of the main dependency injector.<br>
 *
 * Which value will be injected in the field depends on the sources
 * that had been attached to the {@link DependencyInjector} using
 * {@link DependencyInjector#addDependencyResolver(DependencySource)}.
 *
 * @see DependencyInjector#inject(Object)
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectValue
{
}
