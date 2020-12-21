package com.github.brokenswing.comixaire.exception;

/**
 * This exception is thrown when a staff member tries to create a client but provides
 * an existing card id.
 *
 * @see com.github.brokenswing.comixaire.facades.clients.ClientsFacade
 */
public class CardIdAlreadyExist extends Exception
{
    /**
     * Constructs a BadCredentialsException with a descriptive message of the reason which led this exception
     * to be thrown.
     *
     * @param message a message describing what went wrong when checking credentials
     */
    public CardIdAlreadyExist(String message)
    {
        super(message);
    }

}
