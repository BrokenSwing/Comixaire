package com.github.brokenswing.comixaire.exception;

public class NoLoanFoundException extends Exception
{

    public NoLoanFoundException(int libraryItemID)
    {
        super("No loans found for library item with ID : " + libraryItemID);
    }

}
