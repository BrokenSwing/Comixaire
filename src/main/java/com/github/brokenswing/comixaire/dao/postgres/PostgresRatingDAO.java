package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.RatingDAO;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Rating;

import java.sql.*;

public class PostgresRatingDAO implements RatingDAO
{
    private Connection connection;

    public PostgresRatingDAO(Connection connection) { this.connection = connection; }


    @Override
    public LibraryItem[] search(String name, Boolean unratedItems, Boolean ratedItems) throws InternalException
    {
        //TODO: implement
        return new LibraryItem[0];
    }

    @Override
    public void create(Rating rating) throws InternalException
    {
        //TODO: implement
    }

    @Override
    public Rating[] getAllRating() throws InternalException
    {
        //TODO: implement
        return new Rating[0];
    }

}
