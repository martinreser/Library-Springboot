package com.library.bussines;

import com.library.A;
import com.library.dto.AuthorDto;
import com.library.model.Author;
import com.library.persistence.exception.AuthorAlreadyExistsException;
import com.library.persistence.exception.NoValidParamsException;
import com.library.persistence.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author saveAuthor(AuthorDto authorDto) throws NoValidParamsException, AuthorAlreadyExistsException;
    List<Author> findAuthors() throws ResourceNotFoundException;
    Optional<Author> findAuthorById(int id) throws ResourceNotFoundException;
    List<Author> findAuthorsByParams(String sortBy, String name, String lastName, String nationality, Integer age) throws ResourceNotFoundException;
    Author updateAuthorById(int id, AuthorDto authorDto) throws ResourceNotFoundException, NoValidParamsException;

    void deleteAuthorById(int id) throws ResourceNotFoundException;
    void deleteAuthors();

}
