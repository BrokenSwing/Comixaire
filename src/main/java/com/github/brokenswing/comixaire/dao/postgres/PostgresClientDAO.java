package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.ClientDAO;
import com.github.brokenswing.comixaire.dao.StaffMemberDAO;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoClientFoundException;
import com.github.brokenswing.comixaire.exception.NoStaffMemberFoundException;
import com.github.brokenswing.comixaire.exception.UsernameAlreadyExistsException;
import com.github.brokenswing.comixaire.models.Client;
import com.github.brokenswing.comixaire.models.StaffMember;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresClientDAO implements ClientDAO
{

    private final Connection connection;

    PostgresClientDAO(Connection connection)
    {
        this.connection = connection;
    }


    @Override
    public Client create(Client client) throws InternalException {
        return null;
    }

    @Override
    public Client findById(int idClient) throws InternalException, NoClientFoundException {
        return null;
    }

    @Override
    public Client[] findByName(String firstname, String lastname) throws InternalException, NoClientFoundException {
        return new Client[0];
    }

    @Override
    public Client[] findByFirstname(String firstname) throws InternalException, NoClientFoundException {
        return new Client[0];
    }

    @Override
    public Client[] findByLastname(String lastname) throws InternalException, NoClientFoundException {
        return new Client[0];
    }

    @Override
    public void update(Client client) throws InternalException {

    }

    @Override
    public void delete(Client client) throws InternalException {

    }
}
