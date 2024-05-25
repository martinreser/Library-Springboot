package com.library.controller;

import com.library.bussines.AuthorService;
import com.library.dto.AuthorDto;
import com.library.dto.BookDto;
import com.library.model.Author;
import com.library.model.Book;
import com.library.persistence.exception.AuthorAlreadyExistsException;
import com.library.persistence.exception.NoValidParamsException;
import com.library.persistence.exception.ResourceNotFoundException;
import com.library.util.ErrorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    /** POST */

    @PostMapping("/")
    public ResponseEntity<?> saveAuthor (@RequestBody AuthorDto authorDto){
        final ErrorDetails errorDetails;
        try {
            final Author author = authorService.saveAuthor(authorDto);
            return ResponseEntity.ok().body(author);
        } catch (NoValidParamsException e) {
            errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Invalid params");
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        } catch (AuthorAlreadyExistsException e) {
            errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Can't create");
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }
    }

    /** GET */

    @GetMapping("/")
    public ResponseEntity<?> getAllAuthors(){
        try {
            final List<Author> authors = authorService.findAuthors();
            return ResponseEntity.ok().body(authors);
        } catch (ResourceNotFoundException e) {
            final ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "No authors");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable int id){
        try {
            final Author author = authorService.findAuthorById(id).get();
            return ResponseEntity.ok().body(author);
        } catch (ResourceNotFoundException e) {
            final ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "The requested author was not found");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAuthorsByParam(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String nationality,
            @RequestParam(required = false) Integer age) {
        try {
            final List<Author> authors = authorService.findAuthorsByParams(sortBy, name, lastName, nationality, age);
            return ResponseEntity.ok().body(authors);
        } catch (ResourceNotFoundException e) {
            final ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Error");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    /** PUT */

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthorById(@PathVariable int id, @RequestBody AuthorDto authorDto){
        final ErrorDetails errorDetails;
        try {
            final Author author = authorService.updateAuthorById(id, authorDto);
            return ResponseEntity.ok().body(author);
        } catch (ResourceNotFoundException e){
            errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Can't find the book");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
        catch (NoValidParamsException e) {
            errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Invalid params");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthorById (@PathVariable int id){
        try {
            authorService.deleteAuthorById(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e){
            ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Can't find the author");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteAllAuthors (){
        authorService.deleteAuthors();
        return ResponseEntity.noContent().build();
    }
}
