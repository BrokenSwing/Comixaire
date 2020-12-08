package com.github.brokenswing.comixaire;

import com.github.brokenswing.comixaire.auth.AuthFacade;
import com.github.brokenswing.comixaire.auth.PlainTextPasswordAlgorithm;
import com.github.brokenswing.comixaire.auth.Session;
import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.dao.postgres.PostgresDAOFactory;
import com.github.brokenswing.comixaire.di.ValueProvider;

/**
 * This class defines the configuration for the whole project. It provides singleton values to be injected
 * in controllers using the class {@link com.github.brokenswing.comixaire.di.ControllerFactoryDI}.
 */
public class Configuration
{

    private final DAOFactory factory;
    private final Session session;

    public Configuration()
    {
        this.factory = new PostgresDAOFactory();
        this.session = new Session();
    }

    @ValueProvider
    public AuthFacade getAuthFacade()
    {
        return new AuthFacade(factory, new PlainTextPasswordAlgorithm(), this.session);
    }

}
