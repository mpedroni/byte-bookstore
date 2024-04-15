package com.mpedroni.bytebookstore.coupon;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupons")
public class CouponController {
    private final CouponRepository coupons;

    public CouponController(CouponRepository coupons) {
        this.coupons = coupons;
    }

    @PostMapping
    public String create(@Valid @RequestBody CreateCouponRequest request) {
        var coupon = coupons.save(Coupon.newCoupon(request.code(), request.discountInPercent(), request.expiresAt()));

        return coupon.toString();
    }
}
