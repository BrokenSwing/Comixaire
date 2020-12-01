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

public class PostgresStaffMemberDAO implements StaffMemberDAO
{

    private final Connection connection;

    PostgresStaffMemberDAO(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public StaffMember create(StaffMember staffMember) throws InternalException, UsernameAlreadyExistsException
    {
        try
        {
            PreparedStatement prepare = this
                    .connection
                    .prepareStatement(
                            "INSERT INTO staffMembers(staffMember_username, staffMember_password, staffMember_role) "
                                    + "VALUES(?, ?, ?) RETURNING staffMember_id"
                    );
            prepare.setString(1, staffMember.getUsername());
            prepare.setString(2, staffMember.getPassword());
            prepare.setString(3, staffMember.getRole());
            ResultSet result = prepare.executeQuery();
            result.next();
            int idstaff = result.getInt("staffMember_id");
            return new StaffMember(idstaff, staffMember.getUsername(), staffMember.getPassword(), staffMember.getRole());
        }
        catch (SQLException e)
        {
            if (e instanceof PSQLException)
            {
                PSQLException ex = (PSQLException) e;
                if (ex.getServerErrorMessage() != null && "unique_username".equals(ex.getServerErrorMessage().getConstraint()))
                {
                    throw new UsernameAlreadyExistsException(staffMember.getUsername());
                }
            }
            throw new InternalException("Unable to create new staff member", e);
        }
    }

    @Override
    public StaffMember findById(int idStaff) throws InternalException, NoStaffMemberFoundException
    {
        try
        {
            PreparedStatement prepare = this
                    .connection
                    .prepareStatement(
                            "SELECT * FROM staffMembers WHERE staffMember_id = (?)",
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    );
            prepare.setInt(1, idStaff);
            ResultSet result = prepare.executeQuery();
            if (result.first())
            {
                return new StaffMember(
                        result.getInt("staffMember_id"),
                        result.getString("staffMember_username"),
                        result.getString("staffMember_password"),
                        result.getString("staffMember_role")
                );
            }
            else
            {
                throw new NoStaffMemberFoundException(idStaff);
            }
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to find staff member", e);
        }
    }

    @Override
    public StaffMember findByUsername(String username) throws InternalException, NoStaffMemberFoundException
    {
        try
        {
            PreparedStatement prepare = this
                    .connection
                    .prepareStatement(
                            "SELECT * FROM staffMembers WHERE staffMember_username = (?)",
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    );
            prepare.setString(1, username);
            ResultSet result = prepare.executeQuery();
            if (result.first())
            {
                return new StaffMember(
                        result.getInt("staffMember_id"),
                        result.getString("staffMember_username"),
                        result.getString("staffMember_password"),
                        result.getString("staffMember_role")
                );
            }
            else
            {
                throw new NoStaffMemberFoundException(username);
            }
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to find staff member", e);
        }
    }

    @Override
    public void update(StaffMember staffMember) throws InternalException
    {
        try
        {
            PreparedStatement prepare = this
                    .connection
                    .prepareStatement(
                            "UPDATE staffMembers SET "
                                    + "(staffMember_username, staffMember_password) = (?, ?) "
                                    + "WHERE staffMember_id = (?)"
                    );
            prepare.setString(1, staffMember.getUsername());
            prepare.setString(2, staffMember.getPassword());
            prepare.setInt(3, staffMember.getIdStaff());
            prepare.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to update staff member", e);
        }
    }

    @Override
    public void delete(StaffMember staffMember) throws InternalException
    {
        try
        {
            PreparedStatement prepare = this
                    .connection
                    .prepareStatement(
                            "DELETE FROM staffMembers "
                                    + "WHERE staffMember_id = (?)"
                    );
            prepare.setInt(1, staffMember.getIdStaff());
            prepare.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to delete staff member", e);
        }
    }
}
