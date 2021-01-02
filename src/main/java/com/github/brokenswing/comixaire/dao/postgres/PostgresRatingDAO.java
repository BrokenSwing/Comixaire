package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.RatingDAO;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Rating;

import java.sql.*;
import java.util.ArrayList;

public class PostgresRatingDAO implements RatingDAO
{
    private final Connection connection;

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
        try
        {
            ResultSet result = connection.prepareStatement(
                    "SELECT * FROM rating " +
                    "NATURAL JOIN clients " +
                    "NATURAL JOIN libraryitems " +
                    "NATURAL LEFT JOIN books " +
                    "NATURAL LEFT JOIN cd " +
                    "NATURAL LEFT JOIN dvd " +
                    "NATURAL LEFT JOIN games").executeQuery();

            ArrayList<Rating> ratings = new ArrayList<>();

            while(result.next()){
                LibraryItem item = PostgresLibraryItemDAO.libraryItemFromRow(result);
                Client client = PostgresClientDAO.clientFromRow(result);
                if (item != null) {
                    ratings.add(new Rating(client, item, result.getInt("note")));
                }
            }

            return ratings.toArray(new Rating[0]);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to find any rating", e);
        }
    }

}
