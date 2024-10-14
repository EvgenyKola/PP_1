package jm.task.core.jdbc.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://korzened.beget.tech:3306/korzened_kata?useSSL=false";
    private static final String USER = "korzened_kata";
    private static final String PASSWORD = "Q1S2C3q1w2e3r4t5y6";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Загружаем драйвер
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Получаем соединение
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            //System.out.println("Подключение к базе данных успешно выполнено!");
        } catch (SQLException e) {
            System.err.println("Ошибка подключения: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Драйвер MySQL не найден: " + e.getMessage());
        }
        return conn;
    }  
}
