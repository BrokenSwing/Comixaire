package com.github.brokenswing.comixaire.models.builder;

import com.github.brokenswing.comixaire.models.Book;
import com.github.brokenswing.comixaire.models.ConditionType;

import java.util.Date;
import java.util.Objects;

class BookBuilder implements BookStep, LibraryItemStep
{

    private final LibraryItemBuilder builder;

    private String author;
    private String publisher;
    private String isbn;
    private Integer pagesCount;

    BookBuilder(LibraryItemBuilder builder, Book book)
    {
        this(builder);
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.isbn = book.getISBN();
        this.pagesCount = book.getPagesCount();
    }

    BookBuilder(LibraryItemBuilder builder)
    {
        this.builder = builder;
    }

    @Override
    public Book build()
    {
        builder.validate();
        Objects.requireNonNull(author);
        Objects.requireNonNull(publisher);
        Objects.requireNonNull(isbn);
        Objects.requireNonNull(pagesCount);

        return new Book(
                builder.itemId,
                builder.title,
                builder.condition,
                builder.location,
                builder.createdOn,
                builder.releasedOn,
                builder.bookings,
                builder.categories,
                builder.available,
                author,
                isbn,
                publisher,
                pagesCount
        );
    }

    @Override
    public BookStep author(String author)
    {
        this.author = author;
        return this;
    }

    @Override
    public BookStep publisher(String publisher)
    {
        this.publisher = publisher;
        return this;
    }

    @Override
    public BookStep isbn(String isbn)
    {
        this.isbn = isbn;
        return this;
    }

    @Override
    public BookStep pagesCount(int count)
    {
        this.pagesCount = count;
        return this;
    }

    @Override
    public BookStep book()
    {
        return this;
    }

    @Override
    public DVDStep dvd()
    {
        return builder.dvd();
    }

    @Override
    public CDStep cd()
    {
        return builder.cd();
    }

    @Override
    public GameStep game()
    {
        return builder.game();
    }

    /////////////////////////////////
    //        Delegation           //
    /////////////////////////////////

    @Override
    public LibraryItemStep id(int itemId)
    {
        builder.id(itemId);
        return this;
    }

    @Override
    public LibraryItemStep available(boolean available)
    {
        builder.available(available);
        return this;
    }

    @Override
    public LibraryItemStep title(String title)
    {
        builder.title(title);
        return this;
    }

    @Override
    public LibraryItemStep createdOn(Date createdOn)
    {
        builder.createdOn(createdOn);
        return this;
    }

    @Override
    public LibraryItemStep releasedOn(Date releasedOn)
    {
        builder.releasedOn(releasedOn);
        return this;
    }

    @Override
    public LibraryItemStep condition(ConditionType condition)
    {
        builder.condition(condition);
        return this;
    }

    @Override
    public LibraryItemStep location(String location)
    {
        builder.location(location);
        return this;
    }

    @Override
    public LibraryItemStep categories(String[] categories)
    {
        builder.categories(categories);
        return this;
    }

    @Override
    public LibraryItemStep bookings(Integer[] bookings)
    {
        builder.bookings(bookings);
        return this;
    }

}
