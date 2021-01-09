package com.github.brokenswing.comixaire.controller;

import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.exception.*;
import com.github.brokenswing.comixaire.facades.clients.ClientsFacade;
import com.github.brokenswing.comixaire.facades.fineTypes.FineTypesFacade;
import com.github.brokenswing.comixaire.facades.fines.FinesFacade;
import com.github.brokenswing.comixaire.facades.item.LibraryItemFacade;
import com.github.brokenswing.comixaire.facades.loans.LoansFacade;
import com.github.brokenswing.comixaire.facades.returns.ReturnFacade;
import com.github.brokenswing.comixaire.javafx.Alerts;
import com.github.brokenswing.comixaire.models.*;
import com.github.brokenswing.comixaire.utils.FormValidationBuilder;
import com.github.brokenswing.comixaire.utils.PrettyTimeTransformer;
import com.github.brokenswing.comixaire.view.Views;
import com.github.brokenswing.comixaire.view.util.Router;
import com.github.brokenswing.comixaire.view.util.ViewLoader;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class ReturnsController implements Initializable
{
    @ViewParam
    private Loan loan;

    @FXML
    private Text clientName;
    @FXML
    private Text clientSubscription;
    @FXML
    private Text clientLoans;
    @FXML
    private Text clientFines;
    @FXML
    private Text clientGender;

    @FXML
    private Text itemTitle;
    @FXML
    private Text itemCreatedOn;
    @FXML
    private Text itemReleasedOn;
    @FXML
    private Text itemLocation;
    @FXML
    private Text itemCategories;
    @FXML
    private Text itemBookings;
    @FXML
    private Text itemType;
    @FXML
    private Text itemCondition;
    @FXML
    private ChoiceBox<ConditionType> choiceCondition;
    @FXML
    private ChoiceBox<FineType> choiceFine;

    @InjectValue
    private ClientsFacade clientsFacade;
    @InjectValue
    private LibraryItemFacade libraryItemFacade;
    @InjectValue
    private FinesFacade finesFacade;
    @InjectValue
    private FineTypesFacade fineTypesFacade;
    @InjectValue
    private ReturnFacade returnFacade;
    @InjectValue
    private Router router;

    public void back()
    {
        router.navigateTo(Views.CLIENT_RETURNS);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.itemTitle.setText(loan.getLibraryItem().getTitle());
        this.itemCreatedOn.setText(PrettyTimeTransformer.prettyDate(loan.getLibraryItem().getCreatedOn()));
        this.itemReleasedOn.setText(PrettyTimeTransformer.prettyDate(loan.getLibraryItem().getReleasedOn()));
        this.itemLocation.setText(loan.getLibraryItem().getLocation());
        StringJoiner sj = new StringJoiner(", ");
        Arrays.stream(loan.getLibraryItem().getCategories()).forEach(sj::add);
        this.itemCategories.setText(sj.toString());
        this.itemBookings.setText(Integer.toString(loan.getLibraryItem().getBookings().length));
        this.itemType.setText(loan.getLibraryItem().getClass().getSimpleName());
        this.itemCondition.setText(loan.getLibraryItem().getCondition().getLabel());

        this.choiceCondition.setItems(FXCollections.observableArrayList(ConditionType.values()));
        this.choiceCondition.setValue(loan.getLibraryItem().getCondition());

        this.clientName.setText(loan.getClient().getFullname());
        this.clientGender.setText(loan.getClient().getGender());

        try
        {
            if (clientsFacade.validSubscription(loan.getClient())) { clientSubscription.setText("Valid"); }
            else { clientSubscription.setText("Not valid"); }

            clientLoans.setText(Integer.toString(clientsFacade.countLoans(loan.getClient())));
            clientFines.setText(Integer.toString(clientsFacade.countFines(loan.getClient())));

            FineType[] fts = fineTypesFacade.getAllFineTypes();
            this.choiceFine.setItems(FXCollections.observableArrayList(fts));
            this.choiceFine.setValue(Arrays.stream(fts).filter(fine -> fine.getLabel().equals("None")).collect(Collectors.toList()).get(0));
        }
        catch (InternalException e)
        {
            e.printStackTrace();
            Alerts.exception(e);
        }
    }

    public void returnItem()
    {
        if(!choiceCondition.getValue().equals(loan.getLibraryItem().getCondition())){
            LibraryItem item = loan.getLibraryItem();
            item.setCondition(choiceCondition.getValue());
            try
            {
                libraryItemFacade.update(item);
            }
            catch (InternalException e)
            {
                e.printStackTrace();
                Alerts.exception(e);
            }
        }

        Returns returns = null;
        try
        {
            returns = returnFacade.create(new Returns(loan.getIdLoan()), loan.getLibraryItem());
            if(choiceFine.getValue() != null && !choiceFine.getValue().getLabel().equals("None")){
                finesFacade.create(new Fine(returns.getIdReturn(),false, choiceFine.getValue()));
            }
            Alerts.success("The return has been registered.");
            router.navigateTo(Views.CLIENT_RETURNS);
        }
        catch (InternalException | NoReturnFoundException | InvalidFineTypeException e)
        {
            e.printStackTrace();
            Alerts.exception(e);
        }
    }
}
