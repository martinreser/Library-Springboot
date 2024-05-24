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
        try {
            Book book = bookService.saveBook(bookDto);
            return ResponseEntity.ok().body(book);
        } catch (ResourceNotFoundException e){
            ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Invalid params");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
        catch (NoValidParamsException e) {
            ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Invalid params");
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        } catch (BookAlreadyExistsException e) {
            ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Can´t create");
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }
    }

    // GET

    @GetMapping("/")
    public ResponseEntity<?> getAllBooks(){
        try {
            List<Book> books = bookService.findBooks();
            if (books.isEmpty())
                throw new ResourceNotFoundException("There is no book");
            return ResponseEntity.ok().body(books);
        } catch (ResourceNotFoundException e){
            ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "No books");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }}

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable int id) {
        try {
            Book book = bookService.findBookById(id).get();
            return ResponseEntity.ok().body(book);
        } catch (ResourceNotFoundException e){
            ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "The requested book was not found");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<?> getBooksByAuthor(@PathVariable int author, @RequestParam(required = false) String sortBy){
        try {
            List<Book> books = bookService.findBooksByAuthor(author, sortBy);
            if (books.isEmpty())
                throw new ResourceNotFoundException("The author with the ID: " + author + " doesn't have books");
            return ResponseEntity.ok().body(books);
        } catch (ResourceNotFoundException e){
            ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "No books for the author");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<?> getBooksByYear(@PathVariable String year, @RequestParam(required = false) String sortBy){
        try {
            List<Book> books = bookService.findBooksByYear(year, sortBy);
            if (books.isEmpty())
                throw new ResourceNotFoundException("Doesn´t exists books with " + year + " year release");
            return ResponseEntity.ok().body(books);
        } catch (ResourceNotFoundException e){
            ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "No books in the year");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    // PUT

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBookById(@PathVariable int id, @RequestBody BookDto bookDto){
        try {
            Book book = bookService.updateBookById(id, bookDto).get();
            return ResponseEntity.ok().body(book);
        } catch (ResourceNotFoundException e){
            ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Can't find the book");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
        catch (NoValidParamsException e) {
            ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Invalid params");
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
            ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Can't find the book");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteAllBooks (){
        bookService.deleteAllBooks();
        return ResponseEntity.noContent().build();
    }



}
