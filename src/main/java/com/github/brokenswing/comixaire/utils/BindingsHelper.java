package com.github.brokenswing.comixaire.utils;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableStringValue;

public class BindingsHelper
{

    private BindingsHelper()
    {
    }

    public static StringBinding trimmed(ObservableStringValue obs)
    {
        return Bindings.createStringBinding(() -> obs.get() == null ? "" : obs.get().trim(), obs);
    }

}
