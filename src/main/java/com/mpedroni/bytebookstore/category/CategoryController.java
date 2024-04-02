package com.mpedroni.bytebookstore.category;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateCategoryRequest request) {
        var category = categoryRepository.save(
                Category.newCategory(request.name())
        );

        var location = URI.create("/categories/" + category.id());

        return ResponseEntity.created(location).build();
    }
}
