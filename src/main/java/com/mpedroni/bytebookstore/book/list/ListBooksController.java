package com.mpedroni.bytebookstore.book.list;

import com.mpedroni.bytebookstore.book.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/books")
// class's intrinsic load: 2
public class ListBooksController {
    private final BookRepository bookRepository;

    public ListBooksController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public ResponseEntity<List<BookListResponse>> list() {
        var books = StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .map(book -> new BookListResponse(book.id(), book.title()))
                .toList();

        return ResponseEntity.ok(books);
    }
}
