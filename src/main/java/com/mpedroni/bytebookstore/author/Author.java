package com.mpedroni.bytebookstore.author;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("authors")
public class Author {
    @Id
    private Long id;
    private String name;
    private String email;
    private String description;
    private LocalDateTime createdAt;

    @Deprecated
    public Author() {}

    private Author(Long id, String name, String email, String description, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.description = description;
        this.createdAt = createdAt;

        selfValidate();
    }


    /**
     * Factory method to create a new Author instance
     * @param name The name of the author. Must not be null or empty
     * @param email The email of the author. Must not be null or empty and must be a well-formed email address. Cannot be duplicated
     * @param description A brief description of the author. Must not be null or empty and must have at most 400 characters
     * @return A new Author instance
     */
    public static Author newAuthor(String name, String email, String description) {
        return new Author(null, name, email, description, LocalDateTime.now());
    }

    private void selfValidate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("'name' cannot be null or empty");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("'email' cannot be null or empty");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("'description' cannot be null or empty");
        }

        if (description.length() > 400) {
            throw new IllegalArgumentException("'description' must have at most 400 characters");
        }

        if (!email.matches("^(.+)@(.+)$")) {
            throw new IllegalArgumentException("'email' should be a well-formed email address");
        }
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String email() {
        return email;
    }

    public String description() {
        return description;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
