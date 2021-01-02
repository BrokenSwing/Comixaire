package com.github.brokenswing.comixaire.dao;

import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.exception.NoFineFoundException;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.Fine;

public interface FineDAO
{
    Fine create(Fine fine) throws InternalException;

    Fine findById(int idFine) throws InternalException, NoFineFoundException;

    Fine[] findByClient(Client client) throws InternalException, NoClientFoundException, NoFineFoundException;

    void pay(Fine fine) throws InternalException;

    void delete(Fine fine) throws InternalException;
}
