package com.github.brokenswing.comixaire.models;

import java.sql.Date;

public class Subscription
{
    private final int idSubscription;
    private final Date from;
    private final Date to;

    public Subscription(int idSubscription, Date from, Date to)
    {
        this.idSubscription = idSubscription;
        this.from = from;
        this.to = to;
    }

    public int getIdSubscription()
    {
        return idSubscription;
    }

    public Date getFrom()
    {
        return from;
    }

    public Date getTo()
    {
        return to;
    }
}
