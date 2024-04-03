package com.mpedroni.bytebookstore.localization.state;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StateRepository extends CrudRepository<State, Long> {

    @Query("SELECT * FROM states WHERE name = :name AND country_id = :countryId")
    Optional<State> findByNameAndCountryId(String name, Long countryId);
}
