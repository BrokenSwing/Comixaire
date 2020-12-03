package com.github.brokenswing.comixaire.models;

import java.util.*;

public abstract class LibraryItem {
    private final int idLibraryItem;
    private String title;
    private ConditionType condition;
    private String location;
    private Date createdOn;
    private Date releasedOn;
    private Queue<Integer> bookings;
    private ArrayList<String> categories;
    private boolean available;

    public LibraryItem(String title, ConditionType condition, String location, Date createdOn, Date releasedOn, Integer[] bookings, String[] categories, boolean available) {
        this(-1, title, condition, location, createdOn, releasedOn, bookings, categories, available);
    }

    public LibraryItem(int idLibraryItem, String title, ConditionType condition, String location, Date createdOn, Date releasedOn, Integer[] bookings, String[] categories, boolean available) {
        this.idLibraryItem = idLibraryItem;
        this.title = title;
        this.condition = condition;
        this.location = location;
        this.createdOn = createdOn;
        this.releasedOn = releasedOn;
        this.bookings = new LinkedList<>();
        this.bookings.addAll(Arrays.asList(bookings));
        this.categories = new ArrayList<>();
        this.categories.addAll(Arrays.asList(categories));
        this.available = available;
    }

    public int getIdLibraryItem() {
        return idLibraryItem;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ConditionType getCondition() {
        return condition;
    }

    public void setCondition(ConditionType condition) {
        this.condition = condition;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Date getReleasedOn() {
        return releasedOn;
    }

    public Integer[] getBookings() {
        return (Integer[]) bookings.toArray();
    }

    public boolean addBooking(Client client) {
        return bookings.offer(client.getIdClient());
    }

    public int pollBooking(){
        return bookings.poll();
    }

    public String[] getCategories() {
        return (String[]) categories.toArray();
    }

    public boolean removeCategory(String category){
        return categories.remove(category);
    }

    public boolean addCategory(String category) {
        return categories.add(category);
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
