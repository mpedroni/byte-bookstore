package com.mpedroni.bytebookstore.author;

import com.mpedroni.bytebookstore.shared.validators.Unique;
import jakarta.validation.constraints.*;

public record CreateAuthorRequest(
    @NotBlank
    String name,

    @NotBlank
    @Email(regexp = ".+@.+\\..+")
    @Unique(entity = Author.class, field = "email")
    String email,

    @NotBlank
    @Size(max = 400, message = "must have at most 400 characters")
    String description
) {
}
