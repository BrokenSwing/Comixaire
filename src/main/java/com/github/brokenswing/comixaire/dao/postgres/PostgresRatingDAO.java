package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.RatingDAO;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Rating;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PostgresRatingDAO implements RatingDAO
{
    private final Connection connection;

    public PostgresRatingDAO(Connection connection)
    {
        this.connection = connection;
    }


    @Override
    public void create(Rating rating) throws InternalException
    {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO rating (client_id, item_id, note) VALUES (?, ?, ?) ON CONFLICT (client_id, item_id) DO UPDATE SET note = ?"))
        {
            stmt.setInt(1, rating.getClient().getIdClient());
            stmt.setInt(2, rating.getLibraryItem().getIdLibraryItem());
            stmt.setInt(3, rating.getNote());
            stmt.setInt(4, rating.getNote());
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to create rate " + rating, e);
        }
    }

    @Override
    public Rating[] getAllRating() throws InternalException
    {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM rating " +
                "NATURAL JOIN clients " +
                "NATURAL JOIN libraryitems " +
                "NATURAL LEFT JOIN books " +
                "NATURAL LEFT JOIN cd " +
                "NATURAL LEFT JOIN dvd " +
                "NATURAL LEFT JOIN games "))
        {
            ResultSet result = stmt.executeQuery();
            ArrayList<Rating> ratings = new ArrayList<>();

            while (result.next())
            {
                LibraryItem item = PostgresLibraryItemDAO.libraryItemFromRow(result);
                Client client = PostgresClientDAO.clientFromRow(result);
                ratings.add(new Rating(client, item, result.getInt("note")));
            }

            return ratings.toArray(new Rating[0]);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to find any rating", e);
        }
    }

    @Override
    public Integer[] getAllItemId() throws InternalException
    {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT item_id FROM libraryitems"))
        {
            ResultSet result = stmt.executeQuery();
            ArrayList<Integer> itemsId = new ArrayList<>();

            while (result.next())
            {
                itemsId.add(result.getInt("item_id"));
            }
            return itemsId.toArray(new Integer[0]);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to find any item", e);
        }
    }

    @Override
    public Rating[] getRatingByClientId(int clientId) throws InternalException, NoClientFoundException
    {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM clients NATURAL JOIN rating NATURAL JOIN libraryitems NATURAL LEFT JOIN books NATURAL LEFT JOIN cd NATURAL LEFT JOIN dvd NATURAL LEFT JOIN games WHERE client_id = ?"))
        {
            stmt.setInt(1, clientId);
            ResultSet result = stmt.executeQuery();

            ArrayList<Rating> ratings = new ArrayList<>();

            boolean clientFound = false;

            while (result.next())
            {
                clientFound = true;
                result.getInt("note");
                if (result.wasNull())
                {
                    continue;
                }

                LibraryItem item = PostgresLibraryItemDAO.libraryItemFromRow(result);
                Client client = PostgresClientDAO.clientFromRow(result);
                int note = result.getInt("note");
                ratings.add(new Rating(client, item, note));
            }

            if (!clientFound)
            {
                throw new NoClientFoundException(clientId);
            }

            return ratings.toArray(new Rating[0]);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to find any rating", e);
        }
    }

}
