package com.github.brokenswing.comixaire.models;

import java.util.Date;

public class Client
{

    private final int idClient;
    private final Date birthdate;
    private final String cardId;
    private String firstname;
    private String lastname;
    private String gender;
    private String address;
    private int subscriptionId;

    public Client(String firstname, String lastname, String cardId, String gender, String address, Date birthdate)
    {
        this(-1, firstname, lastname, cardId, gender, address, birthdate, -1);
    }

    public Client(String firstname, String lastname, String cardId, String gender, String address, Date birthdate, int subscriptionId)
    {
        this(-1, firstname, lastname, cardId, gender, address, birthdate, subscriptionId);
    }

    public Client(int idClient, String firstname, String cardId, String lastname, String gender, String address, Date birthdate, int subscriptionId)
    {
        this.idClient = idClient;
        this.firstname = firstname;
        this.lastname = lastname;
        this.cardId = cardId;
        this.gender = gender;
        this.address = address;
        this.birthdate = birthdate;
        this.subscriptionId = subscriptionId;
    }

    public int getIdClient()
    {
        return idClient;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Date getBirthdate()
    {
        return birthdate;
    }

    public String getCardId() { return cardId; }

    public int getSubscriptionId() { return subscriptionId; }

    public void setSubscriptionId(int id) { subscriptionId = id; }
}
