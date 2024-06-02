package com.book.service.util;


import com.book.service.model.Author;
import com.book.service.model.Book;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<Book> alreadyExist(int authorId, String bookName, String year) {
        return (root, query, criteriaBuilder) -> {
            Predicate namePredicate = criteriaBuilder.equal(root.get("name"), bookName);
            Predicate authorPredicate = criteriaBuilder.equal(root.get("idAuthor"), authorId);
            if (year != null){
                Predicate yearPredicate = criteriaBuilder.equal(root.get("yearRelease"), year);
                return criteriaBuilder.and(namePredicate, yearPredicate, authorPredicate);
            }
            return criteriaBuilder.and(namePredicate, authorPredicate);
        };
    }

    public static Specification<Book> hasParams(String name, Integer authorId, String yearRelease) {
        return Specification.where(hasAuthor(authorId))
                .and(hasYear(yearRelease))
                .and(hasName(name));
    }

    public static Specification<Book> hasAuthor(Integer authorId) {
        return (root, query, criteriaBuilder) ->
            authorId == null ? null : criteriaBuilder.equal(root.get("idAuthor"), authorId);
    }

    public static Specification<Book> hasYear(String yearRelease) {
        return (root, query, criteriaBuilder) ->
            yearRelease == null || yearRelease.isEmpty() ? null : criteriaBuilder.equal(root.get("yearRelease"), yearRelease);
    }

    public static Specification<Book> hasName(String name) {
        return (root, query, criteriaBuilder) ->
            name == null || name.isEmpty() ? null : criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }


}
