package com.github.brokenswing.comixaire.exception;

public class NoStaffMemberFoundException extends Exception {

    private int idStaff;

    public NoStaffMemberFoundException(int idStaff)
    {
        super("The staff member with id " + idStaff + " does not exist");
        this.idStaff = idStaff;
    }

    public int getIdStaff() {
        return idStaff;
    }
}
