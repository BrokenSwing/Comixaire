package com.github.brokenswing.comixaire.facades.auth;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.exception.BadCredentialsException;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.exception.NoStaffMemberFoundException;
import com.github.brokenswing.comixaire.facades.Facade;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.StaffMember;

/**
 * This class provides a simple API to use the authentication system
 * of the application.
 */
public class AuthFacade extends Facade
{

    private final PasswordAlgorithm passwordAlgorithm;
    private final Session session;

    /**
     * @param daoFactory The factory to use to access users data
     * @param passAlgo   The password hashing algorithm to use to check users passwords
     * @param session    The session to change the state of when a method of the facade succeeds
     */
    public AuthFacade(DAOFactory daoFactory, PasswordAlgorithm passAlgo, Session session)
    {
        super(daoFactory);
        this.passwordAlgorithm = passAlgo;
        this.session = session;
    }

    /**
     * Tries to login a staff member which has the given username and the given password.
     * Given username and password are not trimmed before checking against persisted data.
     * If this method does no throw any exception, it means the staff member is now logged
     * in the system.<br>
     * This method can be called when an user is already logged in the system : the success
     * of this method call will just override logged in user.
     *
     * @param username the username of the staff member to log in
     * @param password the plain password of the staff member to log in
     * @throws BadCredentialsException when no staff member with the given username can be found
     *                                 or that the given password is invalid for the staff member
     *                                 with the given username
     * @throws InternalException       when an expected error occurred
     */
    public void loginStaff(String username, String password) throws BadCredentialsException, InternalException
    {
        StaffMember member;
        try
        {
            member = this.factory.getStaffMemberDAO().findByUsername(username);
        }
        catch (NoStaffMemberFoundException e)
        {
            throw new BadCredentialsException("Invalid username");
        }

        if (this.passwordAlgorithm.verifyPassword(password, member.getPassword()))
        {
            session.setLoggedInStaff(member);
        }
        else
        {
            throw new BadCredentialsException("Invalid password");
        }
    }

    /**
     * Tries to connect a client with the given card ID. Given card ID is not trimmed when checked
     * against system data to retrieve concerned client. If this method does no throw any exception
     * it means that the client with the given card ID is now logged in the system.<br>
     * This method can be called when an other user is already called in the system, in the case,
     * the success of this method call just overrides the user that is logged in the system.
     *
     * @param cardId the card ID of the client to login to the system
     * @throws BadCredentialsException if no client with the given card ID can be found
     * @throws InternalException       when an expected error occurred
     */
    public void loginClient(String cardId) throws BadCredentialsException, InternalException
    {
        Client client;
        try
        {
            client = this.factory.getClientDAO().findByCardID(cardId);
        }
        catch (NoClientFoundException e)
        {
            throw new BadCredentialsException("Invalid client ID");
        }

        session.setLoggedInClient(client);

    }

    /**
     * Logs out the currently logged in user of the system.
     * If no user is logged in the system, this method just does nothing.
     */
    public void logout()
    {
        this.session.logout();
    }

    public StaffMember getLoggedInStaff()
    {
        return this.session.getLoggedInStaff();
    }

    public void setLoggedInStaff(StaffMember member)
    {
        this.session.setLoggedInStaff(member);
    }

    public Client getLoggedInClient()
    {
        return this.session.getLoggedInClient();
    }

    public String hashPassword(String plainTextPassword)
    {
        return this.passwordAlgorithm.hashPassword(plainTextPassword);
    }

}
