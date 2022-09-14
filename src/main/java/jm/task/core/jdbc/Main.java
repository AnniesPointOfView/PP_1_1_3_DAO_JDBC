package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {

        // testDAOJDBC();
        testDAOHibernate();

    }

    public static void testDAOHibernate() {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Mike", "Wheeler", (byte) 20);
        userService.saveUser("Dustin", "Henderson", (byte) 21);
        userService.saveUser("Lucas", "Sinclair", (byte) 19);
        userService.saveUser("Will", "Byers", (byte) 18);

        userService.removeUserById(2);

        // print
        List<User> users = userService.getAllUsers();
        users.stream().forEach(System.out :: println);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

    public static void testDAOJDBC() {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Mike", "Wheeler", (byte) 20);
        userService.saveUser("Dustin", "Henderson", (byte) 21);
        userService.saveUser("Lucas", "Sinclair", (byte) 19);
        userService.saveUser("Will", "Byers", (byte) 18);

        userService.removeUserById(2);

        // print
        List<User> users = userService.getAllUsers();
        users.stream().forEach(System.out :: println);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
