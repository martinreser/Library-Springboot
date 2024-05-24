package com.library.persistence.exception;

public class BookAlreadyExistsException extends Exception {
    public BookAlreadyExistsException(String s) {
        super(s);
    }
}
