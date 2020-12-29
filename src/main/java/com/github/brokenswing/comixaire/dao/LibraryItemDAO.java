package com.github.brokenswing.comixaire.dao;

import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoLibraryItemFoundException;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.LibraryItem;

public interface LibraryItemDAO
{
    LibraryItem create(LibraryItem libraryItem) throws InternalException;

    LibraryItem[] findAll() throws InternalException;

    LibraryItem findById(int libraryItem) throws InternalException, NoLibraryItemFoundException;

    void update(LibraryItem libraryItem) throws InternalException;

    void addBooking(LibraryItem libraryItem, Client client);

    void delete(LibraryItem libraryItem) throws InternalException;

    String[] getCategories() throws InternalException;

    String[] getCastings() throws InternalException;

}
