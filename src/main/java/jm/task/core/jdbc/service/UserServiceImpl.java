package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class UserServiceImpl implements UserService {

    public UserServiceImpl() {

    }

    public void createUsersTable() {

       try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            if (connection != null) {
                if (statement.executeUpdate("SHOW TABLES LIKE 'Users'") == 0) {
                    statement.executeUpdate("CREATE TABLE Users (id int AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL, lastName VARCHAR(255) NOT NULL, age tinyint NOT NULL)");
                } else {
                    //System.out.println("Таблица уже существует");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            if (connection != null) {
                if (statement.executeUpdate("SHOW TABLES LIKE 'Users'") == 0) {
                    //System.out.println("Таблицы не существует");
                } else {
                    statement.executeUpdate("DROP TABLE Users");
                    System.out.println("Таблица удалена");

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String strReq = "INSERT INTO Users SET name=?, lastName=?, age=?";

        try (Connection connection = Util.getConnection(); 
            Statement statement = connection.createStatement(); 
            PreparedStatement preparedStatement = connection.prepareStatement(strReq)) {
            if (connection != null) {
                if (statement.executeUpdate("SHOW TABLES FROM `korzened_kata` LIKE 'Users'") == 0) {
                    //System.out.println("Таблицы не существует");
                } else {
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, lastName);
                    preparedStatement.setByte(3, age);
                    
                    preparedStatement.executeUpdate();

                    System.out.println(name+" добавлен в базу данных");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        String strReq = "DELETE FROM Users WHERE id = ?";
        
        try (Connection connection = Util.getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(strReq);
            ResultSet resultSet = preparedStatement.executeQuery("SELECT id FROM Users WHERE id = " + id)) {
        
            preparedStatement.setLong(1, id);
            
            if (!resultSet.next()) {
                System.out.println("Пользователя не существует");
                return;
            }
            
            if (preparedStatement.executeUpdate() > 0) {
                System.out.println("Пользователь удален");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {

        List<User> Users = new ArrayList<>();

        try (Connection connection = Util.getConnection(); 
            Statement statement = connection.createStatement()){
            if (statement.executeUpdate("SHOW TABLES LIKE 'Users'") == 0) {
                System.out.println("Таблицы не существует");
                
            } else {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");
                if (connection != null) {
                    while(resultSet.next()){
                        User user = new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4));
                        user.setId(resultSet.getLong(1));
                        Users.add(user);
                    }
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Users;
    }

    public void cleanUsersTable() { 
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            if (connection != null) {
                if (statement.executeUpdate("SHOW TABLES LIKE 'Users'") == 0) {
                    System.out.println("Таблицы не существует");
                    
                } else {
                    statement.executeUpdate("DELETE FROM Users");
                    System.out.println("Таблица очищена");

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
}

}
