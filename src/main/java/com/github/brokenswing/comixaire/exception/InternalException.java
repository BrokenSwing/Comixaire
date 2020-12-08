package com.github.brokenswing.comixaire.exception;

/**
 * The exception {@code InternalException} and its subclasses are thrown when an error occurs and that the user
 * has no way to fix this error using the UI.
 */
public class InternalException extends Exception
{

    /**
     * Constructs a new exception with the specified detail message. The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public InternalException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the source exception that led this exception to be thrown
     */
    public InternalException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
