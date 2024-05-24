package com.libreria.bussines;

import com.libreria.model.Author;
import com.libreria.persistence.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public List<Author> getAllAuthors() {
        final List<Author> authors = authorRepository.findAll();
        for (Author author: authors) {
            setAgeAuthor(author);
        }
        return authors;
    }

    @Override
    public Optional<Author> getAuthorById(int id) {
        final Optional<Author> author = authorRepository.findById(id);
        author.get().setAge();
        return author;
    }

    private Author setAgeAuthor(Author author){
        author.setAge();
        return author;
    }
}
