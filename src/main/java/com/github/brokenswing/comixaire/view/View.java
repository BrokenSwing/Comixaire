package com.github.brokenswing.comixaire.view;

import javafx.scene.Parent;

import java.io.IOException;

public class View extends Parent
{

    public View(String viewName) throws IOException
    {
        Parent view = loadFromFXML(viewName);
        this.getChildren().add(view);
    }

    public Parent loadFromFXML(String viewName) throws IOException
    {
        return ViewLoader.getInstance().loadView(viewName);
    }

}
