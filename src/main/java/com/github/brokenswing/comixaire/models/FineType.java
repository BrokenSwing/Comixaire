package com.github.brokenswing.comixaire.models;

public enum FineType {
    DAMAGE(0),
    DAMAGE2(1),
    DAMAGE3(2),
    LATE(3),
    LATE2(4),
    LATE3(5);

    private int type;

    FineType(){
        this(0);
    }

    FineType(int type) {
        this.type = type;
    }

    public String getLabel(){
        switch (this){
            case DAMAGE:
                return "Damages level 1";
            case DAMAGE2:
                return "Damages level 2";
            case DAMAGE3:
                return "Damages level 3";
            case LATE:
                return "Late < 1week";
            case LATE2:
                return "Late < 2weeks";
            case LATE3:
                return "Late > 2weeks";
            default:
                return "Other fine";
        }
    }

    public double getPrice(){
        switch (this){
            case DAMAGE:
                return 2.5;
            case DAMAGE2:
                return 5;
            case DAMAGE3:
                return 10;
            case LATE:
                return 1.5;
            case LATE2:
                return 3.5;
            case LATE3:
                return 7;
            default:
                return 1;
        }
    }

}
