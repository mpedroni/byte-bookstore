package com.mpedroni.bytebookstore.coupon;

import com.mpedroni.bytebookstore.shared.validators.unique.Unique;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CreateCouponRequest(
        @NotBlank
        @Unique(entity = Coupon.class, field = "code")
        String code,

        @NotNull
        @Positive
        @Max(100)
        Integer discountInPercent,

        @NotNull
        @Future
        LocalDateTime expiresAt
) {
}
