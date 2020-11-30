package com.github.brokenswing.comixaire.exception;

public class UsernameAlreadyExistsException extends Exception {

    private String username;

    public UsernameAlreadyExistsException(String username)
    {
        super("The username " + username + " already exists");
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }
}
