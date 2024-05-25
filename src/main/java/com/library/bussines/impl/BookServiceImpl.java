package com.library.bussines.impl;

import com.library.bussines.AuthorService;
import com.library.bussines.BookService;
import com.library.dto.BookDto;
import com.library.model.Author;
import com.library.model.Book;
import com.library.persistence.BookRepository;
import com.library.persistence.exception.BookAlreadyExistsException;
import com.library.persistence.exception.NoValidParamsException;
import com.library.persistence.exception.ResourceNotFoundException;
import com.library.util.AuthorSpecification;
import com.library.util.BookSpecification;
import com.library.util.UtilClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    private final AuthorService authorService;

    public BookServiceImpl(@Lazy AuthorService authorService) {
        super();
        this.authorService = authorService;
    }

    @Override
    public Book saveBook(BookDto bookDto) throws ResourceNotFoundException, NoValidParamsException, BookAlreadyExistsException {
        final Book newBook = new Book();
        final Optional<Author> author = authorService.findAuthorById(bookDto.getAuthorId());
        if (author.isEmpty())
            throw new ResourceNotFoundException("The author with the ID: " + bookDto.getAuthorId() + " doesn't exist.");
        if (!UtilClass.isValidString(bookDto.getName()))
            throw new NoValidParamsException("The name of the book can't be null or empty.");
        if (!UtilClass.isValidYear(bookDto.getYearRelease()))
            throw new NoValidParamsException("The year is invalid.");
        newBook.setName(UtilClass.formatString(bookDto.getName()));
        newBook.setAuthor(author.get());
        newBook.setYearRelease(bookDto.getYearRelease());
        if (booksExist(newBook))
            throw new BookAlreadyExistsException("Already exist a book with the same name, author and year release.");
        return bookRepository.save(newBook);
    }

    @Override
    public List<Book> findBooks() throws ResourceNotFoundException {
        List<Book> books = bookRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        if (books.isEmpty())
            throw new ResourceNotFoundException("There is no books");
        return books;
    }

    @Override
    public Optional<Book> findBookById(int id) throws ResourceNotFoundException {
        final Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty())
            throw new ResourceNotFoundException("Book not found for ID: " + id);
        return book;
    }

    @Override
    public List<Book> findBooksByParams(String sortBy, String name, Integer authorId, String yearRelease) throws ResourceNotFoundException {
        if (sortBy == null || sortBy == "") sortBy = "name";
        Specification<Book> specification = BookSpecification.hasParams(name, authorId, yearRelease);
        List<Book> books = bookRepository.findAll(specification, Sort.by(Sort.Direction.ASC, sortBy));
        if (books.isEmpty())
            throw new ResourceNotFoundException("Doesn´t exists books with the indicates params.");
        return books;
    }

    @Override
    public Book updateBookById(int id, BookDto bookDto) throws ResourceNotFoundException, NoValidParamsException {
        final Optional<Book> bookToUpdate = bookRepository.findById(id);
        if (bookToUpdate.isEmpty())
            throw new ResourceNotFoundException("The book with the ID: " + bookDto.getAuthorId() + " doesn't exist.");
        Optional<Author> authorToUpdate = Optional.empty();
        if (bookToUpdate.get().getAuthor().getId() != id){
            authorToUpdate = authorService.findAuthorById(id);
        }
        if (authorToUpdate.isPresent())
            bookToUpdate.get().setAuthor(authorToUpdate.get());
        if (UtilClass.isValidString(bookDto.getName()))
            bookToUpdate.get().setName(UtilClass.formatString(bookDto.getName()));
        if (UtilClass.isValidYear(bookDto.getYearRelease()))
            bookToUpdate.get().setYearRelease(bookDto.getYearRelease());
        if (!UtilClass.isValidString(bookDto.getName()) && !UtilClass.isValidYear(bookDto.getYearRelease()) && authorToUpdate.isEmpty())
            throw new NoValidParamsException("The name, the author and the year are invalid.");
        return bookRepository.save(bookToUpdate.get());
    }

    @Override
    public void deleteBookById(int id) throws ResourceNotFoundException {
        final Optional<Book> bookToDelete = bookRepository.findById(id);
        if (bookToDelete.isEmpty()){
            throw new ResourceNotFoundException("The book with the ID: " + id + " doesn't exist.");
        }
        bookRepository.deleteById(id);
    }

    @Override
    public void deleteBooks() {
        bookRepository.deleteAll();
    }

    private boolean booksExist(Book book){
        Specification<Book> specification = BookSpecification.alreadyExist(book.getAuthor().getId(), book.getName(), book.getYearRelease());
        return !bookRepository.findAll(specification).isEmpty();
    }
}
