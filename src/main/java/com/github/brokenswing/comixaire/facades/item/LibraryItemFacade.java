package com.github.brokenswing.comixaire.facades.item;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.Facade;
import com.github.brokenswing.comixaire.models.LibraryItem;

public class LibraryItemFacade extends Facade
{

    public LibraryItemFacade(DAOFactory factory)
    {
        super(factory);
    }

    public void create(LibraryItem item) throws InternalException
    {
        this.factory.getLibraryItemDAO().create(item);
    }

    public void update(LibraryItem item) throws InternalException
    {
        this.factory.getLibraryItemDAO().update(item);
    }

    public String[] getKnownCategories() throws InternalException
    {
        return this.factory.getLibraryItemDAO().getCategories();
    }

}
