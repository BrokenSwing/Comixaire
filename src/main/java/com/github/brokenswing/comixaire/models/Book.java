package com.github.brokenswing.comixaire.models;

import java.util.Date;

public class Book extends LibraryItem
{
    private String author;
    private String isbn;
    private String publisher;
    private int pagesCount;

    public Book(String title, ConditionType condition, String location, Date createdOn, Date releasedOn, Integer[] bookings, String[] categories, boolean available, String author, String isbn, String publisher, int pagesCount)
    {
        super(title, condition, location, createdOn, releasedOn, bookings, categories, available);
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.pagesCount = pagesCount;
    }

    public Book(int idLibraryItem, String title, ConditionType condition, String location, Date createdOn, Date releasedOn, Integer[] bookings, String[] categories, boolean available, String author, String isbn, String publisher, int pagesCount)
    {
        super(idLibraryItem, title, condition, location, createdOn, releasedOn, bookings, categories, available);
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.pagesCount = pagesCount;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getISBN()
    {
        return isbn;
    }

    public void setISBN(String isbn)
    {
        this.isbn = isbn;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    public int getPagesCount()
    {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount)
    {
        this.pagesCount = pagesCount;
    }
}