package com.github.brokenswing.comixaire.exception;

import com.github.brokenswing.comixaire.models.StaffMember;

/**
 * This exception is thrown when trying to create a new staff member
 * but that the provided staff member has a username that is already
 * used by an existing staff member.
 *
 * @see com.github.brokenswing.comixaire.dao.StaffMemberDAO#create(StaffMember) 
 */
public class UsernameAlreadyExistsException extends Exception
{

    private final String username;

    /**
     * Constructs an exception to be thrown when trying to create a new
     * staff member using the same username as an already existing staff
     * member.
     *
     * @param username the conflicting username
     */
    public UsernameAlreadyExistsException(String username)
    {
        super("The username " + username + " already exists");
        this.username = username;
    }

    /**
     * @return the conflicting username
     */
    public String getUsername()
    {
        return username;
    }

}
