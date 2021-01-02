package com.github.brokenswing.comixaire.dao;

import com.github.brokenswing.comixaire.exception.*;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.Fine;

public interface FineDAO
{
    void create(Fine fine) throws InternalException, NoReturnFoundException, NonValidFineTypeException;

    Fine[] findByClient(Client client) throws InternalException, NoClientFoundException;

    void pay(Fine fine) throws InternalException;

    void delete(Fine fine) throws InternalException;
}
