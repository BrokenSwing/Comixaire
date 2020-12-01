package com.github.brokenswing.comixaire.dao.postgres;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPostgreSQL
{

    private static Connection connection;

    public ConnectionPostgreSQL()
    {
        Properties p = new Properties();
        try (InputStream in = ConnectionPostgreSQL.class.getClassLoader().getResourceAsStream("database.properties"))
        {
            if (in == null)
            {
                throw new NullPointerException("You must specify a database.properties file");
            }
            p.load(new InputStreamReader(in));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            String url = p.getProperty("url");
            String user = p.getProperty("user");
            String password = p.getProperty("password");
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Connection getConnection()
    {
        return connection;
    }

}