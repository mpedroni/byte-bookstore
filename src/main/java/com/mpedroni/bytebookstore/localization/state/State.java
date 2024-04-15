package com.mpedroni.bytebookstore.localization.state;

import com.mpedroni.bytebookstore.localization.country.Country;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("states")
public class State {
    @Id
    private Long id;

    private String name;

    @Column("country_id")
    private AggregateReference<Country, Long> country;

    @Deprecated
    public State() {
    }

    private State(Long id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = AggregateReference.to(country.id());
    }

    public static State newState(String name, Country country) {
        return new State(null, name, country);
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Long countryId() {
        return country.getId();
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country +
                '}';
    }
}
