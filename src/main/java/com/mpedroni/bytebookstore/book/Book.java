package com.mpedroni.bytebookstore.book;

import com.mpedroni.bytebookstore.author.Author;
import com.mpedroni.bytebookstore.category.Category;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

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
        this.price = BigDecimal.valueOf(Math.round(price.doubleValue()));
        this.pagesNumber = pagesNumber;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.category = AggregateReference.to(category.id());
        this.author = AggregateReference.to(author.id());

        selfValidate();
    }

    /**
     * Creates a new book instance.
     * @param title The title of the book. Must be unique.
     * @param summary A brief summary of the book.
     * @param tableOfContents The table of contents of the book. Must support markdown as string.
     * @param price The price of the book.
     * @param pagesNumber The number of pages of the book.
     * @param isbn The ISBN of the book. Must be unique. Doesn't need to be a valid ISBN.
     * @param publicationDate The publication date of the book.
     * @param category The category of the book.
     * @param author The author of the book.
     * @return A new book instance.
     */
    public static Book newBook(
            @NotBlank
            String title,
            @NotBlank
            @Size(max = 500)
            String summary,
            @NotBlank
            String tableOfContents,
            @NotNull
            @Min(20)
            BigDecimal price,
            @NotNull
            @Min(100)
            Integer pagesNumber,
            @NotBlank
            String isbn,
            @NotNull
            @Future
            LocalDate publicationDate,
            @NotNull
            Category category,
            @NotNull
            Author author) {
        return new Book(null, title, summary, tableOfContents, price, pagesNumber, isbn, publicationDate, category, author);
    }

    public Long id() {
        return id;
    }

    public String title() {
        return title;
    }

    public String summary() {
        return summary;
    }

    public String tableOfContents() {
        return tableOfContents;
    }

    public BigDecimal price() {
        return price;
    }

    public Integer pagesNumber() {
        return pagesNumber;
    }

    public String isbn() {
        return isbn;
    }

    public LocalDate publicationDate() {
        return publicationDate;
    }

    public Long categoryId() {
        return category.getId();
    }

    public Long authorId() {
        return author.getId();
    }


    private void selfValidate() {
        BookValidator.validate(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Book other)) return false;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
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
