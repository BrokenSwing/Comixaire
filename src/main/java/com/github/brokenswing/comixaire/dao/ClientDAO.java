package com.github.brokenswing.comixaire.dao;

import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.models.Client;

public interface ClientDAO
{

    Client create(Client client) throws InternalException;

    Client findById(int idClient) throws InternalException, NoClientFoundException;

    Client[] findByName(String firstname, String lastname) throws InternalException, NoClientFoundException;

    Client[] findByFirstname(String firstname) throws InternalException, NoClientFoundException;

    Client[] findByLastname(String lastname) throws InternalException, NoClientFoundException;

    void update(Client client) throws InternalException;

    void delete(Client client) throws InternalException;

}
