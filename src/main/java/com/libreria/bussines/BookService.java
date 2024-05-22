package com.libreria.bussines;

import com.libreria.dto.BookDto;
import com.libreria.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book saveBook(BookDto bookDto);
    List<Book> findBooks();
    Optional<Book> findBookById(int id);
    List<Book> findBooksByAuthor(String author, String sortBy);
    List<Book> findBooksByYear(String author, String sortBy);
    Optional<Book> updateBookById(int id, BookDto bookDto);
    String deleteBookById(int id);
    String deleteAllBooks();




}
