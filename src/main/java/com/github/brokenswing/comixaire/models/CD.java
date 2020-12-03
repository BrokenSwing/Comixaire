package com.github.brokenswing.comixaire.models;

import java.util.Date;

public class CD extends LibraryItem{
    private int duration;
    private String artist;

    public CD(String title, ConditionType condition, String location, Date createdOn, Date releasedOn, Integer[] bookings, String[] categories, boolean available, int duration, String artist) {
        super(title, condition, location, createdOn, releasedOn, bookings, categories, available);
        this.duration = duration;
        this.artist = artist;
    }

    public CD(int idLibraryItem, String title, ConditionType condition, String location, Date createdOn, Date releasedOn, Integer[] bookings, String[] categories, boolean available, int duration, String artist) {
        super(idLibraryItem, title, condition, location, createdOn, releasedOn, bookings, categories, available);
        this.duration = duration;
        this.artist = artist;
    }
}
