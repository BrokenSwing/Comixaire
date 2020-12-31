package com.github.brokenswing.comixaire.facades.subscriptions;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.facades.Facade;
import com.github.brokenswing.comixaire.models.Subscription;

public class SubscriptionsFacade extends Facade
{
    public SubscriptionsFacade(DAOFactory factory)
    {
        super(factory);
    }

    public Subscription create(Subscription subscription) throws InternalException
    {
        return this.factory.getSubscriptionsDAO().create(subscription);
    }

    public Subscription[] findAllByCardId(String idCard) throws InternalException, NoClientFoundException
    {
        return this.factory.getSubscriptionsDAO().findAllByCardId(idCard);
    }
}
