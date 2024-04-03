package com.mpedroni.bytebookstore.localization.state;

import com.mpedroni.bytebookstore.localization.country.Country;
import com.mpedroni.bytebookstore.shared.validators.exists.ExistsById;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateStateRequest(
        @NotBlank
        String name,

        @NotNull
        @ExistsById(entity = Country.class)
        Long countryId
) {
}
