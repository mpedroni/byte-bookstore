package com.mpedroni.bytebookstore.category;

import com.mpedroni.bytebookstore.shared.validators.unique.Unique;
import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
        @NotBlank
        @Unique(entity = Category.class, field = "name")
        String name
) {
}
