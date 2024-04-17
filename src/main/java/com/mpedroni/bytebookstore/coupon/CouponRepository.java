package com.mpedroni.bytebookstore.coupon;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CouponRepository extends CrudRepository<Coupon, Long> {
    Optional<Coupon> findByCode(String code);
}
