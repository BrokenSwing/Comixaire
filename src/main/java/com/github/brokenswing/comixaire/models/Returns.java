package com.github.brokenswing.comixaire.models;

import java.util.Date;

public class Returns
{
    private final int idLoan;
    private final Date date;

    public Returns(int idLoan, Date date)
    {
        this.idLoan = idLoan;
        this.date = date;
    }

    public Returns(int idLoan)
    {
        this(idLoan, new Date());
    }

    public int getIdLoan()
    {
        return idLoan;
    }

    public Date getDate()
    {
        return date;
    }
}
