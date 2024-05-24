package com.libreria.bussines;

import com.libreria.dto.BookDto;
import com.libreria.model.Author;
import com.libreria.model.Book;
import com.libreria.persistence.BookRepository;
import com.libreria.util.BookSpecification;
import com.libreria.util.FormatString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.Format;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    @Override
    public Book saveBook(BookDto bookDto) {
        final Book newBook = new Book();
        final Author author = authorService.getAuthorById(bookDto.getAuthorBook()).get();
        newBook.setName(FormatString.f(bookDto.getNameBook()));
        newBook.setAuthor(author);
        newBook.setYearRelease(bookDto.getYearBook());
        return bookRepository.save(newBook);
    }

    @Override
    public List<Book> findBooks() {
        return bookRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public Optional<Book> findBookById(int id) {
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
    public Optional<Book> updateBookById(int id, BookDto bookDto) {
        final Optional<Book> bookToUpdate = bookRepository.findById(id);
        Optional<Author> authorToUpdate = null;
        if (bookToUpdate.get().getAuthor().getId() != id){
            authorToUpdate = authorService.getAuthorById(id);
        }
        if (isValidString(bookDto.getNameBook()))
            bookToUpdate.get().setName(FormatString.f(bookDto.getNameBook()));
        if (authorToUpdate.get() != null)
            bookToUpdate.get().setAuthor(authorToUpdate.get());
        if (isValidYear(bookDto.getYearBook()))
            bookToUpdate.get().setYearRelease(bookDto.getYearBook());
        bookRepository.save(bookToUpdate.get());
        return bookToUpdate;
    }

    @Override
    public String deleteBookById(int id){
        if (bookRepository.findById(id).isPresent()){
            bookRepository.deleteById(id);
            return "The book with ID: " + id + " was deleted.";
        }
        return "We can't find the book with ID: " + id;
    }

    @Override
    public String deleteAllBooks() {
        bookRepository.deleteAll();
        return "All the books were deleted.";
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
        if (year > nowYear) return false;
        return true;
    }
}
