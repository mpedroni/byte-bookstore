package com.mpedroni.bytebookstore.coupon;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("coupons")
public class Coupon {
    @Id
    private final Long id;
    private final String code;
    private final Integer discountInPercent;
    private final LocalDateTime expiresAt;

    public Coupon(Long id, String code, Integer discountInPercent, LocalDateTime expiresAt) {
        this.id = id;
        this.code = code;
        this.discountInPercent = discountInPercent;
        this.expiresAt = expiresAt;
    }

    public static Coupon newCoupon(String code, Integer discountInPercent, LocalDateTime expirationDate) {
        return new Coupon(null, code, discountInPercent, expirationDate);
    }

    public Long id() {
        return id;
    }

    public String code() {
        return code;
    }

    public Integer discountInPercent() {
        return discountInPercent;
    }

    public LocalDateTime expiresAt() {
        return expiresAt;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id + ", " +
                "code='" + code + '\'' +
                ", discountInPercent=" + discountInPercent +
                ", expirationDate=" + expiresAt +
                '}';
    }
}
