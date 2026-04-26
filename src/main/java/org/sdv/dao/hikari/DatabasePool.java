package org.sdv.dao.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabasePool {
    private static HikariDataSource dataSource;

    static {
        try {
            System.out.println("Начинаю hikari прорев!");
            //Загрузка конфигурации
            HikariConfig config = new HikariConfig();

            //Основные настройки подключения
            config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
            config.setUsername("postgres");
            config.setPassword("postgres");

            System.out.println("Установлены на стройки подключения!");

            //Настройка пула
            config.setMaximumPoolSize(10);             // Максимальное количество соединений
            config.setMinimumIdle(5);                  // Максимальное количество idle соединений
            config.setConnectionTimeout(30_000);       // Таймаут подключений (30 секунд)
            config.setIdleTimeout(600_000);            // Таймаут idle соединения (10 минут)
            config.setMaxLifetime(1_800_000);          // Максимальное время жизни соединения (30 минут)
            config.setConnectionTestQuery("select 1"); // Проверка соединения

            System.out.println("Установлены настройки пула подключений!");

            //Дополнительные настройки postgres
            config.addDataSourceProperty("cachePrepStmts", "true");   // Управление кэшированием подготовленных запросов
            config.addDataSourceProperty("prepStmtCacheSize", "250"); // Количество подготовленных запросов для кэширования
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048"); // Максимальное количество подготовленных запросов

            System.out.println("Установлены дополнительные настройки для postgres!");

        } catch (Exception e) {
            System.err.println("Конфигурация hikari сделала харакири, а тебе не следует!");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closePool() {
        if(dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

}
