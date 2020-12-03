package com.github.brokenswing.comixaire.models;

import java.util.Date;

public class Book extends LibraryItem{
    private String author;
    private String ISBN;
    private String publisher;
    private int pagesCount;

    public Book(String title, ConditionType condition, String location, Date createdOn, Date releasedOn, Integer[] bookings, String[] categories, boolean available, String author, String ISBN, String publisher, int pagesCount) {
        super(title, condition, location, createdOn, releasedOn, bookings, categories, available);
        this.author = author;
        this.ISBN = ISBN;
        this.publisher = publisher;
        this.pagesCount = pagesCount;
    }

    public Book(int idLibraryItem, String title, ConditionType condition, String location, Date createdOn, Date releasedOn, Integer[] bookings, String[] categories, boolean available, String author, String ISBN, String publisher, int pagesCount) {
        super(idLibraryItem, title, condition, location, createdOn, releasedOn, bookings, categories, available);
        this.author = author;
        this.ISBN = ISBN;
        this.publisher = publisher;
        this.pagesCount = pagesCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }
}
