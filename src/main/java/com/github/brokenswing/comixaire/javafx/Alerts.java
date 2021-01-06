package com.github.brokenswing.comixaire.javafx;

import com.github.brokenswing.comixaire.view.alert.InternalErrorAlert;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alerts
{

    /**
     * Displays an alert popup to the user indicating a success.
     *
     * @param content the string detail of the popup
     */
    public static void success(String content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, content);
        alert.setTitle("Success");
        alert.setHeaderText("Action performed");
        alert.showAndWait();
    }

    /**
     * Displays an alert popup to the user indicating a failure.
     *
     * @param content the string detail of the popup
     */
    public static void failure(String content)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, content);
        alert.setTitle("Failure");
        alert.setHeaderText("We handled an error...");
        alert.showAndWait();
    }

    /**
     * Displays an alert popup to the user indicating an internal exception occurred.
     *
     * @param e the exception handled
     */
    public static void exception(Exception e)
    {
        InternalErrorAlert alert = new InternalErrorAlert(e);
        alert.showAndWait();
    }

    /**
     * Displays an alert popup to the user to get a confirmation.
     *
     * @param title   the popup title
     * @param header  the popup header
     * @param content the string detail of the popup
     * @return boolean representing confirmation's state
     */
    public static boolean confirm(String title, String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, content, ButtonType.YES);
        alert.setTitle(title);
        alert.setHeaderText(header);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.YES;
    }
}
