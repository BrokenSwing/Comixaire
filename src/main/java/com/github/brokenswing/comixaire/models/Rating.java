package com.github.brokenswing.comixaire.models;

public class Rating
{
    private Client client;
    private LibraryItem libraryItem;
    private int note;

    public Rating(Client client, LibraryItem libraryItem, int note)
    {
        this.client = client;
        this.libraryItem = libraryItem;
        this.note = note;
    }

    public Client getClient()
    {
        return client;
    }

    public LibraryItem getLibraryItem()
    {
        return libraryItem;
    }

    public int getNote()
    {
        return note;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public void setLibraryItem(LibraryItem libraryItem)
    {
        this.libraryItem = libraryItem;
    }

    public void setNote(int note)
    {
        this.note = note;
    }
}
