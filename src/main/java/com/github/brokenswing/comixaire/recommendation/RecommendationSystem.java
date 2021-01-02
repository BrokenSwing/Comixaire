package com.github.brokenswing.comixaire.recommendation;

import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Rating;

public class RecommendationSystem
{
    private Rating[] rates;

    public RecommendationSystem(Rating[] rates)
    {
        this.rates = rates;
    }

    public LibraryItem[] computeRecommendation(String type, Integer itemNumber, Client client)
    {
        return new LibraryItem[0];
    }
}
