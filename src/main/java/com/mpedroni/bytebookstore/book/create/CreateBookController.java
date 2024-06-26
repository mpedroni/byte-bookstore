package com.mpedroni.bytebookstore.book.create;

import com.mpedroni.bytebookstore.author.AuthorRepository;
import com.mpedroni.bytebookstore.book.Book;
import com.mpedroni.bytebookstore.book.BookRepository;
import com.mpedroni.bytebookstore.category.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/books")
// class's intrinsic load: 5
public class CreateBookController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public CreateBookController(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
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
}
