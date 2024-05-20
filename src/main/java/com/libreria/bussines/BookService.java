package com.libreria.bussines;

import com.libreria.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookService {
    Book findBookById(int id);
    List<Book> findBooks();
    List<Book> findBooksByName(String nameBook);
    List<Book> findBooksByYear(String yearBook);
    List<Book> findBooksByAuthor(String authorBook);


}
