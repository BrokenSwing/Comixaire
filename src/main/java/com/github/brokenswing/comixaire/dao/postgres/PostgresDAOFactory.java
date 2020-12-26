package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.*;

public class PostgresDAOFactory extends DAOFactory
{

    private final ConnectionPostgreSQL postgresConnection;

    public PostgresDAOFactory()
    {
        this.postgresConnection = new ConnectionPostgreSQL();
    }

    @Override
    public StaffMemberDAO getStaffMemberDAO() { return new PostgresStaffMemberDAO(this.postgresConnection.getConnection()); }

    @Override
    public ClientDAO getClientDAO()
    {
        return new PostgresClientDAO(this.postgresConnection.getConnection());
    }

    @Override
    public LogDAO getLogDAO()
    {
        return new PostgresLogDAO(this.postgresConnection.getConnection());
    }

    @Override
    public FineDAO getFineDAO() {
        //TODO return Postgres FineDAO
        return null;
    }

    @Override
    public LibraryItemDAO getLibraryItemDAO() {
        return new PostgresLibraryItemDAO(this.postgresConnection.getConnection());
    }

}
