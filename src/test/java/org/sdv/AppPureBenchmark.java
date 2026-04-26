package org.sdv;

import org.openjdk.jmh.annotations.*;
import org.sdv.dao.pure.ConnectionClassSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class AppPureBenchmark {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Setup(Level.Trial)
    public void init() throws SQLException {
        conn = ConnectionClassSingleton.getInstance().getConnection();

        //Вставка тестовых данных
        PreparedStatement insert = conn.prepareStatement("INSERT INTO users VALUES(?, ?)");
        for(int i = 1; i <= 10_000; i++) {
            insert.setString(1, "Jon Doe" + i);
            insert.setString(2, "mail" + i + "@mail.com");
            insert.executeUpdate();
        }
        ps = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int testAccessByIndex() throws SQLException {
        ps .setInt(1, 1);
        rs = ps.executeQuery();
        rs.next();
        int id = rs.getInt(1); // Доступ по индексу
        rs.close();
        return id;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int testAccessByName() throws SQLException {
        ps.setInt(1, 1);
        rs = ps.executeQuery();
        rs.next();
        int id = rs.getInt("id"); // Доступ по имени
        rs.close();
        return id;
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

}
