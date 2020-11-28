package com.github.brokenswing.comixaire.dao.postgreSQL;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.dao.StaffMemberDAO;

public class PostgresDAOFactory extends DAOFactory {

    @Override
    public StaffMemberDAO getStaffMemberDAO() {
        return new PostgresStaffMemberDAO();
    }
}
