package com.github.brokenswing.comixaire.models;

public enum FineType
{

    DAMAGE("Damages level 1", 2.5f, 0),
    DAMAGE2("Damages level 2", 5f, 1),
    DAMAGE3("Damages level 3", 10f, 2),
    LATE("Late < 1 week", 1.5f, 3),
    LATE2("Late < 2 weeks", 3.5f, 4),
    LATE3("Late > 2 weeks", 7f, 5);

    private final String label;
    private final float price;
    private final int idType;

    FineType(String label, float price, int idType)
    {
        this.label = label;
        this.price = price;
        this.idType = idType;
    }

    public String getLabel()
    {
        return this.label;
    }

    public double getPrice()
    {
        return this.price;
    }

    public int getIdType() { return this.idType; }

}
