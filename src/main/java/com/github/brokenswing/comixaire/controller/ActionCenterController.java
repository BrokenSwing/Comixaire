package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.auth.AuthFacade;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.view.LoginView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ActionCenterController implements Initializable {

    @FXML
    private ImageView logoutButton;

    @InjectValue
    private AuthFacade auth;

    public void logout() throws IOException {
        auth.logout();
        displayLoginView();
    }


    protected void displayLoginView() throws IOException
    {
        Scene scene = logoutButton.getScene();
        scene.setRoot(new LoginView());
        scene.getWindow().sizeToScene();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.logoutButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event -> {
            try {
                this.logout();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }
}
