package com.github.brokenswing.comixaire;

import com.github.brokenswing.comixaire.auth.AuthFacade;
import com.github.brokenswing.comixaire.auth.PlainTextPasswordAlgorithm;
import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.dao.postgres.PostgresDAOFactory;
import com.github.brokenswing.comixaire.di.ValueProvider;

public class Configuration
{

    private final DAOFactory factory;

    public Configuration()
    {
        this.factory = new PostgresDAOFactory();
    }

    @ValueProvider
    public AuthFacade getAuthFacade()
    {
        return new AuthFacade(factory, new PlainTextPasswordAlgorithm());
    }

}
