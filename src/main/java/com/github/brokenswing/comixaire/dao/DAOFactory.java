package com.github.brokenswing.comixaire.dao;

public abstract class DAOFactory
{

    public abstract StaffMemberDAO getStaffMemberDAO();

    public abstract ClientDAO getClientDAO();

    public abstract LogDAO getLogDAO();

    public abstract FineDAO getFineDAO();

    public abstract LibraryItemDAO getLibraryItemDAO();

    public abstract RatingDAO getRatingDAO();

    public abstract SubscriptionsDAO getSubscriptionsDAO();

    public abstract LoanDAO getLoanDAO();

    public abstract ReturnsDAO getReturnsDAO();

    public abstract FineTypeDAO getFineTypeDAO();
}
