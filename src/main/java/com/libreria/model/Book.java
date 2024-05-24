package com.libreria.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity(name = "book")
@Table(name = "BOOKS")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_book", nullable = false)
    private int id;

    @Column(name = "name_book", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_author")
    @JsonIgnoreProperties({"dateOfBirth", "age", "nacionality", "books"})
    private Author author;

    @Column(name = "year_release")
    private String yearRelease;
}
