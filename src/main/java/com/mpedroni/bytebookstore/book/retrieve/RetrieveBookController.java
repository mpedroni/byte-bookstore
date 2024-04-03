package com.mpedroni.bytebookstore.book.retrieve;

import com.mpedroni.bytebookstore.author.AuthorRepository;
import com.mpedroni.bytebookstore.book.BookRepository;
import com.mpedroni.bytebookstore.book.exception.BookNotFoundException;
import com.mpedroni.bytebookstore.category.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class RetrieveBookController {
    private final BookRepository books;
    private final AuthorRepository authors;
    private final CategoryRepository categories;

    public RetrieveBookController(BookRepository books, AuthorRepository authors, CategoryRepository categories) {
        this.books = books;
        this.authors = authors;
        this.categories = categories;
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<BookDetails> retrieve(@PathVariable Long id) {
        var book = books.findById(id).orElseThrow(() -> new BookNotFoundException("Book with id %d not found".formatted(id)));

        var author = authors.findById(book.authorId()).orElseThrow(() -> new IllegalStateException("Author not found"));
        var category = categories.findById(book.categoryId()).orElseThrow(() -> new IllegalStateException("Category not found"));

        return ResponseEntity.ok(BookDetails.from(book, category, author));
    }
}
