package com.github.brokenswing.comixaire.view;

import com.github.brokenswing.comixaire.Comixaire;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class LoginView extends Parent
{

    public LoginView() throws IOException
    {
        this.getChildren().add(this.load());
    }

    private Parent load() throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Comixaire.class.getClassLoader().getResource("views/login.fxml"));
        return loader.load();
    }

}
