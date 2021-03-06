package com.github.brokenswing.comixaire.models;

public class StaffMember
{

    private final int idStaff;
    private final String role;
    private String username;
    private String password;

    public StaffMember(String username, String password, String role)
    {
        this(-1, username, password, role);
    }

    public StaffMember(int idStaff, String username, String password, String role)
    {
        this.idStaff = idStaff;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public StaffMember(StaffMember staff)
    {
        this(staff.idStaff, staff.username, staff.password, staff.role);
    }

    public int getIdStaff()
    {
        return idStaff;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getRole()
    {
        return role;
    }
}
