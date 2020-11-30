package com.github.brokenswing.comixaire.dao.postgreSQL;

import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.NoStaffMemberFoundException;
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
            if (e instanceof PSQLException) {
                PSQLException ex = (PSQLException) e;
                if (ex.getServerErrorMessage() != null && "unique_username".equals(ex.getServerErrorMessage().getConstraint())) {
                    throw new UsernameAlreadyExistsException(staffMember.getUsername());
                }
            }
            throw new InternalException("Unable to create new staff member", e);
        }
    }

    @Override
    public StaffMember findById(int idStaff) throws InternalException, NoStaffMemberFoundException {
        try {
            PreparedStatement prepare = this
                    .connection
                    .prepareStatement(
                            "SELECT * FROM staffMembers WHERE staffMember_id = (?)",
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    );
            prepare.setInt(1, idStaff);
            ResultSet result = prepare.executeQuery();
            if (result.first()) {
                return new StaffMember(
                        result.getInt("staffMember_id"),
                        result.getString("staffMember_username"),
                        result.getString("staffMember_password"),
                        result.getString("staffMember_role")
                );
            }
            else {
                throw new NoStaffMemberFoundException(idStaff);
            }
        } catch (SQLException e) {
            throw new InternalException("Unable to find staff member", e);
        }
    }

    @Override
    public void update(StaffMember staffMember) {

    }

    @Override
    public void delete(StaffMember staffMember) {

    }

    public static void main(String[] args) throws InternalException, UsernameAlreadyExistsException, NoStaffMemberFoundException {
        StaffMember staffMember = new StaffMember("Test", "Test123", "Employee");
        PostgresStaffMemberDAO postgresStaffMemberDAO = new PostgresStaffMemberDAO();
        staffMember = postgresStaffMemberDAO.create(staffMember);
        System.out.println(staffMember.getIdStaff());

        StaffMember staffMember1 = postgresStaffMemberDAO.findById(5);
        System.out.println(staffMember1.getUsername());

        StaffMember staffMember2 = postgresStaffMemberDAO.findById(6);
        System.out.println(staffMember2.getUsername());


    }
}
