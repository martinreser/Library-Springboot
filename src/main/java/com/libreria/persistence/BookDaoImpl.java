package com.libreria.persistence;

import com.libreria.model.Book;
import com.libreria.util.DBConnection;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookDaoImpl implements BookDao {

    @Override
    public void save(String name, String author, int year) {
        try {
            Statement statement = DBConnection.getConnection().createStatement();
            statement.executeUpdate("INSERT INTO books (`name_book`, `name_author`, `year_release`) VALUES ('" + name + "','" + author + "','" + year + "');");
        } catch (SQLException e){
            e.printStackTrace();
        };
    }

    @Override
    public Book findBookById(int id) {
        try {
            Statement statement = DBConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books WHERE id_book = " + id + ";");
            Book book = null;
            while (resultSet.next()){
                String name_book = resultSet.getString("name_book");
                String name_author = resultSet.getString("name_author");
                String year_release = resultSet.getString("year_release");
                book = new Book(name_book, name_author, year_release);
            }
            return book;
        } catch (SQLException e){
            e.printStackTrace();
        };
        return null;
    }

    @Override
    public List<Book> findBooks() {
        try {
            Statement statement = DBConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books;");
            Book book = null;
            List<Book> books = new ArrayList<>();
            while (resultSet.next()){
                String name_book = resultSet.getString("name_book");
                String name_author = resultSet.getString("name_author");
                String year_release = resultSet.getString("year_release");
                book = new Book(name_book, name_author, year_release);
                books.add(book);
            }
            return books;
        } catch (SQLException e){
            e.printStackTrace();
        };
        return null;
    }

    @Override
    public List<Book> findBooksByName(String nameBook) {
        try {
            Statement statement = DBConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books WHERE name_book LIKE '%" + nameBook + "%';");
            Book book = null;
            List<Book> books = new ArrayList<>();
            while (resultSet.next()){
                String name_book = resultSet.getString("name_book");
                String name_author = resultSet.getString("name_author");
                String year_release = resultSet.getString("year_release");
                book = new Book(name_book, name_author, year_release);
                books.add(book);
            }
            return books;
        } catch (SQLException e){
            e.printStackTrace();
        };
        return null;
    }

    @Override
    public List<Book> findBooksByYear(String yearBook) {
        try {
            Statement statement = DBConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books WHERE year_release LIKE '%" + yearBook + "%';");
            Book book = null;
            List<Book> books = new ArrayList<>();
            while (resultSet.next()){
                String name_book = resultSet.getString("name_book");
                String name_author = resultSet.getString("name_author");
                String year_release = resultSet.getString("year_release");
                book = new Book(name_book, name_author, year_release);
                books.add(book);
            }
            return books;
        } catch (SQLException e){
            e.printStackTrace();
        };
        return null;
    }

    @Override
    public List<Book> findBooksByAuthor(String authorBook) {
        try {
            Statement statement = DBConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books WHERE name_author LIKE '%" + authorBook + "%';");
            Book book = null;
            List<Book> books = new ArrayList<>();
            while (resultSet.next()){
                String name_book = resultSet.getString("name_book");
                String name_author = resultSet.getString("name_author");
                String year_release = resultSet.getString("year_release");
                book = new Book(name_book, name_author, year_release);
                books.add(book);
            }
            return books;
        } catch (SQLException e){
            e.printStackTrace();
        };
        return null;
    }
}
