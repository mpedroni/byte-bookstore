package com.mpedroni.bytebookstore.order;

import com.mpedroni.bytebookstore.coupon.CouponRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;

@Component
public class OrderAfterConvertCallback implements AfterConvertCallback<Order> {
    @Lazy
    @Autowired
    private CouponRepository coupons;

    @Override
    public Order onAfterConvert(@NotNull Order aggregate) {
        if (aggregate.couponId().isEmpty()) return aggregate;

        var couponId = aggregate.couponId().get();
        var coupon = coupons.findById(couponId).orElseThrow(() -> new IllegalStateException("Coupon with id %d not found".formatted(couponId)));

        aggregate.apply(coupon);
        return aggregate;
    }
}
