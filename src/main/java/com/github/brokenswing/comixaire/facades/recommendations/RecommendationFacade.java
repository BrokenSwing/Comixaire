package com.github.brokenswing.comixaire.facades.recommendations;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.Facade;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Rating;
import com.github.brokenswing.comixaire.recommendation.RecommendationSystem;

public class RecommendationFacade extends Facade
{
    public RecommendationFacade(DAOFactory factory)
    {
        super(factory);
    }

    private Rating[] recommendation() throws InternalException
    {
        return this.factory.getRatingDAO().getAllRating();
    }

    public LibraryItem[] computeRecommendation(String type, Integer itemNumber, Client client) throws InternalException
    {
        RecommendationSystem recommender = new RecommendationSystem(this.recommendation());
        return recommender.computeRecommendation(type, itemNumber, client);
    }

}
