package com.mpedroni.bytebookstore.order;

import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Objects;

@Table("order_items")
public class OrderItem {
    private final Long bookId;
    private final Integer quantity;

    private final BigDecimal unitPrice;

    @Transient
    private Order order;

    private OrderItem(
            Long bookId,
            Integer quantity,
            BigDecimal unitPrice,
            Order order

    ) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.order = order;
    }

    public static OrderItem with(Long bookId, Integer quantity, BigDecimal unitPrice) {
        return new OrderItem(bookId, quantity, unitPrice, null);
    }

    public Long bookId() {
        return bookId;
    }

    public Integer quantity() {
        return quantity;
    }

    public BigDecimal unitPrice() {
        return unitPrice;
    }

    @Transient
    public Order order() {
        return order;
    }

    public void withOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (OrderItem) obj;
        return Objects.equals(this.bookId, that.bookId) &&
                Objects.equals(this.quantity, that.quantity) &&
                Objects.equals(this.order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, quantity, order);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "bookId=" + bookId + ", " +
                "quantity=" + quantity + ", " +
                "unitPrice=" + unitPrice + ", " +
                "orderId=" + order.id() + '}';
    }

}
