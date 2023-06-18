package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String LOCALHOST_DB_URL = "jdbc:mysql://localhost:3306/kata";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static Connection connection;

    public static Connection connectToMySqlDatabaseJDBC() {
        try {
            Class.forName(MYSQL_DRIVER);
            connection = DriverManager.getConnection(LOCALHOST_DB_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

