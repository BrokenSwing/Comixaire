package com.github.brokenswing.comixaire.facades.clients;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.exception.CardIdAlreadyExist;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.models.Client;

public class ClientsFacade
{
    private final DAOFactory factory;

    public ClientsFacade(DAOFactory factory)
    {
        this.factory = factory;
    }

    public void create(Client client) throws InternalException, CardIdAlreadyExist
    {
        this.factory.getClientDAO().create(client);
    }
}
