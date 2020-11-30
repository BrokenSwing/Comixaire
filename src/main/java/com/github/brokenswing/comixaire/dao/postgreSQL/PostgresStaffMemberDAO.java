package com.github.brokenswing.comixaire.dao.postgreSQL;

import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.UsernameAlreadyExistsException;
import com.github.brokenswing.comixaire.models.StaffMember;
import com.github.brokenswing.comixaire.dao.StaffMemberDAO;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresStaffMemberDAO implements StaffMemberDAO {

    private Connection connection = ConnectionPostgreSQL.getConnection();

    @Override
    public StaffMember create(StaffMember staffMember) throws InternalException, UsernameAlreadyExistsException {
        try {
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
        } catch (SQLException e) {
            if(e instanceof PSQLException) {
                PSQLException ex = (PSQLException) e;
                if(ex.getServerErrorMessage() != null && "unique_username".equals(ex.getServerErrorMessage().getConstraint())) {
                    throw new UsernameAlreadyExistsException(staffMember.getUsername());
                }
            }

            throw new InternalException("Unable to create new staff member.", e);
        }
    }

    @Override
    public StaffMember find(int idStaff) {
        return null;
    }

    @Override
    public void update(StaffMember staffMember) {

    }

    @Override
    public void delete(StaffMember staffMember) {

    }

    public static void main(String[] args) throws InternalException, UsernameAlreadyExistsException {
        StaffMember staffMember = new StaffMember("Keven", "Test123", "Employee");
        PostgresStaffMemberDAO postgresStaffMemberDAO = new PostgresStaffMemberDAO();
        staffMember = postgresStaffMemberDAO.create(staffMember);
        System.out.println(staffMember.getIdStaff());
    }
}
