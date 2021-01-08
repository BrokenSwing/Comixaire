package com.github.brokenswing.comixaire.models;

public class FineType
{

    //DAMAGE("Damages level 1", 3, 0),
    //DAMAGE2("Damages level 2", 5, 1),
    //DAMAGE3("Damages level 3", 10, 2),
    //LATE("Late < 1 week", 2, 3),
    //LATE2("Late < 2 weeks", 4, 4),
    //LATE3("Late > 2 weeks", 7, 5),
    //NONE("None", 0, 6);

    private final int id;
    private final String label;
    private final int price;

    public FineType(int id, String label, int price)
    {
        this.id = id;
        this.label = label;
        this.price = price;
    }

    public String getLabel()
    {
        return this.label;
    }

    public int getPrice()
    {
        return this.price;
    }

    public int getId() { return this.id; }

    @Override
    public String toString()
    {
        return label;
    }
}
