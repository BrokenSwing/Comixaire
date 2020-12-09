package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.view.ParametrizedView;

/**
 * This interface must be implemented by controllers that need
 * to receive data that is passed to the view their associated to.<br />
 *
 * Example:
 *
 * A view displaying a single item wants to receive the id of
 * the item to display. In this case this view must implement
 * {@link com.github.brokenswing.comixaire.view.ParametrizedView}
 * and specify a controller implementing this interface.
 * When the view will be loaded through
 * {@link com.github.brokenswing.comixaire.view.ViewLoader#loadView(ParametrizedView, Object)}
 * the data that you passed to the loader will be passed to the controller that is
 * associated with the view.<br />
 *
 * @see ParametrizedView
 * @see com.github.brokenswing.comixaire.view.ViewLoader
 *
 * @param <T> the type of the data to receive
 */
public interface ParametrizedController<T>
{

    /**
     * Accepts the data that is passed when creating
     * the view associated with this controller.<br />
     * This method is called BEFORE {@link javafx.fxml.FXML}
     * annotated fields are populated.
     *
     * @param param the passed data.
     */
    void handleViewParam(T param);

}
