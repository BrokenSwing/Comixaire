package com.github.brokenswing.comixaire.facades.booking;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.di.InjectValue;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.facades.Facade;
import com.github.brokenswing.comixaire.facades.logs.LogsFacade;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.LibraryItem;

public class BookingFacade extends Facade
{

    @InjectValue
    private LogsFacade logger;

    public BookingFacade(DAOFactory factory)
    {
        super(factory);
    }

    public void addBooking(LibraryItem libraryItem, Client client) throws InternalException
    {
        libraryItem.addBooking(client);
        this.factory.getLibraryItemDAO().updateBooking(libraryItem);
        logger.log("Add Booking", "Client: " + client.getIdClient() + " / Title: " + libraryItem.getTitle());
    }

    public void deleteBooking(LibraryItem libraryItem, Client client) throws InternalException
    {
        libraryItem.removeBooking(client);
        this.factory.getLibraryItemDAO().update(libraryItem);
        logger.log("Remove Booking", "Client: " + client.getIdClient() + " / Title: " + libraryItem.getTitle());
    }

}
