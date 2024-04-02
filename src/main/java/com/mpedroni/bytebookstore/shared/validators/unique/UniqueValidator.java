package com.mpedroni.bytebookstore.shared.validators.unique;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.stream.Stream;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {
    private Class<?> entity;
    private String field;
    private final JdbcTemplate jdbc;

    public UniqueValidator(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        var table = Stream.of(entity.getDeclaredAnnotations())
                .filter(a -> a instanceof Table)
                .map(a -> ((Table) a))
                .map(a -> !a.name().isBlank() ? a.name() : a.value())
                .findFirst()
                .orElseThrow(() -> new Error("The given entity must explicitly define a table name through the @Table annotation"));

        var query = "SELECT id FROM %s WHERE %s = ? LIMIT 1".formatted(table, field);
        var result = jdbc.query(query, (rs, i) -> rs.getLong("id"), value);

        return result.isEmpty();
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.entity = constraintAnnotation.entity();
        this.field = constraintAnnotation.field();
    }
}
