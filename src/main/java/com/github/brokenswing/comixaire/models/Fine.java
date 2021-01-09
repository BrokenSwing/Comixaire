package com.github.brokenswing.comixaire.models;

public class Fine
{
    private final int idFine;
    private final FineType fineType;
    private final boolean paid;

    public Fine(int idFine, boolean paid, FineType fineType)
    {
        this.idFine = idFine;
        this.paid = paid;
        this.fineType = fineType;
    }

    public int getId()
    {
        return idFine;
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
