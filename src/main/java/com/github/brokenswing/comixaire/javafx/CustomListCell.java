package com.github.brokenswing.comixaire.javafx;

import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class CustomListCell<T> extends ListCell<T>
{

    private final ViewLoader loader;
    private final String viewPath;

    public CustomListCell(ViewLoader loader, String viewPath)
    {
        this.loader = loader;
        this.viewPath = viewPath;
    }

    public static <T> Callback<ListView<T>, ListCell<T>> factory(ViewLoader loader, String viewPath)
    {
        return (list) -> new CustomListCell<>(loader, viewPath);
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
            Node node = loader.loadView(viewPath, item);
            setGraphic(node);
        }
    }

}
