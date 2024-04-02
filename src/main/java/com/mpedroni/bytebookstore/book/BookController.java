package com.mpedroni.bytebookstore.book;

import com.mpedroni.bytebookstore.author.AuthorRepository;
import com.mpedroni.bytebookstore.category.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/books")
// class's intrinsic load: 6
public class BookController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateBookRequest request) {
        var author = authorRepository.findById(request.authorId()).orElseThrow();
        var category = categoryRepository.findById(request.categoryId()).orElseThrow();

        var book = bookRepository.save(
                Book.newBook(
                        request.title(),
                        request.summary(),
                        request.tableOfContents(),
                        request.price(),
                        request.pagesNumber(),
                        request.isbn(),
                        request.publicationDate(),
                        category,
                        author
                )
        );

        var location = URI.create("/books/" + book.id());

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<BookListResponse>> list() {
        var books = StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .map(book -> new BookListResponse(book.id(), book.title()))
                .toList();

        return ResponseEntity.ok(books);
    }
}
