package com.github.brokenswing.comixaire.auth;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.exception.BadCredentialsException;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoStaffMemberFoundException;
import com.github.brokenswing.comixaire.models.StaffMember;

public class AuthFacade
{

    private final DAOFactory daoFactory;
    private final PasswordAlgorithm passwordAlgorithm;

    public AuthFacade(DAOFactory daoFactory, PasswordAlgorithm passAlgo)
    {
        this.daoFactory = daoFactory;
        this.passwordAlgorithm = passAlgo;
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
            // TODO: Store in session
        }
        else
        {
            throw new BadCredentialsException("Invalid password");
        }
    }

    public void tryLoginClient(String clientId) throws BadCredentialsException, InternalException
    {
        throw new InternalException("Unimplemented");
    }

}
