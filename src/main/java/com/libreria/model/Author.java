package com.libreria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity(name = "author")
@Table(name = "AUTHORS")
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

    @Transient()
    private int age = calculateAge();

    private int calculateAge() {
        if (dateOfBirth == null) {
            return 0;
        }
        LocalDate currentDate = LocalDate.now();
        return Period.between(dateOfBirth, currentDate).getYears();
    }



}
