package com.esgi.donjons.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    private static final String URL = System.getenv()
            .getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/guilde");
    private static final String USER = System.getenv()
            .getOrDefault("DB_USER", "donjons");
    private static final String PASSWORD = System.getenv()
            .getOrDefault("DB_PASSWORD", "donjons");

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
