package com.github.brokenswing.comixaire.facades.auth;

import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.StaffMember;

/**
 * This class is used to store the current state of the
 * authentication system of the application. It stores
 * either a logged in staff member, a logged in client
 * or nothing in the case no user is logged in the system.
 */
public class Session
{

    private StaffMember loggedInStaff = null;
    private Client loggedInClient = null;

    /**
     * @return the currently logged in client or null if no client is logged in the system
     */
    public Client getLoggedInClient()
    {
        return loggedInClient;
    }

    public void setLoggedInClient(Client loggedInClient)
    {
        this.loggedInClient = loggedInClient;
    }

    /**
     * @return the currently logged in staff member or null if no staff member is logged in the system
     */
    public StaffMember getLoggedInStaff()
    {
        return loggedInStaff;
    }

    public void setLoggedInStaff(StaffMember loggedInStaff)
    {
        this.loggedInStaff = loggedInStaff;
    }

    /**
     * Logs out any connected user, independently of it being
     * a staff member or a client.<br>
     * If no user is logged in the system, this method does nothing.
     */
    public void logout()
    {
        this.loggedInClient = null;
        this.loggedInStaff = null;
    }

}
