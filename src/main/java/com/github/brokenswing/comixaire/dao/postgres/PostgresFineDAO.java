package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.FineDAO;
import com.github.brokenswing.comixaire.exception.CardIdAlreadyExist;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.exception.NoFineFoundException;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.Fine;
import com.github.brokenswing.comixaire.models.FineType;
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
    public Fine create(Fine fine) throws InternalException
    {
        return null;
    }

    @Override
    public Fine findById(int idFine) throws InternalException, NoFineFoundException
    {
        //TODO: implement
        return null;
    }

    @Override
    public Fine[] findByClient(Client client) throws InternalException, NoFineFoundException
    {
        try
        {
            //TODO: implement
            ResultSet result = connection.prepareStatement("SELECT * FROM fines").executeQuery();

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
        return new Fine(fineId, fineTypeId, paid);
    }
}
