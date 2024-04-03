package com.mpedroni.bytebookstore.localization.country;

import com.mpedroni.bytebookstore.shared.validators.unique.Unique;
import jakarta.validation.constraints.NotBlank;

public record CreateCountryRequest(
        @NotBlank
        @Unique(entity = Country.class, field = "name")
        String name
) {
}
