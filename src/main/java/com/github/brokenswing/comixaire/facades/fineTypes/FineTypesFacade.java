package com.github.brokenswing.comixaire.facades.fineTypes;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.Facade;
import com.github.brokenswing.comixaire.models.FineType;

public class FineTypesFacade extends Facade
{
    public FineTypesFacade(DAOFactory factory)
    {
        super(factory);
    }

    public FineType[] getAllFineTypes() throws InternalException
    {
        return this.factory.getFineTypeDAO().getAllFineTypes();
    }
}
