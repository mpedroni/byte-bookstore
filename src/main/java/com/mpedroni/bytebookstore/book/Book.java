package com.mpedroni.bytebookstore.book;

import com.mpedroni.bytebookstore.author.Author;
import com.mpedroni.bytebookstore.category.Category;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("books")
public class Book {
    @Id
    private Long id;
    private String title;
    private String summary;
    private String tableOfContents;
    private BigDecimal price;
    private Integer pagesNumber;
    private String isbn;
    private LocalDate publicationDate;

    @Column("category_id")
    private AggregateReference<Category, Long> category;

    @Column("author_id")
    private AggregateReference<Author, Long> author;

    @Deprecated
    public Book() {
    }

    private Book(Long id, String title, String summary, String tableOfContents, BigDecimal price, Integer pagesNumber, String isbn, LocalDate publicationDate, Category category, Author author) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.tableOfContents = tableOfContents;
        this.price = price;
        this.pagesNumber = pagesNumber;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.category = AggregateReference.to(category.id());
        this.author = AggregateReference.to(author.id());
    }

    public static Book newBook(String title, String summary, String tableOfContents, BigDecimal price, Integer pagesNumber, String isbn, LocalDate publicationDate, Category category, Author author) {
        return new Book(null, title, summary, tableOfContents, price, pagesNumber, isbn, publicationDate, category, author);
    }

    public Long id() {
        return id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", tableOfContents='" + tableOfContents + '\'' +
                ", price=" + price +
                ", pagesNumber=" + pagesNumber +
                ", isbn='" + isbn + '\'' +
                ", publicationDate=" + publicationDate +
                ", category=" + category +
                ", author=" + author +
                '}';
    }
}
