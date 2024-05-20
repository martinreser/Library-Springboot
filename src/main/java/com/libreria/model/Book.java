package com.libreria.model;


public class Book {
    private String name;
    private String author;
    private String yearRelease;

    public Book(String name, String author, String yearRelease) {
        this.name = name;
        this.author = author;
        this.yearRelease = yearRelease;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYearRelease() {
        return yearRelease;
    }

    public void setYearRelease(String yearRelease) {
        this.yearRelease = yearRelease;
    }
}
