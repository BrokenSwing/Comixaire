package com.github.brokenswing.comixaire.utils;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;

import java.util.Collection;
import java.util.LinkedList;

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
        return add(Bindings.isNotEmpty(obs));
    }

    public <T> FormValidationBuilder notNull(ObservableValue<T> obs)
    {
        return add(Bindings.createBooleanBinding(() -> obs.getValue() != null, obs));
    }

    public BooleanBinding build()
    {
        return validationBindings.stream().reduce(Bindings.createBooleanBinding(() -> true), BooleanExpression::and);
    }

}
