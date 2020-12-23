package com.github.brokenswing.comixaire.exception;

/**
 * This exception is thrown when trying to retrieve a client through
 * {@link com.github.brokenswing.comixaire.dao.ClientDAO}.
 */
public class NoClientFoundException extends Exception
{

    /**
     * Constructs an exception to be thrown when trying to retrieve a client using
     * it's client ID but that no client with the given client ID can be found.
     *
     * @param clientId the client ID used to try to retrieve the client
     */
    public NoClientFoundException(int clientId)
    {
        super("No client with the client ID " + clientId + " can be found.");
    }

    /**
     * Constructs an exception to be thrown when trying to retrieve a client using
     * it's card ID but that no client with the given card ID can be found.
     *
     * @param cardID the card ID used to try to retrieve the client
     */
    public NoClientFoundException(String cardID)
    {
        super(cardID);
    }

}
