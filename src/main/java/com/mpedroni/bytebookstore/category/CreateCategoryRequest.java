package com.mpedroni.bytebookstore.category;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
        @NotBlank
        String name
) {
}
