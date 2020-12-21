package com.github.brokenswing.comixaire.models;

import java.sql.Date;
import java.util.Calendar;

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
        this.idLoan = idLoan;
        this.date = new java.sql.Date(Calendar.getInstance().getTime().getTime()); //now
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
