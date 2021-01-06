package com.github.brokenswing.comixaire.controller.cell;

import com.github.brokenswing.comixaire.controller.ClientBorrowedItemsController;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.rating.RatingFacade;
import com.github.brokenswing.comixaire.models.Rating;
import com.github.brokenswing.comixaire.view.alert.InternalErrorAlert;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class RatingCellController implements Initializable
{

    @FXML
    private Text title;
    @FXML
    private org.controlsfx.control.Rating rate;
    @FXML
    private Button rateButton;
    @FXML
    private Text type;

    @ViewParam
    private ClientBorrowedItemsController.ObservableRating rating;

    @InjectValue
    private ViewLoader loader;
    @InjectValue
    private RatingFacade ratingFacade;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.title.setText(rating.getLibraryItem().getTitle());
        this.type.setText(rating.getLibraryItem().getClass().getSimpleName());

        this.rate.visibleProperty().bind(rating.existsProperty());
        this.rateButton.visibleProperty().bind(rating.existsProperty().not());
        this.rate.ratingProperty().bindBidirectional(rating.noteProperty());

        this.rating.noteProperty().addListener((obs, oldValue, newValue) ->
        {
            Rating realRating = new Rating(rating.getClient(), rating.getLibraryItem(), rating.noteProperty().get());
            try
            {
                ratingFacade.rate(realRating);
            }
            catch (InternalException e)
            {
                e.printStackTrace();
                new InternalErrorAlert(e).showAndWait();
            }
        });
    }

    public void displayRateDialog()
    {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.getDialogPane().getStylesheets().add("/stylesheets/authentication.css");

        AnchorPane pane = loader.loadView("rating.fxml", rating.getLibraryItem());

        dialog.setGraphic(pane);

        ButtonType validButton = new ButtonType("Rate", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(validButton, cancelButton);

        dialog.getDialogPane().lookupButton(validButton).getStyleClass().add("btn");
        dialog.getDialogPane().lookupButton(cancelButton).getStyleClass().add("btn");

        dialog.showAndWait()
                .filter(b -> b == validButton)
                .ifPresent(b ->
                {
                    double note = pane.getChildren().stream().filter(c -> c instanceof org.controlsfx.control.Rating)
                            .findFirst().map(n -> ((org.controlsfx.control.Rating) n).getRating()).orElse(0D);

                    Rating realRating = new Rating(rating.getClient(), rating.getLibraryItem(), (int) note);
                    try
                    {
                        ratingFacade.rate(realRating);
                        rating.noteProperty().set((int) note);
                    }
                    catch (InternalException e)
                    {
                        e.printStackTrace();
                        new InternalErrorAlert(e).showAndWait();
                    }
                });
    }

}
