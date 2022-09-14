package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

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
    private static String DB_URL_HIBERNATE = null;
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
        DB_URL_HIBERNATE = props.getProperty("url_hibernate");
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

    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;

        try {
            Configuration configuration = new Configuration();

            // Hibernate settings equivalent to hibernate.cfg.xml's properties
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, DB_URL_HIBERNATE);
            settings.put(Environment.USER, DB_USER);
            settings.put(Environment.PASS, DB_PASSWORD);
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

            settings.put(Environment.SHOW_SQL, "true");

            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            settings.put(Environment.HBM2DDL_AUTO, "update");

            configuration.setProperties(settings);

            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            System.out.println("Connected");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sessionFactory;
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
