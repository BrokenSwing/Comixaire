package com.github.brokenswing.comixaire.view;

import com.github.brokenswing.comixaire.controller.LoansController;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.view.util.ParametrizedView;

public class LoansView extends ParametrizedView<LoansController, Client>
{
    public LoansView() { super("loans.fxml", LoansController.class); }
}
