package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.view.util.ParametrizedView;
import com.github.brokenswing.comixaire.view.util.ViewLoader;

/**
 * This interface must be implemented by controllers that need
 * to receive data that is passed to the view their associated to.<br>
 *
 * Example:
 *
 * A view displaying a single item wants to receive the id of
 * the item to display. In this case this view must implement
 * {@link ParametrizedView}
 * and specify a controller implementing this interface.
 * When the view will be loaded through
 * {@link ViewLoader#loadView(ParametrizedView, Object)}
 * the data that you passed to the loader will be passed to the controller that is
 * associated with the view.<br>
 *
 * @see ParametrizedView
 * @see ViewLoader
 *
 * @param <T> the type of the data to receive
 */
public interface ParametrizedController<T>
{

    /**
     * Accepts the data that is passed when creating
     * the view associated with this controller.<br>
     * This method is called BEFORE {@link javafx.fxml.FXML}
     * annotated fields are populated.
     *
     * @param param the passed data.
     */
    void handleViewParam(T param);

}
