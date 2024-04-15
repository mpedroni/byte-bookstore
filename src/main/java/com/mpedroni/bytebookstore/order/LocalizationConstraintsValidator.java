package com.mpedroni.bytebookstore.order;

import com.mpedroni.bytebookstore.localization.country.CountryRepository;
import com.mpedroni.bytebookstore.localization.country.exceptions.CountryNotFound;
import com.mpedroni.bytebookstore.localization.state.State;
import com.mpedroni.bytebookstore.localization.state.StateRepository;
import com.mpedroni.bytebookstore.localization.state.exceptions.StateNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LocalizationConstraintsValidator {
    private final CountryRepository countries;
    private final StateRepository states;

    public LocalizationConstraintsValidator(CountryRepository countries, StateRepository states) {
        this.countries = countries;
        this.states = states;
    }

    public void validate(Long countryId, Long stateId) {
        var country = countries.findById(countryId).orElseThrow(() -> new CountryNotFound("Country not found"));

        var hasStateParam = stateId != null;
        State state = hasStateParam ?
                states.findById(stateId)
                        .orElseThrow(() -> new StateNotFoundException("State not found"))
                : null;

        if (hasStateParam && !state.countryId().equals(country.id())) {
            throw new IllegalArgumentException("State does not belong to the country");
        }

        if (!hasStateParam) {
            var countryHasStates = states.existsByCountryId(country.id());

            if (countryHasStates) {
                throw new IllegalArgumentException("State is required because the country has registered states");
            }
        }
    }
}
