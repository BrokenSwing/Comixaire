package com.github.brokenswing.comixaire.dao;

import com.github.brokenswing.comixaire.exception.CardIdAlreadyExist;
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

    Client create(Client client) throws InternalException, CardIdAlreadyExist;

    Client findById(int idClient) throws InternalException, NoClientFoundException;

    /**
     * @param cardID the card ID of the client to find
     * @return the client with the given card ID
     * @throws InternalException      if an expected error occurs
     * @throws NoClientFoundException if no client with the given card ID was found
     */
    Client findByCardID(String cardID) throws InternalException, NoClientFoundException;

    Client[] findAll() throws InternalException;

    void update(Client client) throws InternalException, CardIdAlreadyExist;

    void delete(Client client) throws InternalException;

    int countLoans(Client client) throws InternalException;

    int countFines(Client client) throws InternalException;

    int countVotes(Client client) throws InternalException;

    int countCurrentLoans(Client client) throws InternalException;

    Boolean validSubscription(Client client) throws InternalException;

    int countAll() throws InternalException;

}
