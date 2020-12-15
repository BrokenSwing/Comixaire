package com.github.brokenswing.comixaire.facades.staff;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.UsernameAlreadyExistsException;
import com.github.brokenswing.comixaire.models.StaffMember;

public class StaffMemberFacade
{

    private final DAOFactory factory;

    public StaffMemberFacade(DAOFactory factory)
    {
        this.factory = factory;
    }

    public void update(StaffMember member) throws InternalException, UsernameAlreadyExistsException
    {
        this.factory.getStaffMemberDAO().update(member);
    }

}
