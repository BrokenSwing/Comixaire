package com.github.brokenswing.comixaire.exception;

public class NonValidFineTypeException extends Exception
{
    public NonValidFineTypeException(int idFineType)
    {
        super("Unable to find the fine type with id: " + idFineType);
    }
}
