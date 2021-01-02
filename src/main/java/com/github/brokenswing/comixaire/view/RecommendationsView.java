package com.github.brokenswing.comixaire.view;

import com.github.brokenswing.comixaire.controller.ClientRecommendationsController;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.view.util.ParametrizedView;

public class RecommendationsView extends ParametrizedView<ClientRecommendationsController, Client>
{
    public RecommendationsView()
    {
        super("user-recommendations.fxml", ClientRecommendationsController.class);
    }
}
