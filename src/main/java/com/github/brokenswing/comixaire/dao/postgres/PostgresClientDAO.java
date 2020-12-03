package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.StaffMemberDAO;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoStaffMemberFoundException;
import com.github.brokenswing.comixaire.exception.UsernameAlreadyExistsException;
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
    public Client create(Client client) throws InternalException
    {

    }

    @Override
    public Client findById(int idClient) throws InternalException, NoClientFoundException
    {

    }

    @Override
    public Client[] findByName(String username) throws InternalException, NoClientFoundException
    {

    }

    @Override
    public Client[] findByFirstname(String firstname) throws InternalException, NoClientFoundException
    {

    }

    @Override
    public Client[] findByLastname(String lastname) throws InternalException, NoClientFoundException
    {

    }

    @Override
    public void update(StaffMember staffMember) throws InternalException
    {

    }

    @Override
    public void delete(StaffMember staffMember) throws InternalException
    {

    }
}
