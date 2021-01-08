package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.FineTypeDAO;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.models.FineType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PostgresFineTypeDAO implements FineTypeDAO
{
    private final Connection connection;

    public PostgresFineTypeDAO(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public FineType[] getAllFineTypes() throws InternalException
    {
        try
        {
            PreparedStatement prepare = connection.prepareStatement("SELECT * FROM fineType");
            ResultSet result = prepare.executeQuery();
            ArrayList<FineType> types = new ArrayList<>();
            while (result.next())
            {
                types.add(fineTypeFromRow(result));
            }
            return types.toArray(new FineType[0]);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to find fine types.", e);
        }
    }

    private FineType fineTypeFromRow(ResultSet result) throws SQLException
    {
        int fineTypeId = result.getInt("fineType_id");
        String label = result.getString("fineType_label");
        int price = result.getInt("fineType_price");
        return new FineType(fineTypeId, label, price);
    }
}
