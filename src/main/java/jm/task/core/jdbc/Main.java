package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Mike", "Wheeler", (byte) 20);
        userService.saveUser("Dustin", "Henderson", (byte) 21);
        userService.saveUser("Lucas", "Sinclair", (byte) 19);
        userService.saveUser("Will", "Byers", (byte) 18);

        userService.removeUserById(2);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
