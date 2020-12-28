package com.github.brokenswing.comixaire.models.builder;

import com.github.brokenswing.comixaire.models.CD;
import com.github.brokenswing.comixaire.models.ConditionType;

import java.util.Date;
import java.util.Objects;

class CDBuilder implements CDStep, LibraryItemStep
{

    private final LibraryItemBuilder builder;

    private String artist;
    private Integer duration;

    CDBuilder(LibraryItemBuilder builder, CD cd)
    {
        this(builder);
        this.artist = cd.getArtist();
        this.duration = cd.getDuration();
    }

    CDBuilder(LibraryItemBuilder builder)
    {
        this.builder = builder;
    }


    @Override
    public CD build()
    {
        builder.validate();
        Objects.requireNonNull(artist);
        Objects.requireNonNull(duration);
        return new CD(
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
                artist
        );
    }

    @Override
    public CDStep duration(int seconds)
    {
        this.duration = seconds;
        return this;
    }

    @Override
    public CDStep artist(String artist)
    {
        this.artist = artist;
        return this;
    }

    @Override
    public BookStep book()
    {
        return builder.book();
    }

    @Override
    public DVDStep dvd()
    {
        return builder.dvd();
    }

    @Override
    public CDStep cd()
    {
        return this;
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
