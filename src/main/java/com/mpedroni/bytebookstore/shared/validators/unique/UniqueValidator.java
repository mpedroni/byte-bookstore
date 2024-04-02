package com.mpedroni.bytebookstore.shared.validators.unique;

import com.mpedroni.bytebookstore.shared.validators.EntityTableNameResolver;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {
    private Class<?> entity;
    private String field;
    private final JdbcTemplate jdbc;

    public UniqueValidator(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        var table = EntityTableNameResolver.resolveFor(entity);

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
