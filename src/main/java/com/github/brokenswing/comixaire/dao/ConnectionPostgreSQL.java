package com.github.brokenswing.comixaire.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPostgreSQL {

    private static Connection connection;

    private ConnectionPostgreSQL() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                String url = "";
                String user = "";
                String password = "";
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
