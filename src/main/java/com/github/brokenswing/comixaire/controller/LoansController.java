package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoLibraryItemFoundException;
import com.github.brokenswing.comixaire.facades.booking.BookingFacade;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.facades.item.LibraryItemFacade;
import com.github.brokenswing.comixaire.facades.loans.LoansFacade;
import com.github.brokenswing.comixaire.javafx.IntField;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Loan;
import com.github.brokenswing.comixaire.utils.FormValidationBuilder;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.alert.InternalErrorAlert;
import com.github.brokenswing.comixaire.view.util.Router;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class LoansController implements Initializable
{

    @ViewParam
    private Client client;

    @FXML
    private ListView<String> loansList;
    @FXML
    private Text fullname;
    @FXML
    private Text subscription;
    @FXML
    private Text loans;
    @FXML
    private Text fines;
    @FXML
    private Text gender;
    @FXML
    private IntField libraryItemId;
    @FXML
    private Button loanButton;

    @InjectValue
    private ClientsFacade clientsFacade;
    @InjectValue
    private LibraryItemFacade itemFacade;
    @InjectValue
    private LoansFacade loansFacade;
    @InjectValue
    private BookingFacade bookingFacade;
    @InjectValue
    private Router router;

    public void back()
    {
        router.navigateTo(Views.CLIENT_LOANS);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        fullname.setText(client.getFullname());
        gender.setText(client.getGender());
        try
        {
            if (clientsFacade.validSubscription(client))
            {
                subscription.setText("Valid");
                BooleanBinding isValid = new FormValidationBuilder()
                        .notEmpty(libraryItemId.textProperty())
                        .build();
                loanButton.disableProperty().bind(isValid.not());
            }
            else
            {
                subscription.setText("Not valid");
                loanButton.setDisable(true);
            }
            loans.setText(Integer.toString(clientsFacade.countLoans(client)));
            fines.setText(Integer.toString(clientsFacade.countFines(client)));
        }
        catch (InternalException e)
        {
            e.printStackTrace();
        }

    }

    public void loan()
    {
        try
        {
            LibraryItem item = itemFacade.findById(libraryItemId.getValue());
            if (item.getBookings().length == 0 || item.peekBooking() == client.getIdClient())
            {
                Date from = new Date();
                Date to = Date.from(LocalDate.now().plusWeeks(3).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                loansFacade.create(new Loan(from, to, client, item));
                bookingFacade.deleteBooking(item, client);

                loansList.setItems(FXCollections.observableArrayList(item.getTitle()));//update view, todo: change
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Library item not available");
                alert.setHeaderText("This library item is booked by another client !");
                alert.setContentText("There is actually " + item.getBookings().length + " bookers for this item.");
                alert.showAndWait();
            }
        }
        catch (InternalException e)
        {
            e.printStackTrace();
            new InternalErrorAlert(e).showAndWait();
        }
        catch (NoLibraryItemFoundException e)
        {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Library item not found !");
            alert.setContentText("No library item with the ID " + libraryItemId.getValue() + " can be found.");
        }
    }
}
