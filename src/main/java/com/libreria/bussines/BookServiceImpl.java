package com.libreria.bussines;

import com.libreria.model.Book;
import com.libreria.persistence.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public Book findBookById(int id) {
        return bookDao.findBookById(id);
    }

    @Override
    public List<Book> findBooks() {
        return bookDao.findBooks();
    }

    @Override
    public List<Book> findBooksByName(String nameBook) {
        return bookDao.findBooksByName(nameBook);
    }

    @Override
    public List<Book> findBooksByYear(String yearBook) {
        return bookDao.findBooksByYear(yearBook);
    }

    @Override
    public List<Book> findBooksByAuthor(String authorBook) {
        return bookDao.findBooksByAuthor(authorBook);
    }
}
