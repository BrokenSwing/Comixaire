package com.github.brokenswing.comixaire.facades.returns;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.Facade;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Returns;

public class ReturnFacade extends Facade
{
    public ReturnFacade(DAOFactory factory)
    {
        super(factory);
    }

    public Returns create(Returns returns, LibraryItem libraryItem) throws InternalException
    {
        libraryItem.setAvailable(true);
        factory.getLibraryItemDAO().update(libraryItem);
        return factory.getReturnsDAO().create(returns);
    }
}
