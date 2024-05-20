package com.libreria.controller;

import com.libreria.bussines.BookService;
import com.libreria.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{bookId}")
    public Book getBookById(@PathVariable("bookId") int id){
        return bookService.findBookById(id);
    }

    @GetMapping("/")
    public List<Book> getBooks(){
        return bookService.findBooks();
    }

    @GetMapping("")
    public List<Book> getBooksByName(@RequestParam String nameBook){
        return bookService.findBooksByName(nameBook);
    }

    /*@GetMapping("")
    public List<Book> getBooksByYear(@RequestParam String yearBook){
        return bookService.findBooksByYear(yearBook);
    }

    @GetMapping("")
    public List<Book> getBooksByAuthor(@RequestParam String authorBook){
        return bookService.findBooksByAuthor(authorBook);
    }*/

}
