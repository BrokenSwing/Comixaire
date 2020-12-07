package com.github.brokenswing.comixaire.models;

public enum FineType
{

    DAMAGE("Damages level 1", 2.5f),
    DAMAGE2("Damages level 2", 5f),
    DAMAGE3("Damages level 3", 10f),
    LATE("Late < 1 week", 1.5f),
    LATE2("Late < 2 weeks", 3.5f),
    LATE3("Late > 2 weeks", 7f);

    private final String label;
    private final float price;

    FineType(String label, float price)
    {
        this.label = label;
        this.price = price;
    }

    public String getLabel()
    {
        return this.label;
    }

    public double getPrice()
    {
        return this.price;
    }

}
