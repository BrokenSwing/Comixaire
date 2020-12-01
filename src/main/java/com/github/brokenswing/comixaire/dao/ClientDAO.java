package com.github.brokenswing.comixaire.dao;

import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.models.Client;

public interface ClientDAO
{

    Client getByClientId(String clientId) throws InternalException, NoClientFoundException;

}
