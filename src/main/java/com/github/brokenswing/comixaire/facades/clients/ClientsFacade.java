package com.github.brokenswing.comixaire.facades.clients;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.exception.CardIdAlreadyExist;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.facades.Facade;
import com.github.brokenswing.comixaire.models.Client;

public class ClientsFacade extends Facade
{

    public ClientsFacade(DAOFactory factory)
    {
        super(factory);
    }

    public Client create(Client client) throws InternalException, CardIdAlreadyExist
    {
        return this.factory.getClientDAO().create(client);
    }

    public Client findByCardId(String cardId) throws InternalException, NoClientFoundException
    {
        return this.factory.getClientDAO().findByCardID(cardId);
    }

    public Client[] findByFirstname(String firstname) throws InternalException, NoClientFoundException
    {
        return this.factory.getClientDAO().findByFirstname(firstname);
    }

    public Client[] findByLastname(String lastname) throws InternalException, NoClientFoundException
    {
        return this.factory.getClientDAO().findByLastname(lastname);
    }

    public Client[] findByName(String firstname, String lastname) throws InternalException, NoClientFoundException
    {
        return this.factory.getClientDAO().findByName(firstname, lastname);
    }
}
