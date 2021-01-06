package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.view.alert.InternalErrorAlert;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class Alerts
{
    public static void success(String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, content);
        alert.setTitle("Success");
        alert.setHeaderText("Action performed");
        alert.showAndWait();
    }

    public static void failure(String content){
        Alert alert = new Alert(Alert.AlertType.ERROR, content);
        alert.setTitle("Failure");
        alert.setHeaderText("We handle an error...");
        alert.showAndWait();
    }

    public static void warning(String content){
        Alert alert = new Alert(Alert.AlertType.WARNING, content);
        alert.setTitle("Warning");
        alert.setHeaderText("Please,");
        alert.showAndWait();
    }

    public static void exception(Exception e){
        InternalErrorAlert alert = new InternalErrorAlert(e);
        alert.showAndWait();
    }

    public static boolean confirm(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, content, ButtonType.YES);
        alert.setTitle(title);
        alert.setHeaderText(header);
        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent() && result.get() == ButtonType.YES);
    }
}
