package com.github.brokenswing.comixaire.models;

import java.util.Date;

public class Game extends LibraryItem
{
    private String publisher;
    private int minPlayers;
    private int maxPlayers;
    private int minAge;
    private String contentInventory;

    public Game(String title, ConditionType condition, String location, Date createdOn, Date releasedOn, Integer[] bookings, String[] categories, boolean available, String publisher, int minPlayers, int maxPlayers, int minAge, String contentInventory)
    {
        super(title, condition, location, createdOn, releasedOn, bookings, categories, available);
        this.publisher = publisher;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.minAge = minAge;
        this.contentInventory = contentInventory;
    }

    public Game(int idLibraryItem, String title, ConditionType condition, String location, Date createdOn, Date releasedOn, Integer[] bookings, String[] categories, boolean available, String publisher, int minPlayers, int maxPlayers, int minAge, String contentInventory)
    {
        super(idLibraryItem, title, condition, location, createdOn, releasedOn, bookings, categories, available);
        this.publisher = publisher;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.minAge = minAge;
        this.contentInventory = contentInventory;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    public int getMinPlayers()
    {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers)
    {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers()
    {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    public int getMinAge()
    {
        return minAge;
    }

    public void setMinAge(int minAge)
    {
        this.minAge = minAge;
    }

    public String getContentInventory()
    {
        return contentInventory;
    }

    public void setContentInventory(String contentInventory)
    {
        this.contentInventory = contentInventory;
    }
}
