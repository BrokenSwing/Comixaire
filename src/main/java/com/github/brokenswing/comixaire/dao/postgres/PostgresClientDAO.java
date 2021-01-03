package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.ClientDAO;
import com.github.brokenswing.comixaire.exception.CardIdAlreadyExist;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.models.Client;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class PostgresClientDAO implements ClientDAO
{

    private final Connection connection;

    PostgresClientDAO(Connection connection)
    {
        this.connection = connection;
    }

    public static Client clientFromRow(ResultSet result) throws SQLException
    {
        return new Client(
                result.getInt("client_id"),
                result.getString("client_firstname"),
                result.getString("client_lastname"),
                result.getString("client_cardID"),
                result.getString("client_gender"),
                result.getString("client_address"),
                new Date(result.getDate("client_birthdate").getTime())
        );
    }

    @Override
    public Client create(Client client) throws InternalException, CardIdAlreadyExist
    {
        try
        {
            PreparedStatement prepare = this
                    .connection
                    .prepareStatement(
                            "INSERT INTO clients(client_firstname, client_lastname, client_gender, client_birthdate, client_address, client_cardID)"
                                    + " VALUES (?, ?, ?, ?, ?, ?) RETURNING client_id"
                    );
            prepare.setString(1, client.getFirstname());
            prepare.setString(2, client.getLastname());
            prepare.setString(3, client.getGender());
            prepare.setDate(4, new java.sql.Date(client.getBirthdate().getTime()));
            prepare.setString(5, client.getAddress());
            prepare.setString(6, client.getCardId());

            ResultSet result = prepare.executeQuery();
            result.next();
            return new Client(result.getInt("client_id"), client.getFirstname(), client.getCardId(), client.getLastname(), client.getGender(), client.getAddress(), client.getBirthdate());
        }
        catch (SQLException e)
        {
            if (e instanceof PSQLException)
            {
                PSQLException ex = (PSQLException) e;
                if (ex.getServerErrorMessage() != null && "unique_cardID".equals(ex.getServerErrorMessage().getConstraint()))
                {
                    throw new CardIdAlreadyExist(client.getCardId());
                }
            }
            throw new InternalException("Unable to create client", e);
        }
    }

    @Override
    public Client findById(int idClient) throws InternalException, NoClientFoundException
    {
        try
        {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM clients WHERE client_id = (?)");
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
    public Client[] findAll() throws InternalException
    {
        try
        {
            ResultSet result = connection.prepareStatement("SELECT * FROM clients").executeQuery();

            ArrayList<Client> clients = new ArrayList<>();

            while (result.next())
            {
                Client client = clientFromRow(result);
                clients.add(client);
            }
            return clients.toArray(new Client[0]);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to find any client", e);
        }
    }

    @Override
    public void update(Client client) throws InternalException, CardIdAlreadyExist
    {
        try
        {
            PreparedStatement prepare = this
                    .connection
                    .prepareStatement(
                            "UPDATE clients SET "
                                    + "(client_firstname, client_lastname, client_gender, client_birthdate, client_address, client_cardID) = (?, ?, ?, ?, ?, ?) "
                                    + "WHERE client_id = (?)"
                    );
            prepare.setString(1, client.getFirstname());
            prepare.setString(2, client.getLastname());
            prepare.setString(3, client.getGender());
            prepare.setDate(4, new java.sql.Date(client.getBirthdate().getTime()));
            prepare.setString(5, client.getAddress());
            prepare.setString(6, client.getCardId());
            prepare.setInt(7, client.getIdClient());

            prepare.executeUpdate();
        }
        catch (SQLException e)
        {
            if (e instanceof PSQLException)
            {
                PSQLException ex = (PSQLException) e;
                if (ex.getServerErrorMessage() != null && "unique_cardID".equals(ex.getServerErrorMessage().getConstraint()))
                {
                    throw new CardIdAlreadyExist(client.getCardId());
                }
            }
            throw new InternalException("Unable to update client named: " + client.getFirstname() + " " + client.getLastname(), e);
        }
    }

    @Override
    public void delete(Client client) throws InternalException
    {
        try
        {
            PreparedStatement prepare = this.connection.prepareStatement("DELETE FROM clients WHERE client_id = ?");
            prepare.setInt(1, client.getIdClient());
            prepare.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to delete client named " + client.getFirstname() + " " + client.getLastname(), e);
        }
    }

    @Override
    public int countLoans(Client client) throws InternalException
    {
        try
        {
            PreparedStatement prepare = this.connection.prepareStatement("SELECT COUNT(*) FROM loans WHERE client_id = ?");
            prepare.setInt(1, client.getIdClient());
            ResultSet result = prepare.executeQuery();
            result.next();
            return result.getInt(1);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to count loans for client: " + client.getFullname(), e);
        }
    }

    @Override
    public int countCurrentLoans(Client client) throws InternalException
    {
        try
        {
            PreparedStatement prepare = this.connection.prepareStatement("SELECT COUNT(*) FROM loans WHERE loan_to >= NOW() AND client_id = ?");
            prepare.setInt(1, client.getIdClient());
            ResultSet result = prepare.executeQuery();
            result.next();
            return result.getInt(1);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to count loans for client: " + client.getFullname(), e);
        }
    }

    @Override
    public Boolean validSubscription(Client client) throws InternalException
    {
        try
        {
            PreparedStatement prepare = this.connection.prepareStatement("SELECT COUNT(*) FROM subscriptions " +
                    "WHERE subscription_to >= NOW() " +
                    "AND subscription_from <= NOW() " +
                    "AND client_id = ?");
            prepare.setInt(1, client.getIdClient());
            ResultSet result = prepare.executeQuery();
            result.next();
            return (result.getInt(1) > 0);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to check for valid subscriptions for client: " + client.getFullname(), e);
        }
    }

    @Override
    public int countFines(Client client) throws InternalException
    {
        try
        {
            PreparedStatement prepare = this.connection.prepareStatement("SELECT COUNT(*) FROM fine JOIN loans ON fine.return_id = loans.loan_id WHERE loans.client_id = ?");
            prepare.setInt(1, client.getIdClient());
            ResultSet result = prepare.executeQuery();
            result.next();
            return result.getInt(1);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to count fines for client: " + client.getFullname(), e);
        }
    }

    @Override
    public int countVotes(Client client) throws InternalException
    {
        try
        {
            PreparedStatement prepare = this.connection.prepareStatement("SELECT COUNT(*) FROM rating WHERE client_id = ?");
            prepare.setInt(1, client.getIdClient());
            ResultSet result = prepare.executeQuery();
            result.next();
            return result.getInt(1);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to count votes for client: " + client.getFullname(), e);
        }
    }

}
