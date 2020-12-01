package com.github.brokenswing.comixaire.exception;

public class NoStaffMemberFoundException extends Exception
{

    public NoStaffMemberFoundException(int idStaff)
    {
        super("The staff member with id " + idStaff + " does not exist");
    }

    public NoStaffMemberFoundException(String username)
    {
        super("The staff member with username " + username + " does not exist");
    }

}
