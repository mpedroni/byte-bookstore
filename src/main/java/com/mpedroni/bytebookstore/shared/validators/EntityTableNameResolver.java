package com.mpedroni.bytebookstore.shared.validators;

import org.springframework.data.relational.core.mapping.Table;

import java.util.stream.Stream;

public class EntityTableNameResolver {
    public static String resolveFor(Class<?> entity) {
        return Stream.of(entity.getDeclaredAnnotations())
                .filter(a -> a instanceof Table)
                .map(a -> ((Table) a))
                .map(a -> !a.name().isBlank() ? a.name() : a.value())
                .findFirst()
                .orElseThrow(() -> new Error("The given entity must explicitly define a table name through the @Table annotation"));
    }
}
