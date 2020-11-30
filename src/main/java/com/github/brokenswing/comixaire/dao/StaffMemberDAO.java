package com.github.brokenswing.comixaire.dao;

import com.github.brokenswing.comixaire.models.StaffMember;

public interface StaffMemberDAO {

    StaffMember create(StaffMember staffMember);

    StaffMember find(String idStaff);

    void update(StaffMember staffMember);

    void delete(StaffMember staffMember);

}
