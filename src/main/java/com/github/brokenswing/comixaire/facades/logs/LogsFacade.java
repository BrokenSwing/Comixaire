package com.github.brokenswing.comixaire.facades.logs;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.models.Log;

public class LogsFacade
{

    private final DAOFactory factory;

    public LogsFacade(DAOFactory factory)
    {
        this.factory = factory;
    }

    public Log[] getLogs() throws InternalException
    {
        return factory.getLogDAO().getAll();
    }

}
