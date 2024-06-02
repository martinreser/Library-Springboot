package com.book.service.bussines.impl;

import com.book.service.bussines.BookService;
import com.book.service.dto.BookDto;
import com.book.service.model.Author;
import com.book.service.model.Book;
import com.book.service.persistence.BookRepository;
import com.book.service.persistence.exception.BookAlreadyExistsException;
import com.book.service.persistence.exception.NoValidParamsException;
import com.book.service.persistence.exception.ResourceNotFoundException;
import com.book.service.util.BookSpecification;
import com.book.service.util.UtilClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RestTemplate restTemplate;

    final String authorServiceUrl = "http://localhost:8003/author/";


    @Override
    public Book saveBook(BookDto bookDto) throws ResourceNotFoundException, NoValidParamsException, BookAlreadyExistsException {
        final Book newBook = new Book();
        Author author = null;
        try {
            author = restTemplate.getForObject(authorServiceUrl + bookDto.getAuthorId(), Author.class);
        } catch (HttpClientErrorException e){}
        if (author == null)
            throw new ResourceNotFoundException("The author with the ID: " + bookDto.getAuthorId() + " doesn't exist.");
        if (!UtilClass.isValidString(bookDto.getName()))
            throw new NoValidParamsException("The name of the book can't be null or empty.");
        if (!UtilClass.isValidYear(bookDto.getYearRelease()))
            throw new NoValidParamsException("The year is invalid.");
        newBook.setName(UtilClass.formatString(bookDto.getName()));
        newBook.setIdAuthor(author.getId());
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
            throw new ResourceNotFoundException("The book with the ID: " + id + " doesn't exist.");
        Author authorToUpdate = null;
        if (bookToUpdate.get().getIdAuthor() != bookDto.getAuthorId()){
            try {
                authorToUpdate = restTemplate.getForObject(authorServiceUrl + bookDto.getAuthorId(), Author.class);
            } catch (HttpClientErrorException e){}
        }
        if (authorToUpdate != null)
            bookToUpdate.get().setIdAuthor(authorToUpdate.getId());
        if (UtilClass.isValidString(bookDto.getName()))
            bookToUpdate.get().setName(UtilClass.formatString(bookDto.getName()));
        if (UtilClass.isValidYear(bookDto.getYearRelease()) && bookDto.getYearRelease() != null)
            bookToUpdate.get().setYearRelease(bookDto.getYearRelease());
        if (!UtilClass.isValidString(bookDto.getName()) && !UtilClass.isValidYear(bookDto.getYearRelease()) && bookDto.getYearRelease() == null && authorToUpdate == null)
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
        Specification<Book> specification = BookSpecification.alreadyExist(book.getIdAuthor(), book.getName(), book.getYearRelease());
        return !bookRepository.findAll(specification).isEmpty();
    }
}
