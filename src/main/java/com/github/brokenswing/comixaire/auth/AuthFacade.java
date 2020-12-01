package com.github.brokenswing.comixaire.auth;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.exception.BadCredentialsException;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.exception.NoStaffMemberFoundException;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.StaffMember;

public class AuthFacade
{

    private final DAOFactory daoFactory;
    private final PasswordAlgorithm passwordAlgorithm;
    private final Session session;

    public AuthFacade(DAOFactory daoFactory, PasswordAlgorithm passAlgo, Session session)
    {
        this.daoFactory = daoFactory;
        this.passwordAlgorithm = passAlgo;
        this.session = session;
    }

    public void tryLoginStaff(String username, String password) throws BadCredentialsException, InternalException
    {
        StaffMember member;
        try
        {
            member = this.daoFactory.getStaffMemberDAO().findByUsername(username);
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

    public void tryLoginClient(String clientId) throws BadCredentialsException, InternalException
    {
        Client client;
        try
        {
            client = this.daoFactory.getClientDAO().getByClientId(clientId);
        }
        catch (NoClientFoundException e)
        {
            throw new BadCredentialsException("Invalid client ID");
        }

        session.setLoggedInClient(client);

    }

    public void logout()
    {
        this.session.logout();
    }

}
