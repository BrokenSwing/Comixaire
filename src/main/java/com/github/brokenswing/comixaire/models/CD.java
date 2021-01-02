package com.github.brokenswing.comixaire.models;

import com.github.brokenswing.comixaire.utils.PrettyTimeTransformer;

import java.util.Date;

/**
 * This class represents a specific type of library item
 * that is handled by the software.
 * To avoid manually specifying each argument of the constructor,
 * the {@link com.github.brokenswing.comixaire.models.builder.LibraryItemBuilder}
 * should be preferred.
 */
public class CD extends LibraryItem
{
    private int duration;
    private String artist;

    public CD(String title, ConditionType condition, String location, Date createdOn, Date releasedOn, Integer[] bookings, String[] categories, boolean available, int duration, String artist)
    {
        super(title, condition, location, createdOn, releasedOn, bookings, categories, available);
        this.duration = duration;
        this.artist = artist;
    }

    public CD(int idLibraryItem, String title, ConditionType condition, String location, Date createdOn, Date releasedOn, Integer[] bookings, String[] categories, boolean available, int duration, String artist)
    {
        super(idLibraryItem, title, condition, location, createdOn, releasedOn, bookings, categories, available);
        this.duration = duration;
        this.artist = artist;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public String getPrettyDuration()
    {
        return PrettyTimeTransformer.prettyDuration(this.getDuration());
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public String toString() {
        return getTitle() + " located at " + getLocation() + " is a CD";
    }
}
