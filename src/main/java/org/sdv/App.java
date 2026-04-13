package org.sdv;

import java.sql.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "postgres";
        try {
            //Загрузка Postgres JDBC driver
            Class.forName("org.postgresql.Driver");

            //Установить соединение
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println( "Подключение к Postgres установлено!" );

            //Создать запрос
            Statement statement = connection.createStatement();

            //Создать таблицу, если ее не существует
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, name VARCHAR(50), email VARCHAR(50))";

            statement.execute(createTableSQL);
            System.out.println("Таблица 'user' создана!");

            //Вставить строку в таблицу
            String insertSQL = "INSERT INTO users(name, email) VALUES('John Doe', 'john.doe@example.com')";

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

            //Закрыть соединение
            connection.close();
            System.out.println("Соединение закрыто!");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
