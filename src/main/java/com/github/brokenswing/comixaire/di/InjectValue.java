package com.github.brokenswing.comixaire.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a field must be populated when passing
 * the instance of an object to {@link DependencyInjector#inject(Object)}
 * method.<br>
 *
 * Which value will be injected in the field depends on the sources
 * that had been attached to the {@link DependencyInjector} using
 * {@link DependencyInjector#addSource(Object)}. The field
 * type must exactly match the return type of a method annotated
 * with {@link ValueProvider} of one of the sources.<br>
 *
 * @see DependencyInjector#inject(Object)
 * @see ValueProvider
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectValue
{
}
