package com.github.brokenswing.comixaire.facades.rating;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.facades.Facade;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Rating;

public class RatingFacade extends Facade
{
    public RatingFacade(DAOFactory factory)
    {
        super(factory);
    }

    public void rate (Rating rating) throws InternalException
    {
        this.factory.getRatingDAO().create(rating);
    }

    public Rating[] getAllForClient(Client client) throws InternalException, NoClientFoundException
    {
       return factory.getRatingDAO().getRatingByClientId(client.getIdClient());
    }

}
