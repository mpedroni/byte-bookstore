package com.mpedroni.bytebookstore.book.create;

import com.mpedroni.bytebookstore.author.Author;
import com.mpedroni.bytebookstore.book.Book;
import com.mpedroni.bytebookstore.category.Category;
import com.mpedroni.bytebookstore.shared.validators.exists.ExistsById;
import com.mpedroni.bytebookstore.shared.validators.unique.Unique;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateBookRequest(
    @NotEmpty
    @Unique(entity = Book.class, field = "title")
    String title,

    @NotEmpty
    @Size(max = 500)
    String summary,

    @NotEmpty
    String tableOfContents,

    @NotNull
    @Min(20)
    BigDecimal price,

    @NotNull
    @Min(100)
    Integer pagesNumber,

    @NotEmpty
    @Unique(entity = Book.class, field = "isbn")
    String isbn,

    @NotNull
    @Future
    LocalDate publicationDate,

    @NotNull
    @ExistsById(entity = Category.class)
    Long categoryId,

    @NotNull
    @ExistsById(entity = Author.class)
    Long authorId
) {
}
