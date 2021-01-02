package com.github.brokenswing.comixaire.models;

public class Fine
{
    private final int idFine;
    private final FineType fine;
    private boolean paid;

    public Fine(int idFine, int fineTypeId, boolean paid)
    {
        this.idFine = idFine;
        this.paid = paid;
        this.fine = FineType.values()[fineTypeId];
        System.out.println("Fine type id: " + fineTypeId + " price: " + fine.getPrice() + " " + fine.getLabel());//TODO: remove this
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

    public int getIdType()
    {
        return fine.getIdType();
    }
}
