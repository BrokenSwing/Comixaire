package com.github.brokenswing.comixaire.exception;

public class InvalidFineTypeException extends Exception
{

    public InvalidFineTypeException(int idFineType)
    {
        super("Unable to find the fine type with id: " + idFineType);
    }

}
