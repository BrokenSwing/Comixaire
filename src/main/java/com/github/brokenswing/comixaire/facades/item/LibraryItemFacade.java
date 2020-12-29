package com.github.brokenswing.comixaire.facades.item;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoLibraryItemFoundException;
import com.github.brokenswing.comixaire.facades.Facade;
import com.github.brokenswing.comixaire.facades.logs.LogsFacade;
import com.github.brokenswing.comixaire.models.LibraryItem;

public class LibraryItemFacade extends Facade
{

    @InjectValue
    private LogsFacade logger;

    public LibraryItemFacade(DAOFactory factory)
    {
        super(factory);
    }

    public void create(LibraryItem item) throws InternalException
    {
        this.factory.getLibraryItemDAO().create(item);
        logger.log("Created " + item.getClass().getSimpleName(), "Title: " + item.getTitle());
    }

    public void update(LibraryItem item) throws InternalException
    {
        this.factory.getLibraryItemDAO().update(item);
        logger.log("Updated " + item.getClass().getSimpleName(), "Title: " + item.getTitle());
    }

    public String[] getKnownCategories() throws InternalException
    {
        return this.factory.getLibraryItemDAO().getCategories();
    }

    public String[] getKnownCastings() throws InternalException
    {
        return this.factory.getLibraryItemDAO().getCastings();
    }

    public LibraryItem[] findAll() throws InternalException
    {
        return this.factory.getLibraryItemDAO().findAll();
    }

    public LibraryItem findById(int libraryItem) throws InternalException, NoLibraryItemFoundException
    {
        return this.factory.getLibraryItemDAO().findById(libraryItem);
    }


}
