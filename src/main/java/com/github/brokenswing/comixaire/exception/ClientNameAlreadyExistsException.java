package com.github.brokenswing.comixaire.exception;

public class ClientNameAlreadyExistsException extends Exception
{

    private String firstname;
    private String lastname;

    public UsernameAlreadyExistsException(String firstname, String lastname)
    {
        super("The name " + firstname + " " + lastname + " already exists");
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getName()
    {
        return firstname + " " + lastname;
    }
    public String getFirsname()
    {
        return firstname;
    }
    public String getLastname()
    {
        return lastname;
    }
}
