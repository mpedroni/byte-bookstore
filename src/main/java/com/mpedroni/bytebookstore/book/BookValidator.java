package com.mpedroni.bytebookstore.book;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookValidator {
    public static void validate(Book book) {
        if (book.title() == null || book.title().isBlank()) {
            throw new IllegalArgumentException("'title' must not be empty");
        }
        if (book.summary() == null || book.summary().isBlank()) {
            throw new IllegalArgumentException("'summary' must not be empty");
        }
        if (book.summary().length() > 500) {
            throw new IllegalArgumentException("'summary' must have at most 500 characters");
        }
        if (book.tableOfContents() == null || book.tableOfContents().isBlank()) {
            throw new IllegalArgumentException("'tableOfContents' must not be empty");
        }
        if (book.price() == null || book.price().compareTo(BigDecimal.valueOf(20)) < 0) {
            throw new IllegalArgumentException("'price' must be greater than 20");
        }
        if (book.pagesNumber() == null || book.pagesNumber() <= 100) {
            throw new IllegalArgumentException("'pagesNumber' must be greater than 100");
        }
        if (book.isbn() == null || book.isbn().isBlank()) {
            throw new IllegalArgumentException("'isbn' must not be empty");
        }
        if (book.publicationDate() == null || book.publicationDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("'publicationDate' must be a future date");
        }
        if (book.categoryId() == null) {
            throw new IllegalArgumentException("'category' must not be null");
        }
        if (book.authorId() == null) {
            throw new IllegalArgumentException("'author' must not be null");
        }
    }
}
