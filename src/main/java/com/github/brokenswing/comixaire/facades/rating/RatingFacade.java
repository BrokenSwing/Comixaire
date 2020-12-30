package com.github.brokenswing.comixaire.facades.rating;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.Facade;
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

    public LibraryItem[] search (String name, Boolean unratedItems, Boolean ratedItems) throws InternalException
    {
       return this.factory.getRatingDAO().search(name, unratedItems, ratedItems);
    }

}
