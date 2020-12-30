package com.github.brokenswing.comixaire.dao;

public abstract class DAOFactory
{

    public abstract StaffMemberDAO getStaffMemberDAO();

    public abstract ClientDAO getClientDAO();

    public abstract LogDAO getLogDAO();

    public abstract FineDAO getFineDAO();

    public abstract LibraryItemDAO getLibraryItemDAO();

    public abstract RatingDAO getRatingDAO();

}
