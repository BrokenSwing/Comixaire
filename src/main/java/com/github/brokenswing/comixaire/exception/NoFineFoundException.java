package com.github.brokenswing.comixaire.exception;

/**
 * This exception is thrown when trying to retrieve a fine through
 * {@link com.github.brokenswing.comixaire.dao.FineDAO}.
 */
public class NoFineFoundException extends Exception
{

    /**
     * Constructs an exception to be thrown when trying to retrieve a fine using
     * it's ID but no fine with this ID can be found.
     *
     * @param fineId the fine ID used to try to retrieve the fine
     */
    public NoFineFoundException(int fineId)
    {
        super("No fine with the ID " + fineId + " can be found.");
    }

}
