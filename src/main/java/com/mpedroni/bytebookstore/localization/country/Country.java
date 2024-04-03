package com.mpedroni.bytebookstore.localization.country;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("countries")
public class Country {
    @Id
    private Long id;
    private String name;

    @Deprecated
    public Country() {
    }

    private Country(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Country newCountry(String name) {
        return new Country(null, name);
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
