package com.github.brokenswing.comixaire.view.util;

/**
 * Represents a FXML file view.
 */
public class View
{

    private final String viewName;

    /**
     * @param viewName the name of the FXML file for this view
     */
    public View(String viewName)
    {
        this.viewName = viewName;
    }

    public String getViewName()
    {
        return this.viewName;
    }

}
