package org.sdv.dao.pure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClassSingleton {

    private static ConnectionClassSingleton INSTANCE;
    private static String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
    private static String username = "postgres";
    private static String password = "postgres";
    private Connection connection;

    private ConnectionClassSingleton() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(jdbcURL, username, password);

        } catch(ClassNotFoundException e) {
            System.err.println("Подключи postgres библиотеку, а то как лох!");
            System.err.println(e.getMessage());
        } catch(SQLException e) {
            System.err.println("Базы с такими кредами нет, запусти доцкер контейнер с БэДэ jdbc:postgresql://localhost:5432/postgres и аналогичным паролем и пользоватетелм");
            System.err.println(e.getMessage());
        }
    }

    public static ConnectionClassSingleton getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ConnectionClassSingleton();
        }
        return INSTANCE;
    }

    public Connection getConnection() {
        return connection;
    }
}
