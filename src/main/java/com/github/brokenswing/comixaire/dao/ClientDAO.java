package com.github.brokenswing.comixaire.dao;

import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.models.Client;

/**
 * Data access object to the clients. This interface allows
 * to manipulate clients without being aware of the underlying
 * system that stores the data.
 */
public interface ClientDAO
{

    Client create(Client client) throws InternalException;

    Client findById(int idClient) throws InternalException, NoClientFoundException;

    /**
     * @param cardID the card ID of the client to find
     * @return the client with the given card ID
     * @throws InternalException      if an expected error occurs
     * @throws NoClientFoundException if no client with the given card ID was found
     */
    Client findByCardID(String cardID) throws InternalException, NoClientFoundException;

    Client[] findByName(String firstname, String lastname) throws InternalException, NoClientFoundException;

    Client[] findByFirstname(String firstname) throws InternalException, NoClientFoundException;

    Client[] findByLastname(String lastname) throws InternalException, NoClientFoundException;

    void update(Client client) throws InternalException;

    void delete(Client client) throws InternalException;

}
