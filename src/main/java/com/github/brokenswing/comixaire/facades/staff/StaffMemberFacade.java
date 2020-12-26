package com.github.brokenswing.comixaire.facades.staff;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.UsernameAlreadyExistsException;
import com.github.brokenswing.comixaire.facades.Facade;
import com.github.brokenswing.comixaire.models.StaffMember;

public class StaffMemberFacade extends Facade
{

    public StaffMemberFacade(DAOFactory factory)
    {
        super(factory);
    }

    public void update(StaffMember member) throws InternalException, UsernameAlreadyExistsException
    {
        this.factory.getStaffMemberDAO().update(member);
    }

}
