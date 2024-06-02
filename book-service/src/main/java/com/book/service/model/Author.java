package com.book.service.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Author {
    private int id;

    private String name;

    private String lastName;

    private LocalDate dateOfBirth;

    private String nationality;

    @JsonIgnoreProperties({"author"})
    private List<Book> books;

    private Integer age;

    public Integer getAge() {
        if (dateOfBirth != null) {
            return Period.between(dateOfBirth, LocalDate.now()).getYears();
        } else {
            return null;
        }
    }

    public List<Book> getBooks() {
        if (books != null) {
            return books;
        } else {
            return new ArrayList<>();
        }
    }
}
