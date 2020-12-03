package com.github.brokenswing.comixaire.auth;

import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.StaffMember;

public class Session
{

    private StaffMember loggedInStaff = null;
    private Client loggedInClient = null;

    public Client getLoggedInClient()
    {
        return loggedInClient;
    }

    public void setLoggedInClient(Client loggedInClient)
    {
        this.loggedInClient = loggedInClient;
    }

    public StaffMember getLoggedInStaff()
    {
        return loggedInStaff;
    }

    public void setLoggedInStaff(StaffMember loggedInStaff)
    {
        this.loggedInStaff = loggedInStaff;
    }

    public void logout()
    {
        this.loggedInClient = null;
        this.loggedInStaff = null;
    }

}
