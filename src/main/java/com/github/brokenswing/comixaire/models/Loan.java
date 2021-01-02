package com.github.brokenswing.comixaire.models;

import java.util.Date;

public class Loan
{
    private final int idLoan;
    private final Date from;
    private final Date to;
    private final Client client;
    private final LibraryItem libraryItem;

    public Loan(int idLoan, Date from, Date to, Client client, LibraryItem libraryItem)
    {
        this.idLoan = idLoan;
        this.from = from;
        this.to = to;
        this.client = client;
        this.libraryItem = libraryItem;
    }

    public int getIdLoan()
    {
        return idLoan;
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

    public LibraryItem getLibraryItem()
    {
        return libraryItem;
    }
}
