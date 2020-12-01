package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.DAOFactory;
import com.github.brokenswing.comixaire.dao.StaffMemberDAO;

public class PostgresDAOFactory extends DAOFactory
{

    private final ConnectionPostgreSQL postgresConnection;

    public PostgresDAOFactory()
    {
        this.postgresConnection = new ConnectionPostgreSQL();
    }

    @Override
    public StaffMemberDAO getStaffMemberDAO()
    {
        return new PostgresStaffMemberDAO(this.postgresConnection.getConnection());
    }

}
