package com.github.brokenswing.comixaire.models;

public enum ConditionType {
    NEW(0),
    LIKE_NEW(1),
    VERY_GOOD(2),
    GOOD(3),
    QUITE_GOOD(4),
    SATISFYING(5),
    MEDIUM(6),
    BAD(7);


    private int type;

    ConditionType(){
        this(0);
    }

    ConditionType(int type) {
        this.type = type;
    }

    public String getLabel(){
        switch (this){
            case NEW:
                return "Brand new";
            case LIKE_NEW:
                return "As new";
            case VERY_GOOD:
                return "Very good";
            case GOOD:
                return "Good";
            case QUITE_GOOD:
                return "Quite good";
            case SATISFYING:
                return "Satisfying";
            case MEDIUM:
                return "Medium";
            case BAD:
                return "Bad";
            default:
                return "No information";
        }
    }
}
