package com.mpedroni.bytebookstore.order;

import com.mpedroni.bytebookstore.book.Book;
import com.mpedroni.bytebookstore.book.BookRepository;
import com.mpedroni.bytebookstore.localization.country.CountryRepository;
import com.mpedroni.bytebookstore.localization.country.exceptions.CountryNotFound;
import com.mpedroni.bytebookstore.localization.state.State;
import com.mpedroni.bytebookstore.localization.state.StateRepository;
import com.mpedroni.bytebookstore.localization.state.exceptions.StateNotFoundException;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
// class's intrinsic load: 18
public class OrderController {
    // +1
    private final CountryRepository countries;
    // +1
    private final StateRepository states;
    // +1
    private final BookRepository books;
    // +1
    private final OrderRepository orders;

    public OrderController(CountryRepository countries, StateRepository states, BookRepository books, OrderRepository orders) {
        this.countries = countries;
        this.states = states;
        this.books = books;
        this.orders = orders;
    }

    @PostMapping
    @Transactional
    // +1
    public void create(@Valid @RequestBody CreateOrderRequest request) {
        // +1
        var country = countries.findById(request.countryId()).orElseThrow(() -> new CountryNotFound("Country not found"));

        var hasStateParam = request.stateId() != null;
        State state = hasStateParam ?
                states.findById(request.stateId())
                        // +1
                        .orElseThrow(() -> new StateNotFoundException("State not found"))
                : null;

        // +1
        if (hasStateParam && !state.countryId().equals(country.id())) {
            throw new IllegalArgumentException("State does not belong to the country");
        }

        // +1
        if (!hasStateParam) {
            var countryHasStates = states.existsByCountryId(country.id());

            // +1
            if (countryHasStates) {
                throw new IllegalArgumentException("State is required because the country has registered states");
            }
        }

        var chart = request.chart();

        // +1
        var bookIds = chart.items().stream().map(item -> item.bookId()).toList();

        var orderedBooks = books.findAllByIds(bookIds);

        // +1
        if (orderedBooks.size() != bookIds.size()) {
            // +1
            var orderedBookIds = orderedBooks.stream().map(Book::id).toList();

            // +1
            var notFound = bookIds.stream().filter(id -> !orderedBookIds.contains(id)).toList();
            throw new IllegalArgumentException("Books not found ids: %s".formatted(notFound));
        }
        
        var items = chart.items().stream()
                // +1
                .map(item -> {
                    var book = orderedBooks.stream()
                            // +1
                            .filter(b -> b.id().equals(item.bookId()))
                            .findFirst()
                            .orElseThrow();

                    return OrderItem.with(book.id(), item.quantity(), book.price());
                })
                .collect(Collectors.toSet());

        // +1
        var order = Order.newOrder(
                request.email(),
                request.firstName(),
                request.lastName(),
                request.document(),
                request.address(),
                request.complement(),
                request.city(),
                country,
                state,
                request.phone(),
                request.cep(),
                items
        );

        // +1
        if (order.total().compareTo(chart.total()) != 0) {
            throw new IllegalArgumentException("Cart total value does not match the sum of the items");
        }

        orders.save(order);
    }
}
