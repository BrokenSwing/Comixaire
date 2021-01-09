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
    public StaffMemberDAO getStaffMemberDAO()
    {
        return new PostgresStaffMemberDAO(this.postgresConnection.getConnection());
    }

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
    public FineDAO getFineDAO()
    {
        return new PostgresFineDAO(this.postgresConnection.getConnection());
    }

    @Override
    public LibraryItemDAO getLibraryItemDAO()
    {
        return new PostgresLibraryItemDAO(this.postgresConnection.getConnection());
    }

    @Override
    public RatingDAO getRatingDAO()
    {
        return new PostgresRatingDAO(this.postgresConnection.getConnection());
    }

    @Override
    public SubscriptionsDAO getSubscriptionsDAO()
    {
        return new PostgresSubscriptionsDAO(this.postgresConnection.getConnection());
    }

    @Override
    public LoanDAO getLoanDAO()
    {
        return new PostgresLoanDAO(postgresConnection.getConnection());
    }

    @Override
    public ReturnsDAO getReturnsDAO()
    {
        return new PostgresReturnsDAO(postgresConnection.getConnection());
    }

    @Override
    public FineTypeDAO getFineTypeDAO()
    {
        return new PostgresFineTypeDAO(postgresConnection.getConnection());
    }

}
