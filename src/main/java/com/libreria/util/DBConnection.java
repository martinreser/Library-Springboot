package com.libreria.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static String url = "jdbc:mysql://localhost:3306/Libreria?serverTimezone=UTC";
    private static String username = "root";
    private static String password = "45319502";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    };

}
