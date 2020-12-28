package com.github.brokenswing.comixaire.models.builder;

import com.github.brokenswing.comixaire.models.*;

import java.util.Date;
import java.util.Objects;

public class LibraryItemBuilder implements LibraryItemStep
{

    int itemId;
    String title;
    Date createdOn;
    Date releasedOn;
    ConditionType condition;
    String location;
    Integer[] bookings;
    String[] categories;
    Boolean available;

    private LibraryItemBuilder()
    {
        this.itemId = -1;
        this.bookings = new Integer[0];
        this.categories = new String[0];
    }

    LibraryItemBuilder(LibraryItem item)
    {
        this.itemId = item.getIdLibraryItem();
        this.title = item.getTitle();
        this.createdOn = item.getCreatedOn();
        this.releasedOn = item.getReleasedOn();
        this.condition = item.getCondition();
        this.location = item.getLocation();
        this.bookings = item.getBookings();
        this.categories = item.getCategories();
        this.available = item.isAvailable();
    }

    public static LibraryItemStep create()
    {
        return new LibraryItemBuilder();
    }

    public static LibraryItemStep from(Book book)
    {
        LibraryItemBuilder builder = new LibraryItemBuilder(book);
        return new BookBuilder(builder, book);
    }

    public static LibraryItemStep from(DVD dvd)
    {
        LibraryItemBuilder builder = new LibraryItemBuilder(dvd);
        return new DVDBuilder(builder, dvd);
    }

    public static LibraryItemStep from(CD cd)
    {
        LibraryItemBuilder builder = new LibraryItemBuilder(cd);
        return new CDBuilder(builder, cd);
    }

    public static LibraryItemStep from(Game game)
    {
        LibraryItemBuilder builder = new LibraryItemBuilder(game);
        return new GameBuilder(builder, game);
    }

    @Override
    public LibraryItemStep id(int itemId)
    {
        this.itemId = itemId;
        return this;
    }

    @Override
    public LibraryItemStep available(boolean available)
    {
        this.available = available;
        return this;
    }

    @Override
    public LibraryItemStep title(String title)
    {
        this.title = title;
        return this;
    }

    @Override
    public LibraryItemStep createdOn(Date createdOn)
    {
        this.createdOn = createdOn;
        return this;
    }

    @Override
    public LibraryItemStep releasedOn(Date releasedOn)
    {
        this.releasedOn = releasedOn;
        return this;
    }

    @Override
    public LibraryItemStep condition(ConditionType condition)
    {
        this.condition = condition;
        return this;
    }

    @Override
    public LibraryItemStep location(String location)
    {
        this.location = location;
        return this;
    }

    @Override
    public LibraryItemStep categories(String[] categories)
    {
        this.categories = categories;
        return this;
    }

    @Override
    public LibraryItemStep bookings(Integer[] bookings)
    {
        this.bookings = bookings;
        return this;
    }

    void validate()
    {
        Objects.requireNonNull(title);
        Objects.requireNonNull(createdOn);
        Objects.requireNonNull(releasedOn);
        Objects.requireNonNull(condition);
        Objects.requireNonNull(location);
        Objects.requireNonNull(bookings);
        Objects.requireNonNull(categories);
        Objects.requireNonNull(available);
    }

    /////////////////////////////////////////
    ///            Sub-builders           ///
    /////////////////////////////////////////

    @Override
    public BookStep book()
    {
        return new BookBuilder(this);
    }

    @Override
    public DVDStep dvd()
    {
        return new DVDBuilder(this);
    }

    @Override
    public CDStep cd()
    {
        return new CDBuilder(this);
    }

    @Override
    public GameStep game()
    {
        return new GameBuilder(this);
    }

}
