package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.SubscriptionsDAO;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.models.Loan;
import com.github.brokenswing.comixaire.models.Subscription;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PostgresSubscriptionsDAO implements SubscriptionsDAO
{
    private Connection connection;
    public PostgresSubscriptionsDAO(Connection connection)
    {
        this.connection = connection;
    }

    public static Subscription subscriptionFromRow(ResultSet resultSet) throws SQLException
    {
        return new Subscription(
                resultSet.getInt("subscription_id"),
                new java.util.Date(resultSet.getDate("subscription_from").getTime()),
                new java.util.Date(resultSet.getDate("subscription_to").getTime()),
                PostgresClientDAO.clientFromRow(resultSet)
        );
    }

    @Override
    public Subscription create(Subscription subscription) throws InternalException
    {
        try
        {
            PreparedStatement prepare = this.connection.prepareStatement("INSERT INTO subscriptions(subscription_from, subscription_to, client_id) VALUES(?, ?, ?) RETURNING subscription_id");
            prepare.setDate(1, new Date(subscription.getFrom().getTime()));
            prepare.setDate(2, new Date(subscription.getTo().getTime()));
            prepare.setInt(3, subscription.getClient().getIdClient());
            ResultSet result = prepare.executeQuery();
            result.next();
            int idSub = result.getInt("subscription_id");
            return new Subscription(idSub, subscription.getFrom(), subscription.getTo(), subscription.getClient());
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to create new subscription", e);
        }
    }

    @Override
    public Subscription[] findAllByCardId(String idCard) throws InternalException
    {
        try
        {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM subscriptions " +
                            "NATURAL LEFT JOIN clients " +
                            "WHERE client_cardid = ?");
            stmt.setString(1, idCard);

            ResultSet result = stmt.executeQuery();
            List<Subscription> subscriptions = new LinkedList<>();
            while (result.next())
            {
                subscriptions.add(subscriptionFromRow(result));
            }
            return subscriptions.toArray(new Subscription[0]);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to find subscriptions for card ID " + idCard, e);
        }
    }
}
