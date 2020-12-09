package com.github.brokenswing.comixaire.dao;

import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoStaffMemberFoundException;
import com.github.brokenswing.comixaire.exception.UsernameAlreadyExistsException;
import com.github.brokenswing.comixaire.models.StaffMember;

public interface StaffMemberDAO
{

    StaffMember create(StaffMember staffMember) throws InternalException, UsernameAlreadyExistsException;

    StaffMember findById(int idStaff) throws InternalException, NoStaffMemberFoundException;

    /**
     * @param username The username of the staff member to find
     * @return the staff member with the given username
     * @throws InternalException           if an unexpected error occurs
     * @throws NoStaffMemberFoundException if no member with the given username was found
     */
    StaffMember findByUsername(String username) throws InternalException, NoStaffMemberFoundException;

    void update(StaffMember staffMember) throws InternalException;

    void delete(StaffMember staffMember) throws InternalException;

}
