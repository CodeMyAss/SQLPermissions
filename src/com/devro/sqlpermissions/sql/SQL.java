package com.devro.sqlpermissions.sql;

import com.devro.sqlpermissions.utils.LoggingUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Copyright DevRo_ (c) 2014. All Rights Reserved.
 * Programmed by: DevRo_ (Erik Rosemberg)
 * Creation Date: 17, 07, 2014
 * Programmed for the SQLPermissions project.
 */
public class SQL {
    private Connection connection = null;

    public SQL(String host, String port, String database, String username, String password) {
        try {
            LoggingUtils.log("SQL", "Connecting to SQL Database (" + host + ":" + port + ")");

            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);

            LoggingUtils.log("SQL", "Connected to SQL Database (" + host + ":" + port + ")");
        } catch (SQLException e) {
            LoggingUtils.log("SQL", "Couldn't connect to MySQL Database, StackTrace: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            LoggingUtils.log("SQL", "Class com.mysql.jdbc.Driver was not Found: " + e.getMessage() );
        }
    }

    public void endConnection() {
        try {
            connection.close();
            LoggingUtils.log("SQL", "Disconnected from MySQL Database!");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
