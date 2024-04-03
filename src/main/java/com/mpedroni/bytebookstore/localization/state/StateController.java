package com.mpedroni.bytebookstore.localization.state;

import com.mpedroni.bytebookstore.localization.country.CountryRepository;
import com.mpedroni.bytebookstore.localization.country.exceptions.CountryNotFound;
import com.mpedroni.bytebookstore.localization.state.exceptions.StateAlreadyExists;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/states")
// class's intrinsic load: 6 (not considering custom exceptions)
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

        states.findByNameAndCountryId(request.name(), country.id()).ifPresent(state -> {
            throw new StateAlreadyExists("State %s already registered in country %s".formatted(state.name(), country.name()));
        });

        var state = states.save(State.newState(request.name(), country));

        var location = URI.create("/states/" + state.id());
        return ResponseEntity.created(location).build();
    }
}
