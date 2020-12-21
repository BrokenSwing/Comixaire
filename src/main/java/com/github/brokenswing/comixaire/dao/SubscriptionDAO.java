package com.github.brokenswing.comixaire.dao;

import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.models.Subscription;

public interface SubscriptionDAO
{
    Subscription create(Subscription subscription) throws InternalException;

    Subscription[] findAllByCardId(String idCard) throws InternalException, NoClientFoundException;
}
