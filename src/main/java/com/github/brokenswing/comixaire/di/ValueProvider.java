package com.github.brokenswing.comixaire.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated method provides
 * a value for the dependency injection system when set
 * a source using {@link DependencyInjector#addSource(Object)}.<br />
 *
 * This value provided by this method will be a candidate for
 * injection in a field annotated by {@link InjectValue} and that
 * has exactly the same type as the return type of the method when
 * a call to {@link DependencyInjector#inject(Object)} is done.<br />
 *
 * If multiple methods provides the same dependency in the same class,
 * then the first one will be picked.<br />
 *
 * A method annotated with this annotation MUST NOT return a null value.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueProvider
{
}
