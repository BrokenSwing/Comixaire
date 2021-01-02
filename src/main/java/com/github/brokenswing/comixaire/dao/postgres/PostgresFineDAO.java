package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.FineDAO;
import com.github.brokenswing.comixaire.exception.*;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.Fine;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;

public class PostgresFineDAO implements FineDAO
{
    private final Connection connection;

    public PostgresFineDAO(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public void create(Fine fine) throws InternalException, NoReturnFoundException, NonValidFineTypeException
    {
        try
        {
            PreparedStatement prepare = connection.prepareStatement("INSERT INTO fine(return_id, fineType_id, paid) VALUES (?, ?, ?)");
            prepare.setInt(1, fine.getId());
            prepare.setInt(2, fine.getIdType());
            prepare.setBoolean(3, fine.isPaid());
            prepare.executeQuery();
        }
        catch (SQLException e)
        {
            if (e instanceof PSQLException)
            {
                PSQLException ex = (PSQLException) e;
                if (ex.getServerErrorMessage() != null && "fk_return".equals(ex.getServerErrorMessage().getConstraint()))
                {
                    throw new NoReturnFoundException(fine.getId());
                }
                else if (ex.getServerErrorMessage() != null && "fk_fineType".equals(ex.getServerErrorMessage().getConstraint()))
                {
                    throw new NonValidFineTypeException(fine.getIdType());
                }
            }
            throw new InternalException("Unable to create fine", e);
        }
    }

    @Override
    public Fine[] findByClient(Client client) throws InternalException, NoFineFoundException, NoClientFoundException
    {
        try
        {
            System.out.println(client.getFirstname() + " " + client.getIdClient());
            PreparedStatement prepare = connection.prepareStatement("SELECT * FROM fine JOIN fineType ON fineType.fineType_id = fine.fineType_id " +
                    "JOIN loans ON loans.loan_id = fine.return_id WHERE loans.client_id = ?");
            prepare.setInt(1, client.getIdClient());
            ResultSet result = prepare.executeQuery();
            ArrayList<Fine> fines = new ArrayList<>();

            while (result.next())
            {
                fines.add(fineFromRow(result));
            }
            if (fines.isEmpty())
            {
                throw new NoFineFoundException(client);
            }
            return fines.toArray(new Fine[0]);
        }
        catch (SQLException e)
        {
            if (e instanceof PSQLException)
            {
                PSQLException ex = (PSQLException) e;
                if (ex.getServerErrorMessage() != null && "fk_clientLoan".equals(ex.getServerErrorMessage().getConstraint()))
                {
                    throw new NoClientFoundException(client.getCardId());
                }
            }
            throw new InternalException("Unable to find any fines", e);
        }
    }

    @Override
    public void pay(Fine fine) throws InternalException
    {
        try
        {
            PreparedStatement prepare = this.connection.prepareStatement("UPDATE fine SET paid = ? WHERE return_id = ? AND fineType_id = ?");
            prepare.setBoolean(1, true);
            prepare.setInt(2, fine.getId());
            prepare.setInt(3, fine.getIdType());
            prepare.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to pay fine: " + fine.getLabel() + " with price of: " + fine.getPrice(), e);
        }
    }

    @Override
    public void delete(Fine fine) throws InternalException
    {
        try
        {
            PreparedStatement prepare = this.connection.prepareStatement("DELETE FROM fine WHERE return_id = ? AND fineType_id = ?");
            prepare.setInt(1, fine.getId());
            prepare.setInt(2, fine.getIdType());
            prepare.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to delete fine " + fine.getLabel() + " with price of: " + fine.getPrice(), e);
        }
    }

    private Fine fineFromRow(ResultSet result) throws SQLException
    {
        int fineId = result.getInt("return_id");
        int fineTypeId = result.getInt("fineType_id");
        Boolean paid = result.getBoolean("paid");
        String label = result.getString("fineType_label");
        int price = result.getInt("fineType_price");
        return new Fine(fineId, paid, fineTypeId, label, price);
    }
}
