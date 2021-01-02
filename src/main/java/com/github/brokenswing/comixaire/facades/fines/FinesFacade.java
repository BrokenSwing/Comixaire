package com.github.brokenswing.comixaire.facades.fines;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.*;
import com.github.brokenswing.comixaire.facades.Facade;
import com.github.brokenswing.comixaire.facades.logs.LogsFacade;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.Fine;

public class FinesFacade extends Facade
{
    @InjectValue
    private LogsFacade logger;

    public FinesFacade(DAOFactory factory)
    {
        super(factory);
    }

    public void create(Fine fine) throws InternalException, NoReturnFoundException, InvalidFineTypeException
    {
        this.factory.getFineDAO().create(fine);
        logger.log("Created fine: " + fine.getLabel(), "Price: " + fine.getPrice());
    }

    public void delete(Fine fine) throws InternalException
    {
        this.factory.getFineDAO().delete(fine);
        logger.log("Deleted fine: " + fine.getLabel(), "Price: " + fine.getPrice());
    }

    public void pay(Fine fine) throws InternalException
    {
        this.factory.getFineDAO().pay(fine);
        logger.log("Pay fine: " + fine.getLabel(), "Price: " + fine.getPrice());
    }

    public Fine[] findByClient(Client client) throws InternalException, NoClientFoundException
    {
        return this.factory.getFineDAO().findByClient(client);
    }
}
