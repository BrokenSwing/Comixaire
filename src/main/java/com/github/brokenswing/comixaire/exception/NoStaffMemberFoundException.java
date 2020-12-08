package com.github.brokenswing.comixaire.exception;

/**
 * This exception is thrown when trying to retrieve a staff member through
 * {@link com.github.brokenswing.comixaire.dao.StaffMemberDAO}.
 */
public class NoStaffMemberFoundException extends Exception
{

    /**
     * Constructs an exception to be thrown when trying to retrieve a staff member
     * using it's ID but that no staff member with the given ID can be found.
     *
     * @param idStaff the ID used to try to retrieve the staff member
     */
    public NoStaffMemberFoundException(int idStaff)
    {
        super("The staff member with id " + idStaff + " does not exist");
    }

    /**
     * Constructs an exception to be thrown when trying to retrieve a staff member
     * using it's username but that no staff member with the given username can be
     * found.
     *
     * @param username the username used to try to retrieve the staff member
     */
    public NoStaffMemberFoundException(String username)
    {
        super("The staff member with username " + username + " does not exist");
    }

}
