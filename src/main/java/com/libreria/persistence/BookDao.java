package com.libreria.persistence;

import com.libreria.model.Book;

import java.util.List;

public interface BookDao {
    void save(String name, String author, int year);
    Book findBookById(int id);
    List<Book> findBooks();
    List<Book> findBooksByName(String nameBook);
    List<Book> findBooksByYear(String yearBook);
    List<Book> findBooksByAuthor(String authorBook);


}
