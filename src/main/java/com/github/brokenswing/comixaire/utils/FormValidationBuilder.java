package com.github.brokenswing.comixaire.utils;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import static com.github.brokenswing.comixaire.utils.BindingsHelper.trimmed;

public class FormValidationBuilder
{

    private final Collection<BooleanBinding> validationBindings = new LinkedList<>();

    public FormValidationBuilder add(BooleanBinding binding)
    {
        this.validationBindings.add(binding);
        return this;
    }

    public FormValidationBuilder notEmpty(ObservableStringValue obs)
    {
        return notEmpty(obs, true);
    }

    public FormValidationBuilder notEmpty(ObservableStringValue obs, boolean trim)
    {
        return add(Bindings.isNotEmpty(trim ? trimmed(obs) : obs));
    }

    public <T> FormValidationBuilder notNull(ObservableValue<T> obs)
    {
        return add(Bindings.createBooleanBinding(() -> obs.getValue() != null, obs));
    }

    public <T> FormValidationBuilder notIn(ObservableValue<T> obs, ObservableList<T> list)
    {
        return add(Bindings.createBooleanBinding(() -> !list.contains(obs.getValue()), obs, list));
    }

    public <T> FormValidationBuilder notIn(ObservableValue<T> obs, ObservableList<T> list, BiFunction<T, T, Boolean> matcher)
    {
        Predicate<T> predicate = (v) -> matcher.apply(obs.getValue(), v);
        return add(Bindings.createBooleanBinding(
                () -> list.stream().noneMatch(predicate),
                obs, list
        ));
    }

    public BooleanBinding build()
    {
        return validationBindings.stream().reduce(Bindings.createBooleanBinding(() -> true), BooleanExpression::and);
    }

}
