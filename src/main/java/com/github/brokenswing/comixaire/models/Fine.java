package com.github.brokenswing.comixaire.models;

public class Fine
{
    private final int idLoan;
    private final FineType fineType;
    private final boolean paid;

    public Fine(int idLoan, boolean paid, FineType fineType)
    {
        this.idLoan = idLoan;
        this.paid = paid;
        this.fineType = fineType;
    }

    public int getId()
    {
        return idLoan;
    }

    public boolean isPaid()
    {
        return paid;
    }

    public FineType getFineType()
    {
        return fineType;
    }
}
