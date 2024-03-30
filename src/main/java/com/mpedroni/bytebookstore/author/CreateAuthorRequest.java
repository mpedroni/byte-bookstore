package com.mpedroni.bytebookstore.author;

import jakarta.validation.constraints.*;

public record CreateAuthorRequest(
    @NotBlank
    String name,

    @NotBlank
    @Email(regexp = ".+@.+\\..+")
    String email,

    @NotBlank
    @Size(max = 400)
    String description
) {
}
