package com.libreria.bussines;

import com.libreria.model.Author;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AuthorService {
    List<Author> getAllAuthors();
}
