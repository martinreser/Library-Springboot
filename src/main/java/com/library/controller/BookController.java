package com.library.controller;

import com.library.bussines.BookService;
import com.library.dto.BookDto;
import com.library.model.Book;
import com.library.persistence.exception.BookAlreadyExistsException;
import com.library.util.ErrorDetails;
import com.library.persistence.exception.NoValidParamsException;
import com.library.persistence.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    private BookService bookService;

    // POST

    @PostMapping("/")
    public ResponseEntity<?> saveBook (@RequestBody BookDto bookDto){
        final  ErrorDetails errorDetails;
        try {
            final Book book = bookService.saveBook(bookDto);
            return ResponseEntity.ok().body(book);
        } catch (ResourceNotFoundException e){
            errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Invalid params");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
        catch (NoValidParamsException e) {
            errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Invalid params");
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        } catch (BookAlreadyExistsException e) {
            errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Can´t create");
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }
    }

    // GET

    @GetMapping("/")
    public ResponseEntity<?> getAllBooks(){
        try {
            final List<Book> books = bookService.findBooks();
            return ResponseEntity.ok().body(books);
        } catch (ResourceNotFoundException e){
            final ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "No books");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }}

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable int id) {
        try {
            final Book book = bookService.findBookById(id).get();
            return ResponseEntity.ok().body(book);
        } catch (ResourceNotFoundException e){
            final ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "The requested book was not found");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getBooksByYear(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer authorId,
            @RequestParam(required = false) String yearRelease) {
        try {
            final List<Book> books = bookService.findBooksByParams(sortBy, name, authorId, yearRelease);
            return ResponseEntity.ok().body(books);
        } catch (ResourceNotFoundException e){
            final ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Error");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    // PUT

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBookById(@PathVariable int id, @RequestBody BookDto bookDto){
        final ErrorDetails errorDetails;
        try {
            final Book book = bookService.updateBookById(id, bookDto);
            return ResponseEntity.ok().body(book);
        } catch (ResourceNotFoundException e){
            errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Can't find the book");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
        catch (NoValidParamsException e) {
            errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Invalid params");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookById (@PathVariable int id){
        try {
            bookService.deleteBookById(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e){
            final ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Can't find the book");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteAllBooks (){
        bookService.deleteBooks();
        return ResponseEntity.noContent().build();
    }



}
