package com.github.brokenswing.comixaire.models;

import java.util.Date;

public class DVD extends LibraryItem{
    private int duration;
    private String producer;
    private String[] casting;

    public DVD(String title, ConditionType condition, String location, Date createdOn, Date releasedOn, Integer[] bookings, String[] categories, boolean available, int duration, String producer, String[] casting) {
        super(title, condition, location, createdOn, releasedOn, bookings, categories, available);
        this.duration = duration;
        this.producer = producer;
        this.casting = casting;
    }

    public DVD(int idLibraryItem, String title, ConditionType condition, String location, Date createdOn, Date releasedOn, Integer[] bookings, String[] categories, boolean available, int duration, String producer, String[] casting) {
        super(idLibraryItem, title, condition, location, createdOn, releasedOn, bookings, categories, available);
        this.duration = duration;
        this.producer = producer;
        this.casting = casting;
    }


    public int getDuration() {
        return duration;
    }

    public String getPrettyDuration() {
        StringBuilder sb = new StringBuilder();

        int hours = this.getDuration() / 3600;
        int minutes = (this.getDuration() - hours * 3600) / 60;
        int seconds = this.getDuration() - hours * 3600 - minutes * 60;

        if(hours > 0) {
            sb.append(hours + "h ");
        }
        if(minutes > 0 || hours > 0) {
            sb.append(minutes + "min ");
        }
        sb.append(seconds + "s");

        return sb.toString();
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String[] getCasting() {
        return casting;
    }

    public void setCasting(String[] casting) {
        this.casting = casting;
    }
}
