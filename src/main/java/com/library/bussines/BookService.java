package com.library.bussines;

import com.library.dto.BookDto;
import com.library.model.Book;
import com.library.persistence.exception.BookAlreadyExistsException;
import com.library.persistence.exception.NoValidParamsException;
import com.library.persistence.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book saveBook(BookDto bookDto) throws ResourceNotFoundException, NoValidParamsException, BookAlreadyExistsException;
    List<Book> findBooks();
    Optional<Book> findBookById(int id) throws ResourceNotFoundException;
    List<Book> findBooksByAuthor(int author, String sortBy);
    List<Book> findBooksByYear(String author, String sortBy);
    Optional<Book> updateBookById(int id, BookDto bookDto) throws ResourceNotFoundException, NoValidParamsException;
    void deleteBookById(int id) throws ResourceNotFoundException;
    void deleteAllBooks();




}
