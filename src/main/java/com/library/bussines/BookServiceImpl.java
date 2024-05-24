package com.library.bussines;

import com.library.dto.BookDto;
import com.library.model.Author;
import com.library.model.Book;
import com.library.persistence.BookRepository;
import com.library.persistence.exception.BookAlreadyExistsException;
import com.library.persistence.exception.NoValidParamsException;
import com.library.persistence.exception.ResourceNotFoundException;
import com.library.util.BookSpecification;
import com.library.util.FormatString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    @Override
    public Book saveBook(BookDto bookDto) throws ResourceNotFoundException, NoValidParamsException, BookAlreadyExistsException {
        final Book newBook = new Book();
        final Optional<Author> author = authorService.getAuthorById(bookDto.getAuthorId());
        if (author.isEmpty())
            throw new ResourceNotFoundException("The author with the ID: " + bookDto.getAuthorId() + " doesn't exist.");
        if (!isValidString(bookDto.getName()))
            throw new NoValidParamsException("The name of the book can't be null or empty.");
        if (!isValidYear(bookDto.getYearRelease()))
            throw new NoValidParamsException("The year is invalid.");
        newBook.setName(FormatString.f(bookDto.getName()));
        newBook.setAuthor(author.get());
        newBook.setYearRelease(bookDto.getYearRelease());
        if (booksExist(newBook))
            throw new BookAlreadyExistsException("Already exist a book with the same name, author and year release.");
        return bookRepository.save(newBook);
    }

    @Override
    public List<Book> findBooks() {
        return bookRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public Optional<Book> findBookById(int id) throws ResourceNotFoundException {
        final Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty())
            throw new ResourceNotFoundException("Book not found for ID: " + id);
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findBooksByAuthor(int authorId, String sortBy) {
        sortBy = sortBy != null ? sortBy : "name";
        Specification<Book> specification = BookSpecification.hasAuthor(authorId);

        return bookRepository.findAll(specification, Sort.by(Sort.Direction.ASC, sortBy));
    }

    @Override
    public List<Book> findBooksByYear(String year, String sortBy) {
        sortBy = sortBy != null ? sortBy : "name";
        Specification<Book> specification = BookSpecification.hasYear(year);


        return bookRepository.findAll(specification, Sort.by(Sort.Direction.ASC, sortBy));
    }

    @Override
    public Optional<Book> updateBookById(int id, BookDto bookDto) throws ResourceNotFoundException, NoValidParamsException {
        final Optional<Book> bookToUpdate = bookRepository.findById(id);
        if (bookToUpdate.isEmpty())
            throw new ResourceNotFoundException("The book with the ID: " + bookDto.getAuthorId() + " doesn't exist.");
        Optional<Author> authorToUpdate = Optional.empty();
        if (bookToUpdate.get().getAuthor().getId() != id){
            authorToUpdate = authorService.getAuthorById(id);
        }
        if (authorToUpdate.isPresent())
            bookToUpdate.get().setAuthor(authorToUpdate.get());
        if (isValidString(bookDto.getName()))
            bookToUpdate.get().setName(FormatString.f(bookDto.getName()));
        if (isValidYear(bookDto.getYearRelease()))
            bookToUpdate.get().setYearRelease(bookDto.getYearRelease());
        if (!isValidString(bookDto.getName()) && !isValidYear(bookDto.getYearRelease()) && authorToUpdate.isEmpty())
            throw new NoValidParamsException("The name, the author and the year are invalid.");
        bookRepository.save(bookToUpdate.get());
        return bookToUpdate;
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
    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }

    private boolean isValidString(String string){
        if (string == null || string.equals(""))
            return false;
        return true;
    }

    private boolean isValidYear(String yearRelease){
        int nowYear = LocalDate.now().getYear();
        int year = 0;
        try {
             year = Integer.parseInt(yearRelease);
        }
        catch (NumberFormatException n){}
        if (year > nowYear || year < 0) return false;
        return true;
    }

    private boolean booksExist(Book book){
        Specification<Book> specification = BookSpecification.alreadyExist(book.getAuthor().getId(), book.getName(), book.getYearRelease());
        return !bookRepository.findAll(specification).isEmpty();
    }
}
