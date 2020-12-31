package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.SubscriptionsDAO;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.models.Subscription;

import java.sql.Connection;

public class PostgresSubscriptionsDAO implements SubscriptionsDAO
{
    private Connection connection;
    public PostgresSubscriptionsDAO(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public Subscription create(Subscription subscription) throws InternalException
    {
        //TODO: !!PROBLEME DE SPECIFICATION!!
        return null;
    }

    @Override
    public Subscription[] findAllByCardId(String idCard) throws InternalException, NoClientFoundException
    {
        //TODO: !!PROBLEME DE SPECIFICATION!!
        return new Subscription[0];
    }
}
