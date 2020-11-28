package com.github.brokenswing.comixaire.dao;

import com.github.brokenswing.comixaire.dao.postgreSQL.PostgresDAOFactory;

public abstract class DAOFactory {

    public abstract StaffMemberDAO getStaffMemberDAO();

    public static DAOFactory getFactory(DAOFactoryType type)
    {
        if(type.equals(DAOFactoryType.POSTGRESQL_DAO_FACTORY)) {
            return new PostgresDAOFactory();
        }

        return null;
    }
}
