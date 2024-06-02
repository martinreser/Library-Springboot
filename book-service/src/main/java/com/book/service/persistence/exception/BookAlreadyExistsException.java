package com.book.service.persistence.exception;


public class BookAlreadyExistsException extends Exception {
    public BookAlreadyExistsException(String s) {
        super(s);
    }
}
