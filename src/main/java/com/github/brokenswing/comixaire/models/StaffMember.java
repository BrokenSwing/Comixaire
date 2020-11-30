package com.github.brokenswing.comixaire.models;

public class StaffMember {

    private String idStaff;
    private String username;
    private String password;
    private String role;

    public String getIdStaff()
    {
        return idStaff;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getRole()
    {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
