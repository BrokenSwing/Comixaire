package com.github.brokenswing.comixaire.models;

public enum ConditionType
{
    NEW("Brand new"),
    LIKE_NEW("As new"),
    VERY_GOOD("Very good"),
    GOOD("Good"),
    QUITE_GOOD("Quite good"),
    SATISFYING("Satisfying"),
    MEDIUM("Medium"),
    BAD("Bad");

    private final String label;

    ConditionType(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return this.label;
    }

}
