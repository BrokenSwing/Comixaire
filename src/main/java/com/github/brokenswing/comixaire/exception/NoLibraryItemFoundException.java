package com.github.brokenswing.comixaire.exception;

/**
 * This exception is thrown when trying to retrieve a library item through
 * {@link com.github.brokenswing.comixaire.dao.LibraryItemDAO}.
 */
public class NoLibraryItemFoundException extends Exception
{
    /**
     * Constructs an exception to be thrown when trying to retrieve a staff member
     * using it's ID but that no staff member with the given ID can be found.
     *
     * @param idLibraryItem the ID used to try to retrieve the library item
     */
    public NoLibraryItemFoundException(int idLibraryItem)
    {
        super("The library item with id " + idLibraryItem + " does not exist");
    }

    /**
     * Constructs an exception to be thrown when trying to retrieve a library item
     * using it's title but that no library item with the given title can be
     * found.
     *
     * @param title the title used to try to retrieve the library item
     */
    public NoLibraryItemFoundException(String title)
    {
        super("The library item with title " + title + " does not exist");
    }
}
