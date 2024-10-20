package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.dao.*;





public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoJDBCImpl(); // или new UserDaoHibernateImpl();
        UserService userService = new UserServiceImpl(userDao);
        
        userService.createUsersTable();

        userService.saveUser("Evgeny", "Korzenok", (byte) 32);
        userService.saveUser("Marina", "Krisko", (byte) 32);
        userService.saveUser("Ivan", "Medvedev", (byte) 32);
        userService.saveUser("Semen", "Belkin", (byte) 32);

        for (User u: userService.getAllUsers()) {
            System.out.println(u);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

}
