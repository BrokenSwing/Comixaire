package com.github.brokenswing.comixaire.models;

import java.util.Calendar;
import java.util.Date;

public class Returns
{
    private final int idReturn;
    private final int idLoan;
    private final Date date;

    public Returns(int idReturn, int idLoan, Date date)
    {
        this.idReturn = idReturn;
        this.idLoan = idLoan;
        this.date = date;
    }

    public Returns(int idLoan, Date date)
    {
        this(-1, idLoan, date);
    }

    public Returns(int idLoan)
    {
        this(-1, idLoan, new Date());
    }

    public int getIdLoan()
    {
        return idLoan;
    }

    public Date getDate()
    {
        return date;
    }

    public int getIdReturn()
    {
        return idReturn;
    }
}
