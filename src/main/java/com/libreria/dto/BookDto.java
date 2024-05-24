package com.libreria.dto;

public class BookDto {
    private String nameBook;
    private int authorBook;
    private String yearBook;

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public int getAuthorBook() {
        return authorBook;
    }

    public void setAuthorBook(int authorBook) {
        this.authorBook = authorBook;
    }

    public String getYearBook() {
        return yearBook;
    }

    public void setYearBook(String yearBook) {
        this.yearBook = yearBook;
    }
}
