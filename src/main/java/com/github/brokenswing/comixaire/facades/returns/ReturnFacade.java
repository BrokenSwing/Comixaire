package com.github.brokenswing.comixaire.facades.returns;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.Facade;
import com.github.brokenswing.comixaire.models.Returns;

public class ReturnFacade extends Facade
{
    public ReturnFacade(DAOFactory factory)
    {
        super(factory);
    }

    public Returns create(Returns returns) throws InternalException
    {
        return factory.getReturnsDAO().create(returns);
    }
}
