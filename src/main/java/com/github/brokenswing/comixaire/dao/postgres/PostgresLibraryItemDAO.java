package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.LibraryItemDAO;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoLibraryItemFoundException;
import com.github.brokenswing.comixaire.models.*;

import java.sql.Connection;

public class PostgresLibraryItemDAO implements LibraryItemDAO
{
    private Connection connection;

    public PostgresLibraryItemDAO(Connection connection) { this.connection = connection; }

    @Override
    public LibraryItem create(LibraryItem libraryItem) throws InternalException
    {
        //TODO: create libraryItem and get back the id
        if(libraryItem instanceof Book) {
            //TODO: create book with the libraryItem id
        }
        else if (libraryItem instanceof CD) {
            //TODO: create CD with the libraryItem id
        }
        else if(libraryItem instanceof DVD){
            //TODO: create DVD with the libraryItem id
        }
        else{
            //TODO: create game with the libraryItem id
        }
        return null;
    }

    @Override
    public LibraryItem[] findAll() throws InternalException
    {
        //TODO: implement
        return new LibraryItem[0];
    }

    @Override
    public LibraryItem findById(int libraryItem) throws InternalException, NoLibraryItemFoundException
    {
        //TODO: implement
        return null;
    }

    @Override
    public LibraryItem[] findByTitle(String title) throws InternalException, NoLibraryItemFoundException
    {
        //TODO: implement
        return new LibraryItem[0];
    }

    @Override
    public void update(LibraryItem libraryItem) throws InternalException
    {
        //TODO: implement
    }

    @Override
    public void addBooking(LibraryItem libraryItem, Client client)
    {
        //TODO: implement
    }

    @Override
    public void delete(LibraryItem libraryItem) throws InternalException
    {
        //TODO: implement
    }
}
