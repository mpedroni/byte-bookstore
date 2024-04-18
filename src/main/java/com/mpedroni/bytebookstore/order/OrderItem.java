package com.mpedroni.bytebookstore.order;

import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Objects;

@Table("order_items")
public class OrderItem {
    private Long bookId;
    private Integer quantity;

    private BigDecimal unitPrice;

    @Deprecated
    protected OrderItem() {}

    private OrderItem(
            Long bookId,
            Integer quantity,
            BigDecimal unitPrice
    ) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public static OrderItem with(Long bookId, Integer quantity, BigDecimal unitPrice) {
        return new OrderItem(bookId, quantity, unitPrice);
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (OrderItem) obj;
        return Objects.equals(this.bookId, that.bookId) &&
                Objects.equals(this.quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, quantity);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "bookId=" + bookId + ", " +
                "quantity=" + quantity + ", " +
                "unitPrice=" + unitPrice + ", " +
                '}';
    }

}
