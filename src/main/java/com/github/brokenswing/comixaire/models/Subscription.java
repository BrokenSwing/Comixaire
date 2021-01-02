package com.github.brokenswing.comixaire.models;

import com.github.brokenswing.comixaire.utils.PrettyTimeTransformer;

import java.util.Date;

public class Subscription
{
    private final int idSubscription;
    private final Date from;
    private final Date to;
    private final Client client;

    public Subscription(int idSubscription, Date from, Date to, Client client)
    {
        this.idSubscription = idSubscription;
        this.from = from;
        this.to = to;
        this.client = client;
    }

    public Subscription(Date from, Date to, Client client)
    {
        this(-1, from, to, client);
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

    public Client getClient()
    {
        return client;
    }

    public String toString(){
        return "From: " + PrettyTimeTransformer.prettyDate(from) + "\tTo: " + PrettyTimeTransformer.prettyDate(to);
    }
}
