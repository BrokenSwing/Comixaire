package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.ClientDAO;
import com.github.brokenswing.comixaire.exception.CardIdAlreadyExist;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.LibraryItem;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;

public class PostgresClientDAO implements ClientDAO
{

    private final Connection connection;

    PostgresClientDAO(Connection connection)
    {
        this.connection = connection;
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
            return new Client(result.getInt("client_id"), client.getFirstname(), client.getCardId(), client.getLastname(), client.getGender(), client.getAddress(), client.getBirthdate(), client.getSubscriptionId());
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
    public Client[] findByName(String firstname, String lastname) throws InternalException, NoClientFoundException
    {
        try
        {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM clients WHERE client_firstname = (?) AND client_lastname = (?)");
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            ResultSet result = stmt.executeQuery();

            ArrayList<Client> clients = new ArrayList<>();

            while(result.next()){
                clients.add(clientFromRow(result));
            }
            if(clients.isEmpty()){
                throw new NoClientFoundException("No client found with the name: " + firstname + " " + lastname);
            }
            return clients.toArray(new Client[0]);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to find client named: " + firstname + " " + lastname, e);
        }
    }

    @Override
    public Client[] findByFirstname(String firstname) throws InternalException, NoClientFoundException
    {
        try
        {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM clients WHERE client_firstname = (?)");
            stmt.setString(1, firstname);
            ResultSet result = stmt.executeQuery();

            ArrayList<Client> clients = new ArrayList<>();

            while(result.next()){
                clients.add(clientFromRow(result));
            }
            if(clients.isEmpty()){
                throw new NoClientFoundException("No client found with the firstname: " + firstname);
            }
            return clients.toArray(new Client[0]);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to find client named: " + firstname, e);
        }
    }

    @Override
    public Client[] findByLastname(String lastname) throws InternalException, NoClientFoundException
    {
        try
        {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM clients WHERE client_lastname = (?)");
            stmt.setString(1, lastname);
            ResultSet result = stmt.executeQuery();

            ArrayList<Client> clients = new ArrayList<>();

            while(result.next()){
                clients.add(clientFromRow(result));
            }
            if(clients.isEmpty()){
                throw new NoClientFoundException("No client found with the lastname: " + lastname);
            }
            return clients.toArray(new Client[0]);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to find client named: " + lastname, e);
        }
    }

    @Override
    public Client[] findAll() throws InternalException, NoClientFoundException
    {
        try
        {
            ResultSet result = connection.prepareStatement("SELECT * FROM clients").executeQuery();

            ArrayList<Client> clients = new ArrayList<>();

            while(result.next()){
                Client client = clientFromRow(result);
                clients.add(client);
                System.out.println(client);
            }
            if(clients.isEmpty()){
                throw new NoClientFoundException("No clients found");
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
                                    + "(client_firstname, client_lastname, client_gender, client_birthdate, client_address, client_cardID, subscription_id) = (?, ?, ?, ?, ?, ?, ?) "
                                    + "WHERE client_id = (?)"
                    );
            prepare.setString(1, client.getFirstname());
            prepare.setString(2, client.getLastname());
            prepare.setString(3, client.getGender());
            prepare.setDate(4, new java.sql.Date(client.getBirthdate().getTime()));
            prepare.setString(5, client.getAddress());
            prepare.setString(6, client.getCardId());
            prepare.setInt(7, client.getSubscriptionId());
            prepare.setInt(8, client.getIdClient());

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
            PreparedStatement prepare = this.connection.prepareStatement("DELETE FROM clients WHERE client_id = (?)");
            prepare.setInt(1, client.getIdClient());
            prepare.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to delete client named " + client.getFirstname() + " " + client.getLastname(), e);
        }
    }

    private Client clientFromRow(ResultSet result) throws SQLException
    {
        return new Client(
                result.getInt("client_id"),
                result.getString("client_firstname"),
                result.getString("client_lastname"),
                result.getString("client_cardID"),
                result.getString("client_gender"),
                result.getString("client_address"),
                result.getDate("client_birthdate"),
                result.getInt("subscription_id")
        );
    }

}
