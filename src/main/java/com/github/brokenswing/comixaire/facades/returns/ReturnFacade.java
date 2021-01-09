package com.github.brokenswing.comixaire.facades.returns;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.Facade;
import com.github.brokenswing.comixaire.facades.logs.LogsFacade;
import com.github.brokenswing.comixaire.models.LibraryItem;
import com.github.brokenswing.comixaire.models.Returns;

public class ReturnFacade extends Facade
{
    @InjectValue
    private LogsFacade logger;

    public ReturnFacade(DAOFactory factory)
    {
        super(factory);
    }

    public Returns create(Returns returns, LibraryItem libraryItem) throws InternalException
    {
        libraryItem.setAvailable(true);
        factory.getLibraryItemDAO().update(libraryItem);
        logger.log("Return item " + libraryItem.getIdLibraryItem(), libraryItem.getTitle() + " is now available");
        return factory.getReturnsDAO().create(returns);
    }
}
