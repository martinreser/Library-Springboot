package com.library.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
@Entity(name = "author")
@Table(name = "AUTHORS")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_author", nullable = false)
    private int id;

    @Column(name = "name_author", nullable = false)
    private String name;

    @Column(name = "last_name_author", nullable = false)
    private String lastName;

    @Column(name = "date_birth_author")
    private LocalDate dateOfBirth;

    @Column(name = "nacionality_author", nullable = false)
    private String nacionality;

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties({"author"})
    private List<Book> books;

    @Transient()
    private Integer age;

    public Integer getAge() {
        if (dateOfBirth != null) {
            return Period.between(dateOfBirth, LocalDate.now()).getYears();
        } else {
            return null;
        }
    }
}
