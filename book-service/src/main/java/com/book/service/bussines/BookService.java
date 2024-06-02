package com.book.service.bussines;

import com.book.service.dto.BookDto;
import com.book.service.model.Book;
import com.book.service.persistence.exception.BookAlreadyExistsException;
import com.book.service.persistence.exception.NoValidParamsException;
import com.book.service.persistence.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book saveBook(BookDto bookDto) throws ResourceNotFoundException, NoValidParamsException, BookAlreadyExistsException;
    List<Book> findBooks() throws ResourceNotFoundException;
    Optional<Book> findBookById(int id) throws ResourceNotFoundException;
    List<Book> findBooksByParams(String sortBy, String name, Integer authorId, String yearRelease) throws ResourceNotFoundException;
    Book updateBookById(int id, BookDto bookDto) throws ResourceNotFoundException, NoValidParamsException;
    void deleteBookById(int id) throws ResourceNotFoundException;
    void deleteBooks();




}
