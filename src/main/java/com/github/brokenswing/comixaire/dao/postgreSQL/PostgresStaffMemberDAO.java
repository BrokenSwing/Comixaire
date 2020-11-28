package com.github.brokenswing.comixaire.dao.postgreSQL;

import com.github.brokenswing.comixaire.StaffMember;
import com.github.brokenswing.comixaire.dao.StaffMemberDAO;

import java.sql.Connection;

public class PostgresStaffMemberDAO implements StaffMemberDAO {

    private Connection connection = ConnectionPostgreSQL.getConnection();

    @Override
    public StaffMember create(StaffMember staffMember) {
        return null;
    }

    @Override
    public StaffMember find(String idStaff) {
        return null;
    }

    @Override
    public void update(StaffMember staffMember) {

    }

    @Override
    public void delete(StaffMember staffMember) {

    }
}
