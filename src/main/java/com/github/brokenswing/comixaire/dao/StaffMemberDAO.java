package com.github.brokenswing.comixaire.dao;

import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.UsernameAlreadyExistsException;
import com.github.brokenswing.comixaire.models.StaffMember;

public interface StaffMemberDAO {

    StaffMember create(StaffMember staffMember) throws InternalException, UsernameAlreadyExistsException;

    StaffMember find(int idStaff);

    void update(StaffMember staffMember);

    void delete(StaffMember staffMember);

}
