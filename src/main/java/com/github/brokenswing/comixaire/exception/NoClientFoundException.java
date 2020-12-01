package com.github.brokenswing.comixaire.exception;

public class NoClientFoundException extends Exception
{

    public NoClientFoundException(String clientId)
    {
        super("No client with the client ID " + clientId + " can be found.");
    }

}
