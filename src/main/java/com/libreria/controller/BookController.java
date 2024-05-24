package com.libreria.controller;

import com.libreria.bussines.BookService;
import com.libreria.dto.BookDto;
import com.libreria.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    private BookService bookService;

    // POST

    @PostMapping("")
    public Book saveBook (@RequestBody BookDto bookDto){
        return bookService.saveBook(bookDto);
    }

    // GET

    @GetMapping("")
    public List<Book> getAllBooks(){
        return bookService.findBooks();
    }

    @GetMapping("/{id}")
    public Optional<Book> getBookById(@PathVariable int id){
        return bookService.findBookById(id);
    }

    @GetMapping("/author/{author}")
    public List<Book> getBooksByAuthor(@PathVariable int author, @RequestParam(required = false) String sortBy){
        return bookService.findBooksByAuthor(author, sortBy);
    }

    @GetMapping("/year/{year}")
    public List<Book> getBooksByYear(@PathVariable String year, @RequestParam(required = false) String sortBy){
        return bookService.findBooksByYear(year, sortBy);
    }

    // PUT

    @PutMapping("/{id}")
    public Optional<Book> updateBookById(@PathVariable int id, @RequestBody BookDto bookDto){
        return bookService.updateBookById(id, bookDto);
    }

    // DELETE

    @DeleteMapping("/{id}")
    public String deleteBookById (@PathVariable int id){
        return bookService.deleteBookById(id);
    }

    @DeleteMapping("")
    public String deleteAllBooks (){
        return bookService.deleteAllBooks();
    }



}
