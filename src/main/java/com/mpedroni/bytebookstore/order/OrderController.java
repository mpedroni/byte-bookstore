package com.mpedroni.bytebookstore.order;

import com.mpedroni.bytebookstore.book.Book;
import com.mpedroni.bytebookstore.book.BookRepository;
import com.mpedroni.bytebookstore.coupon.CouponRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final LocalizationConstraintsValidator locationValidator;
    private final BookRepository books;
    private final OrderRepository orders;
    private final CouponRepository coupons;

    public OrderController(LocalizationConstraintsValidator locationValidator, BookRepository books, OrderRepository orders, CouponRepository coupons) {
        this.locationValidator = locationValidator;
        this.books = books;
        this.orders = orders;
        this.coupons = coupons;
    }

    @PostMapping
    @Transactional
    public void create(@Valid @RequestBody CreateOrderRequest request) {
        locationValidator.validate(request.countryId(), request.stateId());

        var chart = request.chart();
        var bookIds = chart.bookIds();

        var orderedBooks = books.findAllByIds(bookIds);
        validateBooks(bookIds, orderedBooks);
        
        var order = Order.newOrder(
                request.email(),
                request.firstName(),
                request.lastName(),
                request.document(),
                request.address(),
                request.complement(),
                request.city(),
                request.countryId(),
                request.stateId(),
                request.phone(),
                request.cep(),
                request.chart().total(),
                request.chart().toDomainItems(orderedBooks)
        );

        if (request.couponCode() != null) {
            var coupon = coupons.findByCode(request.couponCode()).orElseThrow(() -> new IllegalArgumentException("Coupon not found"));

            if (coupon.expiresAt().isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("Coupon expired");
            }

            order.apply(coupon);
        }

        orders.save(order);
    }

    private void validateBooks(List<Long> orderedIds, List<Book> existentBooks) {
        
        if (existentBooks.size() != orderedIds.size()) {
            
            var orderedBookIds = existentBooks.stream().map(Book::id).toList();

            
            var notFound = existentBooks.stream().filter(id -> !orderedBookIds.contains(id)).toList();
            throw new IllegalArgumentException("Books not found ids: %s".formatted(notFound));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindOrderResponse> find(@PathVariable Long id) {
        var order = orders.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));

        return ResponseEntity.ok(FindOrderResponse.from(order));
    }
}
