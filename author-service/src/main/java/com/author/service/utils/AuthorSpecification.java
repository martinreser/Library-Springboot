package com.author.service.utils;

import com.author.service.model.Author;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class AuthorSpecification {
    public static Specification<Author> alreadyExist(String name, String lastName, String nationality, LocalDate dateOfBirth) {
        return (root, query, criteriaBuilder) -> {
            Predicate namePredicate = criteriaBuilder.equal(root.get("name"), name);
            Predicate lastNamePredicate = criteriaBuilder.equal(root.get("lastName"), lastName);
            Predicate nationalityPredicate = criteriaBuilder.equal(root.get("nationality"), nationality);
            Predicate dateOfBirthPredicate = criteriaBuilder.equal(root.get("dateOfBirth"), dateOfBirth);

            return criteriaBuilder.and(namePredicate, lastNamePredicate, nationalityPredicate, dateOfBirthPredicate);
        };
    }

    public static Specification<Author> hasParams(String name, String lastName, String nationality, Integer age) {
        return Specification.where(hasName(name))
                .and(hasLastName(lastName))
                .and(hasNationality(nationality))
                .and(hasAge(age));
    }

    private static Specification<Author> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null || name.isEmpty() ? null : criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    private static Specification<Author> hasLastName(String lastName) {
        return (root, query, criteriaBuilder) ->
                lastName == null || lastName.isEmpty() ? null : criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%");
    }

    private static Specification<Author> hasNationality(String nationality) {
        return (root, query, criteriaBuilder) ->
                nationality == null || nationality.isEmpty() ? null : criteriaBuilder.like(root.get("nationality"), "%" + nationality + "%");
    }

    private static Specification<Author> hasAge(Integer age) {
        if (age == null)
            return null;
        LocalDate today = LocalDate.now();
        LocalDate lastDate = today.minusYears(age);
        LocalDate firstDate = today.minusYears(age+1)
                .plusDays(1);
        return (root, query, criteriaBuilder) ->
                age == null ? null : criteriaBuilder.between(root.get("dateOfBirth"), firstDate, lastDate);
    }
}
