package com.mpedroni.bytebookstore.shared.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.stream.Stream;

public class ExistsByIdValidator implements ConstraintValidator<ExistsById, Long> {
    private final JdbcTemplate jdbc;

    public ExistsByIdValidator(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private Class<?> entity;

    @Override
    public void initialize(ExistsById constraintAnnotation) {
        entity = constraintAnnotation.entity();
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        var table = Stream.of(entity.getDeclaredAnnotations())
                .filter(a -> a instanceof Table)
                .map(a -> ((Table) a))
                .map(a -> !a.name().isBlank() ? a.name() : a.value())
                .findFirst()
                .orElseThrow(() -> new Error("The given entity must explicitly define a table name through the @Table annotation"));

        var query = "SELECT id FROM %s WHERE id = ? LIMIT 1".formatted(table);
        var result = jdbc.query(query, (rs, i) -> rs.getLong("id"), id);

        return !result.isEmpty();
    }
}
