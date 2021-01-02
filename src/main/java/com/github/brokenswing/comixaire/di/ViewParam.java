package com.github.brokenswing.comixaire.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the field must be populated
 * with one of the values passed through
 * {@link com.github.brokenswing.comixaire.view.util.ViewLoader#loadView(String, Object...)}.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewParam
{
}
