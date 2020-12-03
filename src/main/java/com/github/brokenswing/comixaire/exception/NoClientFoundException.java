package com.github.brokenswing.comixaire.exception;

public class NoClientFoundException extends Exception
{

    public NoClientFoundException(int clientId)
    {
        super("No client with the client ID " + clientId + " can be found.");
    }

    public NoClientFoundException(String cardID)
    {
        super("No client with the card ID " + cardID + " can be found.");
    }

}
