package com.github.brokenswing.comixaire.facades.logs;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.auth.Session;
import com.github.brokenswing.comixaire.models.Log;

import java.util.Date;

public class LogsFacade
{

    private final DAOFactory factory;
    private final Session session;

    public LogsFacade(DAOFactory factory, Session session)
    {
        this.factory = factory;
        this.session = session;
    }

    public Log[] getLogs() throws InternalException
    {
        return factory.getLogDAO().getAll();
    }

    public void log(String operationType, String operationDetails)
    {
        Log log = new Log(operationDetails, operationType, new Date(), session.getLoggedInStaff());
        try
        {
            factory.getLogDAO().create(log);
        }
        catch (InternalException e)
        {
            e.printStackTrace();
        }
    }

}
