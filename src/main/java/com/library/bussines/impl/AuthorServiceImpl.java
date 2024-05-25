package com.library.bussines.impl;

import com.library.A;
import com.library.bussines.AuthorService;
import com.library.bussines.BookService;
import com.library.dto.AuthorDto;
import com.library.model.Author;
import com.library.model.Book;
import com.library.persistence.AuthorRepository;
import com.library.persistence.exception.AuthorAlreadyExistsException;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    private final BookService bookService;

    public AuthorServiceImpl(@Lazy BookService bookService) {
        super();
        this.bookService = bookService;
    }

    @Override
    public Author saveAuthor(AuthorDto authorDto) throws NoValidParamsException, AuthorAlreadyExistsException {
        final Author newAuthor = new Author();
        if (!UtilClass.isValidWord(authorDto.getName()))
            throw new NoValidParamsException("The name can't be null, empty or contains numbers.");
        if (!UtilClass.isValidWord(authorDto.getLastName()))
            throw new NoValidParamsException("The last name can't be null, empty or contains numbers.");
        if (!UtilClass.isValidWord(authorDto.getNationality()))
            throw new NoValidParamsException("The nationality can't be null, empty or contains numbers.");
        LocalDate dateOfBirth = UtilClass.formatDate(authorDto.getDateOfBirth());
        if (dateOfBirth == null)
            throw new NoValidParamsException("The date of birth is an invalid date or is not in the desired format (yyyy-MM-dd)");
        newAuthor.setName(UtilClass.formatString(authorDto.getName()));
        newAuthor.setLastName(UtilClass.formatString(authorDto.getLastName()));
        newAuthor.setDateOfBirth(dateOfBirth);
        newAuthor.setNationality(authorDto.getNationality());
        if (authorExist(newAuthor))
            throw new AuthorAlreadyExistsException("Already exist an author with the same name, last name, nationality and date of birth.");
            return authorRepository.save(newAuthor);
    }

    @Override
    public List<Author> findAuthors() throws ResourceNotFoundException {
        List<Author> authors = authorRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        if (authors.isEmpty())
            throw new ResourceNotFoundException("There is no authors");
        return authors;
    }

    @Override
    public Optional<Author> findAuthorById(int id) throws ResourceNotFoundException {
        final Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty())
            throw new ResourceNotFoundException("Author not found for ID: " + id);
        return author;
    }

    @Override
    public List<Author> findAuthorsByParams(String sortBy, String name, String lastName, String nationality, Integer age) throws ResourceNotFoundException {
        if (sortBy == null || sortBy == "") sortBy = "name";
        Specification<Author> specification = AuthorSpecification.hasParams(name, lastName, nationality, age);
        List<Author> authors = authorRepository.findAll(specification, Sort.by(Sort.Direction.ASC, sortBy));
        if (authors.isEmpty())
            throw new ResourceNotFoundException("Doesn´t exists books with the indicates params.");
        return authors;
    }

    @Override
    public Author updateAuthorById(int id, AuthorDto authorDto) throws ResourceNotFoundException, NoValidParamsException {
        final Optional<Author> authorToUpdate = authorRepository.findById(id);
        if (authorToUpdate.isEmpty())
            throw new ResourceNotFoundException("The author with the ID: " + id + " doesn't exist.");
        if (UtilClass.isValidWord(authorDto.getName()))
            authorToUpdate.get().setName(UtilClass.formatString(authorDto.getName()));
        if (UtilClass.isValidWord(authorDto.getLastName()))
            authorToUpdate.get().setLastName(UtilClass.formatString(authorDto.getLastName()));
        if (UtilClass.isValidWord(authorDto.getNationality()))
            authorToUpdate.get().setNationality(UtilClass.formatString(authorDto.getNationality()));
        LocalDate dateOfBirth = UtilClass.formatDate(authorDto.getDateOfBirth());
        if (dateOfBirth != null) authorToUpdate.get().setDateOfBirth(dateOfBirth);
        if (!UtilClass.isValidWord(authorDto.getName()) && !UtilClass.isValidWord(authorDto.getLastName()) &&
                !UtilClass.isValidWord(authorDto.getNationality()) && dateOfBirth == null)
            throw new NoValidParamsException("The name, the last name, the nationality and the date of birth are invalid.");
        return authorRepository.save(authorToUpdate.get());
    }

    @Override
    public void deleteAuthorById(int id) throws ResourceNotFoundException {
        final Optional<Author> authorToDelete = authorRepository.findById(id);
        if (authorToDelete.isEmpty()){
            throw new ResourceNotFoundException("The author with the ID: " + id + " doesn't exist.");
        }
        List<Book> booksToDelete = authorToDelete.get().getBooks();
        booksToDelete.stream()
                        .forEach(book -> {
                            try {
                                bookService.deleteBookById(book.getId());
                            } catch (ResourceNotFoundException e) {
                            }
                        });

        authorRepository.deleteById(id);
    }

    @Override
    public void deleteAuthors() {
        authorRepository.deleteAll();
    }

    private boolean authorExist(Author author){
        Specification<Author> specification = AuthorSpecification.alreadyExist(author.getName(), author.getLastName(), author.getNationality(), author.getDateOfBirth());
        return !authorRepository.findAll(specification).isEmpty();
    }
}
