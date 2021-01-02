package com.github.brokenswing.comixaire.exception;

/**
 * This exception is thrown when a loan cannot be found.
 */
public class NoLoanFoundException extends Exception
{

    /**
     * This constructor is used to specify that no loan can be found for
     * a given library item.
     *
     * @param libraryItemID the ID of the library item
     */
    public NoLoanFoundException(int libraryItemID)
    {
        super("No loans found for library item with ID : " + libraryItemID);
    }

}
