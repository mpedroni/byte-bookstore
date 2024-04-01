package com.mpedroni.bytebookstore.category;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("categories")
public class Category {
    @Id
    private final Long id;
    private final String name;

    private Category(Long id, String name) {
        this.id = id;
        this.name = name;

        selfValidate();
    }


    /**
     * Factory method to create a new Category instance
     *
     * @param name The name of the category. Must not be null or empty and cannot be duplicated
     * @return A new Category instance
     */
    public static Category newCategory(@NotEmpty String name) {
        return new Category(null, name);
    }

    private void selfValidate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("'name' cannot be null or empty");
        }
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return "Category{name='" + name + "'}";
    }
}
