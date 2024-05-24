package com.library.util;

import com.library.model.Author;
import com.library.model.Book;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<Book> hasAuthor(int authorId) {
        return (root, query, criteriaBuilder) -> {
            Join<Book, Author> authorJoin = root.join("author");
            return criteriaBuilder.equal(authorJoin.get("id"), authorId);
        };
    }

    public static Specification<Book> hasYear(String yearRelease) {
        return (root, query, criteriaBuilder) -> {
            Path<String> yearPath = root.get("yearRelease");
            return criteriaBuilder.equal(yearPath, yearRelease);
        };
    }

    public static Specification<Book> alreadyExist(int authorId, String bookName, String year) {
        return (root, query, criteriaBuilder) -> {
            Join<Book, Author> authorJoin = root.join("author");

            Predicate authorPredicate = criteriaBuilder.equal(authorJoin.get("id"), authorId);
            Predicate namePredicate = criteriaBuilder.equal(root.get("name"), bookName);
            if (year != null){
                Predicate yearPredicate = criteriaBuilder.equal(root.get("yearRelease"), year);
                return criteriaBuilder.and(authorPredicate, namePredicate, yearPredicate);
            }
            return criteriaBuilder.and(authorPredicate, namePredicate);
        };
    }
}
