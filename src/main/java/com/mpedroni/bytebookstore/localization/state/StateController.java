package com.mpedroni.bytebookstore.localization.state;

import com.mpedroni.bytebookstore.localization.country.CountryRepository;
import com.mpedroni.bytebookstore.localization.country.exceptions.CountryNotFound;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/states")
public class StateController {
    private final StateRepository states;
    private final CountryRepository countries;

    public StateController(StateRepository states, CountryRepository countries) {
        this.states = states;
        this.countries = countries;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateStateRequest request) {
        var country = countries.findById(request.countryId()).orElseThrow(() -> new CountryNotFound("Country not found"));
        var state = states.save(State.newState(request.name(), country));

        var location = URI.create("/states/" + state.id());
        return ResponseEntity.created(location).build();
    }
}
