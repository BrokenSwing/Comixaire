package com.github.brokenswing.comixaire.models.builder;

import com.github.brokenswing.comixaire.models.ConditionType;
import com.github.brokenswing.comixaire.models.Game;

import java.util.Date;
import java.util.Objects;

class GameBuilder implements GameStep, LibraryItemStep
{

    private final LibraryItemBuilder builder;

    private String publisher;
    private String inventory;
    private Integer minPlayers;
    private Integer maxPlayers;
    private Integer minAge;

    GameBuilder(LibraryItemBuilder builder, Game game)
    {
        this(builder);
        this.publisher = game.getPublisher();
        this.inventory = game.getContentInventory();
        this.minPlayers = game.getMinPlayers();
        this.maxPlayers = game.getMaxPlayers();
        this.minAge = game.getMinAge();
    }

    GameBuilder(LibraryItemBuilder builder)
    {
        this.builder = builder;
    }

    @Override
    public GameStep publisher(String publisher)
    {
        this.publisher = publisher;
        return this;
    }

    @Override
    public GameStep minPlayers(int min)
    {
        this.minPlayers = min;
        return this;
    }

    @Override
    public GameStep maxPlayers(int max)
    {
        this.maxPlayers = max;
        return this;
    }

    @Override
    public GameStep inventory(String inventory)
    {
        this.inventory = inventory;
        return this;
    }

    @Override
    public GameStep minAge(int min)
    {
        this.minAge = min;
        return this;
    }

    @Override
    public Game build()
    {
        builder.validate();
        Objects.requireNonNull(publisher);
        Objects.requireNonNull(minPlayers);
        Objects.requireNonNull(maxPlayers);
        Objects.requireNonNull(minAge);
        Objects.requireNonNull(inventory);
        return new Game(
                builder.itemId,
                builder.title,
                builder.condition,
                builder.location,
                builder.createdOn,
                builder.releasedOn,
                builder.bookings,
                builder.categories,
                builder.available,
                publisher,
                minPlayers,
                maxPlayers,
                minAge,
                inventory
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
