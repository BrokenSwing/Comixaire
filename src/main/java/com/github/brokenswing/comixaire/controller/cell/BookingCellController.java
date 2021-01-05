package com.github.brokenswing.comixaire.controller.cell;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.booking.BookingFacade;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.alert.InternalErrorAlert;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;

public class BookingCellController implements Initializable
{

    @ViewParam
    private Pair<LibraryItem, Client> params;

    private LibraryItem libraryItem;
    private Client client;

    @FXML
    private Text itemTitle;
    @FXML
    private Text itemLocation;
    @FXML
    private Text itemType;

    @InjectValue
    private BookingFacade bookingFacade;
    @InjectValue
    private Router router;

    @FXML
    protected void book()
    {
        try
        {
            this.bookingFacade.addBooking(libraryItem, client);
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setContentText("The library item \"" + libraryItem.getTitle() + "\" was successfully booked.");
            successAlert.showAndWait();
        }
        catch (InternalException e)
        {
            e.printStackTrace();
            new InternalErrorAlert(e).showAndWait();
        }
        router.navigateTo(Views.BOOKINGS, client);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.libraryItem = params.getKey();
        this.client = params.getValue();
        this.itemTitle.setText(this.libraryItem.getTitle());
        this.itemLocation.setText("Location : " + this.libraryItem.getLocation());
        this.itemType.setText("Type : " + libraryItem.getClass().getSimpleName());
    }
}

