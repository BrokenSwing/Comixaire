package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.ClientDAO;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.models.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresClientDAO implements ClientDAO
{

    private final Connection connection;

    PostgresClientDAO(Connection connection)
    {
        this.connection = connection;
    }


    @Override
    public Client create(Client client) throws InternalException
    {
        //TODO: implement
        return null;
    }

    @Override
    public Client findById(int idClient) throws InternalException, NoClientFoundException
    {
        try
        {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM clients WHERE client_id = ?");
            stmt.setInt(1, idClient);
            ResultSet result = stmt.executeQuery();
            if (result.next())
            {
                return clientFromRow(result);
            }
            else
            {
                throw new NoClientFoundException(idClient);
            }
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to find client by id. Client id: " + idClient, e);
        }
    }

    @Override
    public Client findByCardID(String cardID) throws InternalException, NoClientFoundException
    {
        try
        {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM clients WHERE client_cardID = ?");
            stmt.setString(1, cardID);
            ResultSet result = stmt.executeQuery();
            if (result.next())
            {
                return clientFromRow(result);
            }
            else
            {
                throw new NoClientFoundException(cardID);
            }
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to find client by id. Client card id: " + cardID, e);
        }
    }

    @Override
    public Client[] findByName(String firstname, String lastname) throws InternalException, NoClientFoundException
    {
        //TODO: implement
        return new Client[0];
    }

    @Override
    public Client[] findByFirstname(String firstname) throws InternalException, NoClientFoundException
    {
        //TODO: implement
        return new Client[0];
    }

    @Override
    public Client[] findByLastname(String lastname) throws InternalException, NoClientFoundException
    {
        //TODO: implement
        return new Client[0];
    }

    @Override
    public void update(Client client) throws InternalException
    {
        //TODO: implement
    }

    @Override
    public void delete(Client client) throws InternalException
    {
        //TODO: implement
    }

    private Client clientFromRow(ResultSet result) throws SQLException
    {
        return new Client(
                result.getInt("client_id"),
                result.getString("client_firtname"),
                result.getString("client_lastname"),
                result.getString("client_cardID"),
                result.getString("client_gender"),
                result.getString("client_address"),
                result.getDate("client_birthdate")
        );
    }

}
