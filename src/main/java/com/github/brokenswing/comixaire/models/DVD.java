package com.github.brokenswing.comixaire.models;

import com.github.brokenswing.comixaire.utils.PrettyTimeTransformer;

import java.util.Date;

public class DVD extends LibraryItem
{
    private int duration;
    private String producer;
    private String[] casting;

    public DVD(String title, ConditionType condition, String location, Date createdOn, Date releasedOn, Integer[] bookings, String[] categories, boolean available, int duration, String producer, String[] casting)
    {
        super(title, condition, location, createdOn, releasedOn, bookings, categories, available);
        this.duration = duration;
        this.producer = producer;
        this.casting = casting;
    }

    public DVD(int idLibraryItem, String title, ConditionType condition, String location, Date createdOn, Date releasedOn, Integer[] bookings, String[] categories, boolean available, int duration, String producer, String[] casting)
    {
        super(idLibraryItem, title, condition, location, createdOn, releasedOn, bookings, categories, available);
        this.duration = duration;
        this.producer = producer;
        this.casting = casting;
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

    public String getProducer()
    {
        return producer;
    }

    public void setProducer(String producer)
    {
        this.producer = producer;
    }

    public String[] getCasting()
    {
        return casting;
    }

    public void setCasting(String[] casting)
    {
        this.casting = casting;
    }
}
