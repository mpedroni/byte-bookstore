package com.mpedroni.bytebookstore.order;

import java.math.BigDecimal;
import java.util.List;

public record FindOrderResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String document,
        String address,
        String complement,
        String city,
        Long countryId,
        Long stateId,
        String phone,
        String cep,
        Boolean hasCoupon,
        BigDecimal total,
        BigDecimal totalWithoutDiscount,
        List<FindOrderResponseOrderItem> items) {

    public static FindOrderResponse from(Order order) {
        return new FindOrderResponse(
                order.id(),
                order.email(),
                order.firstName(),
                order.lastName(),
                order.document(),
                order.address(),
                order.complement(),
                order.city(),
                order.countryId(),
                order.stateId(),
                order.phone(),
                order.cep(),
                order.couponId().isPresent(),
                order.total(),
                order.totalWithoutDiscount(),
                order.items().stream().map(FindOrderResponseOrderItem::from).toList()
        );
    }

    record FindOrderResponseOrderItem(Long bookId, Integer quantity, BigDecimal unitPrice) {
        public static FindOrderResponseOrderItem from(OrderItem item) {
            return new FindOrderResponseOrderItem(item.bookId(), item.quantity(), item.unitPrice());
        }
    }
}
