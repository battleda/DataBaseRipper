package org.sdv;

import org.sdv.dao.hikari.DatabasePool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AppHikari {

    public static void main(String[] args) {
        try {
            //Получить соединение из DatabasePool с привлечением HikariCP
            Connection connection = DatabasePool.getConnection();

            System.out.println("Подключение к Postgres установлено!");

            //Создать запрос
            Statement statement = connection.createStatement();

            //Создать таблицу, если ее не существует
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, name VARCHAR(50), email VARCHAR(50))";

            statement.execute(createTableSQL);
            System.out.println("Таблица 'users' создана!");

            //Вставить строку в таблицу
            String insertSQL = "INSERT INTO users(name email) VALUES('John Doe', 'john.doe@example.com')";

            statement.executeUpdate(insertSQL);
            System.out.println("Данные вставлены в таблицу!");

            //Выбрать данные из таблицы
            String selectSQL = "SELECT * FROM users";

            ResultSet resultSet = statement.executeQuery(selectSQL);

            while(resultSet.next()) {
                System.out.println(
                        "User ID: " + resultSet.getInt("id")
                        + ", Name: "
                        + resultSet.getString("name")
                        + ", Email: "
                        + resultSet.getString("email")
                );
            }

            //Закрыть
            DatabasePool.closePool();

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
