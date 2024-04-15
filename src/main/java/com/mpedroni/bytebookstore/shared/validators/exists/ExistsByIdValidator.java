package com.mpedroni.bytebookstore.shared.validators.exists;

import com.mpedroni.bytebookstore.shared.validators.EntityTableNameResolver;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.jdbc.core.JdbcTemplate;


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
        if (id == null) {
            return true;
        }

        var table = EntityTableNameResolver.resolveFor(entity);

        var query = "SELECT id FROM %s WHERE id = ? LIMIT 1".formatted(table);
        var result = jdbc.query(query, (rs, i) -> rs.getLong("id"), id);

        return !result.isEmpty();
    }
}
