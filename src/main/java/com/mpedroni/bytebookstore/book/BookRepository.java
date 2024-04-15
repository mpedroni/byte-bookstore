package com.mpedroni.bytebookstore.book;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    @Query(value = "SELECT * FROM books WHERE id IN (:ids)")
    List<Book> findAllByIds(List<Long> ids);
}
