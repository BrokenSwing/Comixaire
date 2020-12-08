package com.github.brokenswing.comixaire.exception;

/**
 * This exception is thrown when a staff member or a client tries to authenticate but provides
 * bad credentials.
 *
 * @see com.github.brokenswing.comixaire.auth.AuthFacade
 */
public class BadCredentialsException extends Exception
{

    /**
     * Constructs a BadCredentialsException with a descriptive message of the reason which led this exception
     * to be thrown.
     *
     * @param message a message describing what went wrong when checking credentials
     */
    public BadCredentialsException(String message)
    {
        super(message);
    }

}
