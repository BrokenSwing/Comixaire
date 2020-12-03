package com.github.brokenswing.comixaire.models;

import java.util.Date;

public class Client
{
    private final int idClient;
    private String firstname;
    private String lastname;
    private String gender;
    private String address;
    private final Date birthdate;

    public Client(String firstname, String lastname, String gender, String address, Date birthdate)
    {
        this(-1, firstname, lastname, gender, address, birthdate);
    }

    public Client(int idClient, String firstname, String lastname, String gender, String address, Date birthdate)
    {
        this.idClient = idClient;
        this.firstname = firstname;
        this.lastname = lastname;
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


}
