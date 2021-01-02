package com.github.brokenswing.comixaire.models;

public class Fine
{
    private final int idFine;
    private final int fineTypeId;
    private final String label;
    private final int price;
    private boolean paid;

    public Fine(int idFine, boolean paid, int fineTypeId, String label, int price)
    {
        this.idFine = idFine;
        this.paid = paid;
        this.fineTypeId = fineTypeId;
        this.label = label;
        this.price = price;
    }

    public int getId()
    {
        return idFine;
    }

    public boolean isPaid()
    {
        return paid;
    }

    public String getLabel()
    {
        return label;
    }

    public int getPrice()
    {
        return price;
    }

    public int getIdType()
    {
        return fineTypeId;
    }
}
