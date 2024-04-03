package com.mpedroni.bytebookstore.localization.country;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/countries")
public class CountryController {
    private final CountryRepository countries;

    public CountryController(CountryRepository countries) {
        this.countries = countries;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateCountryRequest request) {
        var country = countries.save(Country.newCountry(request.name()));

        var location = URI.create("/countries/" + country.id());
        return ResponseEntity.created(location).build();
    }
}
