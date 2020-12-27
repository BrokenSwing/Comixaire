package com.github.brokenswing.comixaire.models.builder;

import com.github.brokenswing.comixaire.models.ConditionType;
import com.github.brokenswing.comixaire.models.DVD;

import java.util.Date;
import java.util.Objects;

class DVDBuilder implements LibraryItemStep, DVDStep
{

    private final LibraryItemBuilder builder;

    private String producer;
    private Integer duration;
    private String[] casting;

    DVDBuilder(LibraryItemBuilder builder, DVD dvd)
    {
        this(builder);
        this.producer = dvd.getProducer();
        this.duration = dvd.getDuration();
        this.casting = dvd.getCasting();
    }

    DVDBuilder(LibraryItemBuilder builder)
    {
        this.builder = builder;
    }

    @Override
    public DVDStep producer(String producer)
    {
        this.producer = producer;
        return this;
    }

    @Override
    public DVDStep casting(String[] casting)
    {
        this.casting = casting;
        return this;
    }

    @Override
    public DVDStep duration(int seconds)
    {
        this.duration = seconds;
        return this;
    }

    @Override
    public DVD build()
    {
        builder.validate();
        Objects.requireNonNull(producer);
        Objects.requireNonNull(duration);
        Objects.requireNonNull(casting);
        return new DVD(
                builder.itemId,
                builder.title,
                builder.condition,
                builder.location,
                builder.createdOn,
                builder.releasedOn,
                builder.bookings,
                builder.categories,
                builder.available,
                duration,
                producer,
                casting
        );
    }

    @Override
    public BookStep book()
    {
        return builder.book();
    }

    @Override
    public DVDStep dvd()
    {
        return this;
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
