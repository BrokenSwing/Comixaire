package com.github.brokenswing.comixaire.models;

public class Fine
{
    private final int idFine;
    private final FineType fine;
    private boolean paid;

    public Fine(int idFine, FineType fine, boolean paid)
    {
        this.idFine = idFine;
        this.fine = fine;
        this.paid = paid;
    }

    public int getId()
    {
        return idFine;
    }

    public boolean isPaid()
    {
        return paid;
    }

    public void setPaid(boolean paid)
    {
        this.paid = paid;
    }

    public String getLabel()
    {
        return fine.getLabel();
    }

    public double getPrice()
    {
        return fine.getPrice();
    }
}
