package com.github.brokenswing.comixaire.dao;

import com.github.brokenswing.comixaire.StaffMember;
import com.github.brokenswing.comixaire.dao.postgreSQL.ConnectionPostgreSQL;

import java.sql.Connection;

public interface StaffMemberDAO {

    StaffMember create(StaffMember staffMember);

    StaffMember find(String idStaff);

    void update(StaffMember staffMember);

    void delete(StaffMember staffMember);

}
