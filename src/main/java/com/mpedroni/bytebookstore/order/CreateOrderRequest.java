package com.mpedroni.bytebookstore.order;

import com.mpedroni.bytebookstore.book.Book;
import com.mpedroni.bytebookstore.localization.country.Country;
import com.mpedroni.bytebookstore.localization.state.State;
import com.mpedroni.bytebookstore.shared.validators.document.Document;
import com.mpedroni.bytebookstore.shared.validators.exists.ExistsById;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record CreateOrderRequest(
    @NotBlank
    @Email(regexp = ".+@.+\\..+")
    String email,

    @NotBlank
    String firstName,

    @NotBlank
    String lastName,

    @NotBlank
    @Document
    String document,

    @NotBlank
    String address,

    @NotBlank
    String complement,

    @NotBlank
    String city,

    @NotNull
    @ExistsById(entity = Country.class)
    Long countryId,

    @ExistsById(entity = State.class)
    Long stateId,

    @NotBlank
    String phone,

    @NotBlank
    String cep,

    @NotNull
    @Valid
    Chart chart
) {

    record Chart(
            @NotNull
            @Positive
            BigDecimal total,

            @NotEmpty
            List<@Valid ChartItem> items

    ) {
        public List<Long> bookIds() {
            return items.stream().map(ChartItem::bookId).toList();
        }

        public Set<OrderItem> toDomainItems(List<Book> orderedBooks) {
            var prices = orderedBooks.stream().collect(Collectors.toMap(Book::id, Book::price));

            return this.items.stream()
                    .map(item -> OrderItem.with(item.bookId(), item.quantity(), prices.get(item.bookId())))
                    .collect(Collectors.toSet());
        }
    }

    record ChartItem(
            @NotNull
            @ExistsById(entity = Book.class)
            Long bookId,

            @NotNull
            @Positive
            Integer quantity
    ) {
    }
}
