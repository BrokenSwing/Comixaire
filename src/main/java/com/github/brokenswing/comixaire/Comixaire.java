package com.github.brokenswing.comixaire;

import com.github.brokenswing.comixaire.view.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Comixaire application. This is the class for the entry point of the whole JavaFX application.
 */
public class Comixaire extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        primaryStage.setScene(new Scene(new LoginView()));
        primaryStage.setResizable(false); // TODO: Create responsive views !!
        primaryStage.show();
    }

}
