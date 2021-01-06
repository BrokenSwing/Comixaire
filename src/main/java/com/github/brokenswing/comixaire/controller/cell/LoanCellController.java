package com.github.brokenswing.comixaire.controller.cell;

import com.github.brokenswing.comixaire.di.ViewParam;
import com.github.brokenswing.comixaire.models.Loan;
import com.github.brokenswing.comixaire.utils.PrettyTimeTransformer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class LoanCellController implements Initializable
{

    @ViewParam
    private Loan loan;

    @FXML
    private Text itemTitle;
    @FXML
    private Text itemState;
    @FXML
    private Text loanFrom;
    @FXML
    private Text loanTo;

    @FXML
    protected void returnItem()
    {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.itemTitle.setText(loan.getLibraryItem().getTitle());
        this.itemState.setText("State : " + loan.getLibraryItem().getCondition());
        this.loanFrom.setText("From : " + PrettyTimeTransformer.prettyDate(loan.getFrom()));
        this.loanTo.setText("To : " + PrettyTimeTransformer.prettyDate(loan.getTo()));
    }

}
