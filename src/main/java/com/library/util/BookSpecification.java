package com.library.util;

import com.library.model.Author;
import com.library.model.Book;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
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
}
