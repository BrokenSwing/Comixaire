package com.github.brokenswing.comixaire.dao.postgreSQL;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class ConnectionPostgreSQL {

    private static Connection connection;

    private ConnectionPostgreSQL() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            Properties p = new Properties();
            try
            {
                p.load(new InputStreamReader(
                        Objects.requireNonNull(
                                ConnectionPostgreSQL.class.getClassLoader()
                                        .getResourceAsStream("database.properties"))
                ));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            try {
                String url = p.getProperty("url");
                String user = p.getProperty("user");
                String password = p.getProperty("password");
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
