package com.mpedroni.bytebookstore.book.retrieve;

import com.mpedroni.bytebookstore.author.Author;
import com.mpedroni.bytebookstore.book.Book;
import com.mpedroni.bytebookstore.category.Category;

public record BookDetails(
        Long id,
        String title,
        String summary,
        String tableOfContents,
        String price,
        Integer pagesNumber,
        String isbn,
        String publicationDate,
        BookDetailsCategory category,
        BookDetailsAuthor author
) {
    public static BookDetails from(Book book, Category category, Author author) {
        var authorDetails = new BookDetailsAuthor(
            author.id(),
            author.name(),
            author.description()
        );

        var categoryDetails = new BookDetailsCategory(
            category.id(),
            category.name()
        );

        return new BookDetails(
            book.id(),
            book.title(),
            book.summary(),
            book.tableOfContents(),
            book.price().toString(),
            book.pagesNumber(),
            book.isbn(),
            book.publicationDate().toString(),
            categoryDetails,
            authorDetails
        );
    }

    private record BookDetailsAuthor(
        Long id,
        String name,
        String description) {}

    private record BookDetailsCategory(
        Long id,
        String name) {}
}
