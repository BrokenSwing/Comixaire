package com.github.brokenswing.comixaire.facades.recommendations;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.Facade;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Rating;
import com.github.brokenswing.comixaire.recommendation.RecommenderSystem;

public class RecommendationFacade extends Facade
{
    public RecommendationFacade(DAOFactory factory)
    {
        super(factory);
    }

    private Rating[] getAllRating() throws InternalException
    {
        return this.factory.getRatingDAO().getAllRating();
    }

    private Integer[] getAllItemId() throws InternalException
    {
        return this.factory.getRatingDAO().getAllItemId();
    }

    public LibraryItem[] computeRecommendation(Client client) throws InternalException
    {
        return new RecommenderSystem(this.getAllRating()).getRecommendedItems(client);
    }

}
