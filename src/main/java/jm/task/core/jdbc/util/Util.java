package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static String DB_URL = null;
    private static String DB_USER = null;
    private static String DB_PASSWORD = null;

    static {
        Properties props = new Properties();
        try (InputStream inputStream = Files.newInputStream(Paths.get("database.properties"))) {
            props.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DB_URL = props.getProperty("url");
        DB_USER = props.getProperty("username");
        DB_PASSWORD = props.getProperty("password");
    }

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connection to DB succesfull");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection failed..." + e.getMessage());
        }

        return connection;
    }

    public static void rollbackCommit(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
