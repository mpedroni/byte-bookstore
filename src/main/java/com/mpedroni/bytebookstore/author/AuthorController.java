package com.mpedroni.bytebookstore.author;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/authors")
/*
 * class's intrinsic load: 3
 */
public class AuthorController {
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateAuthorRequest request) {
        var author = authorRepository.save(
                Author.newAuthor(
                        request.name(),
                        request.email(),
                        request.description()
                )
        );

        var location = URI.create("/authors/" + author.id());

        return ResponseEntity.created(location).build();
    }
}
