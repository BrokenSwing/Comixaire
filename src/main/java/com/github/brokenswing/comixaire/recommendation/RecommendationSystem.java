package com.github.brokenswing.comixaire.recommendation;

import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Rating;

import java.util.Arrays;

public class RecommendationSystem
{
    private Rating[] rates;
    private Integer[] itemsId;

    public RecommendationSystem(Rating[] rates, Integer[] itemsId)
    {
        this.rates = rates;
        this.itemsId = itemsId;
    }

    public LibraryItem[] computeRecommendation(String type, Integer itemNumber, Client client)
    {
        return new LibraryItem[0];
    }
}
