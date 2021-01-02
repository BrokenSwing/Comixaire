package com.github.brokenswing.comixaire.exception;

public class NoReturnFoundException extends Exception
{
    public NoReturnFoundException(int idReturn)
    {
        super("Unable to find the return with id: " + idReturn);
    }
}
