package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;




public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        
        userService.createUsersTable();
        userService.saveUser("Evgeny", "Korzenok", (byte) 32);
        userService.saveUser("Marina", "Krisko", (byte) 32);
        userService.saveUser("Ivan", "Medvedev", (byte) 32);
        userService.saveUser("Semen", "Belkin", (byte) 32);
        for(User user: userService.getAllUsers()) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

}
