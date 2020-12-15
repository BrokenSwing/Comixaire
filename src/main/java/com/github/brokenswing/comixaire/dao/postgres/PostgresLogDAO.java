package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.LogDAO;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.exception.UsernameAlreadyExistsException;
import com.github.brokenswing.comixaire.models.Log;
import com.github.brokenswing.comixaire.models.StaffMember;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PostgresLogDAO implements LogDAO
{

    private Connection connection;

    public PostgresLogDAO(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public Log create(Log log) throws InternalException
    {
        try
        {
            PreparedStatement prepare = this
                    .connection
                    .prepareStatement(
                            "INSERT INTO logs(log_date, log_operationDetails, log_operationType,staffMember_id) "
                                    + "VALUES(?, ?, ?, ?) RETURNING log_id"
                    );
            prepare.setTimestamp(1, log.getTimestamp(), Calendar.getInstance());
            prepare.setString(2, log.getOperationDetails());
            prepare.setString(3, log.getOperationType());
            prepare.setInt(4, log.getStaffMember().getIdStaff());
            prepare.executeQuery();
            return log;
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to create log", e);
        }
    }

    @Override
    public Log[] getAll() throws InternalException
    {
        try
        {
            PreparedStatement prepare = this
                    .connection
                    .prepareStatement("SELECT " +
                            "log_operationDetails," +
                            "log_operationType," +
                            "log_date," +
                            "staffMember_id," +
                            "staffMember_username," +
                            "staffMember_password," +
                            "staffMember_role " +
                            "FROM logs " +
                            "NATURAL JOIN staffMembers");
            ResultSet result = prepare.executeQuery();
            ArrayList<Log> logs = new ArrayList<Log>();
            while(result.next()){
                logs.add(new Log(
                        result.getString("log_operationDetails"),
                        result.getString("log_operationType"),
                        result.getTimestamp("log_date"),
                        new StaffMember(
                                result.getInt("staffMember_id"),
                                result.getString("staffMember_username"),
                                result.getString("staffMember_password"),
                                result.getString("staffMember_role")
                        )
                ));
            }
            return logs.toArray(new Log[0]);
        }
        catch (SQLException e)
        {
            throw new InternalException("Unable to get logs", e);
        }
    }

}
