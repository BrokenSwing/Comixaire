package com.github.brokenswing.comixaire.javafx;

import com.github.brokenswing.comixaire.controller.util.ParametrizedController;
import com.github.brokenswing.comixaire.view.util.ParametrizedView;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.function.Supplier;

public class CustomListCell<T> extends ListCell<T>
{

    private final ViewLoader loader;
    private final Supplier<ParametrizedView<? extends ParametrizedController<T>, T>> view;

    public CustomListCell(ViewLoader loader, Supplier<ParametrizedView<? extends ParametrizedController<T>, T>> view)
    {
        this.loader = loader;
        this.view = view;
    }

    public static <T> Callback<ListView<T>, ListCell<T>> factory(ViewLoader loader, Supplier<ParametrizedView<? extends ParametrizedController<T>, T>> viewSupplier)
    {
        return (list) -> new CustomListCell<>(loader, viewSupplier);
    }

    @Override
    protected void updateItem(T item, boolean empty)
    {
        super.updateItem(item, empty);
        if (empty)
        {
            setText(null);
            setGraphic(null);
        }
        else
        {
            Node node = loader.loadView(view.get(), item);
            setGraphic(node);
        }
    }

}
