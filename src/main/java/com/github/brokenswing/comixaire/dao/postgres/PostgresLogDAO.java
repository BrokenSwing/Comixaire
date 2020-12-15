package com.github.brokenswing.comixaire.dao.postgres;

import com.github.brokenswing.comixaire.dao.LogDAO;
import com.github.brokenswing.comixaire.exception.InternalException;
import com.github.brokenswing.comixaire.models.Log;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class PostgresLogDAO implements LogDAO
{

    private Connection connection;
    private List<Log> logs = new ArrayList<>();

    public PostgresLogDAO(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public Log create(Log log) throws InternalException
    {
        logs.add(log);
        return log;
    }

    @Override
    public Log[] getAll() throws InternalException
    {
        return logs.toArray(new Log[0]);
    }

}
