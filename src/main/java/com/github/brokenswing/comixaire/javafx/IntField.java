package com.github.brokenswing.comixaire.javafx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.WeakChangeListener;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.util.Objects;

// Found through : https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
// Stolen from : https://gist.github.com/jewelsea/1962045
// Modified by: BrokenSwing
public class IntField extends TextField
{

    private final IntegerProperty value = new SimpleIntegerProperty(this, "value", 0);
    private final IntegerProperty minValue = new SimpleIntegerProperty(this, "min", Integer.MIN_VALUE);
    private final IntegerProperty maxValue = new SimpleIntegerProperty(this, "max", Integer.MAX_VALUE);

    public IntField()
    {
        textProperty().bindBidirectional(valueProperty(), new StringConverter<Number>()
        {
            @Override
            public String toString(Number object)
            {
                return object == null ? "" : object.intValue() + "";
            }

            @Override
            public Number fromString(String string)
            {
                try
                {
                    return Integer.parseInt(string);
                }
                catch (NumberFormatException e)
                {
                    return null;
                }
            }
        });

        // make sure the value property is clamped to the required range
        // and update the field's text to be in sync with the value.
        value.addListener((observableValue, oldValue, newValue) ->
        {
            if (newValue == null)
            {
                return;
            }

            if (newValue.intValue() < minValue.intValue())
            {
                value.setValue(minValue.intValue());
            }
            else if (newValue.intValue() > maxValue.intValue())
            {
                value.setValue(maxValue.intValue());
            }
        });

        this.textProperty().addListener(new WeakChangeListener<>((obs, oldValue, newValue) -> {
            String valueToSet = applyStringCorrection(oldValue, newValue);
            if (!Objects.equals(valueToSet, newValue))
            {
                textProperty().set(valueToSet);
            }
        }));
    }

    private String applyStringCorrection(String oldValue, String newValue)
    {
        if (newValue == null || "".equals(newValue) || "-".equals(newValue))
        {
            return newValue;
        }

        try
        {
            int intValue = Integer.parseInt(newValue);

            if (intValue < minValue.intValue())
            {
                return String.valueOf(minValue.intValue());
            }

            if (intValue > maxValue.intValue())
            {
                return String.valueOf(maxValue.intValue());
            }

            return newValue;
        }
        catch (NumberFormatException e)
        {
            return oldValue;
        }
    }

    // expose an integer value property for the text field.
    public int getValue()
    {
        return value.getValue();
    }

    public void setValue(int newValue)
    {
        value.setValue(newValue);
    }

    public IntegerProperty valueProperty()
    {
        return value;
    }

    public IntegerProperty minValueProperty()
    {
        return this.minValue;
    }

    public IntegerProperty maxValueProperty()
    {
        return this.maxValue;
    }

    public int getMaxValue()
    {
        return maxValue.get();
    }

    public void setMaxValue(int value)
    {
        this.maxValue.setValue(value);
    }

    public int getMinValue()
    {
        return minValue.get();
    }

    public void setMinValue(int value)
    {
        this.minValue.setValue(value);
    }

}
