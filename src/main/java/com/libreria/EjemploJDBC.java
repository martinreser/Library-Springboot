package com.libreria;

import java.sql.*;

public class EjemploJDBC {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/Libreria?serverTimezone=UTC";
        String username = "root";
        String password = "45319502";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            String nameBook = "Rubius OMG";
            String nameAuthor = "J.K. Rowling";

            statement.executeUpdate("INSERT INTO books (`name_book`, `name_author`) VALUES ('" + nameBook + "','" + nameAuthor + "');");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books");

            while (resultSet.next()){
                System.out.println(resultSet.getString("id_book") + "  " + resultSet.getString("name_book"));
            }
            connection.close();
            statement.close();
            resultSet.close();

        } catch (SQLException e){
            e.printStackTrace();
        };

    }
}
