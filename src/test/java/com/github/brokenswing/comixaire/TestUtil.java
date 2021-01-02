package com.github.brokenswing.comixaire;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.testfx.api.FxRobot;

import java.io.IOException;
import java.util.Objects;

public class TestUtil
{

    private TestUtil()
    {
    }

    public static Runnable rethrow(ThrowableRunnable r)
    {
        return () ->
        {
            try
            {
                r.run();
            }
            catch (Throwable e)
            {
                throw new RuntimeException(e);
            }
        };
    }

    public static <V extends Node, T> Pair<V, T> controllerFromView(String viewPath)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(TestUtil.class.getClassLoader().getResource("views/" + viewPath)));
            V n = loader.load();
            return new Pair<>(n, loader.getController());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void closeWindow(FxRobot robot, String selector)
    {
        robot.interact(()->((Stage)((robot.lookup(selector).query())).getScene().getWindow()).close());
    }

    public interface ThrowableRunnable
    {
        void run() throws Throwable;
    }

}
