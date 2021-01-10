package com.github.brokenswing.comixaire.models;

import java.util.Date;
import java.util.Objects;

public class Client
{

    private final int idClient;
    private Date birthdate;
    private String cardId;
    private String firstname;
    private String lastname;
    private String gender;
    private String address;

    public Client(String firstname, String lastname, String cardId, String gender, String address, Date birthdate)
    {
        this(-1, firstname, lastname, cardId, gender, address, birthdate);
    }

    public Client(int idClient, String firstname, String lastname, String cardId, String gender, String address, Date birthdate)
    {
        this.idClient = idClient;
        this.firstname = firstname;
        this.lastname = lastname;
        this.cardId = cardId;
        this.gender = gender;
        this.address = address;
        this.birthdate = birthdate;
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

    public String getFullname()
    {
        return firstname + " " + lastname;
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

    public void setBirthdate(Date birthdate)
    {
        this.birthdate = birthdate;
    }

    public String getCardId()
    {
        return cardId;
    }

    public void setCardId(String cardId)
    {
        this.cardId = cardId;
    }

    public String toString()
    {
        return getFirstname() + " " + getLastname() + ", born the " + getBirthdate() + " is " + getGender() + " lives " + getAddress() + " and has cardId: " + getCardId();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return idClient == client.idClient;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(idClient);
    }
}
