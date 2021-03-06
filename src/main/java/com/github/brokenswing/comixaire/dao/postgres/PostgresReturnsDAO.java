package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.ReturnsDAO;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.models.Returns;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresReturnsDAO implements ReturnsDAO
{

    private final Connection connection;

    public PostgresReturnsDAO(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public Returns create(Returns returns) throws InternalException
    {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO returns (return_date, loan_id) VALUES (?, ?)"))
        {
            statement.setDate(1, new java.sql.Date(returns.getDate().getTime()));
            statement.setInt(2, returns.getIdLoan());
            statement.executeUpdate();
            return returns;
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to create new return", e);
        }
    }

}
