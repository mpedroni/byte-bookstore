ALTER TABLE orders ADD COLUMN coupon_id INT;
ALTER TABLE orders ADD FOREIGN KEY (coupon_id) REFERENCES coupons(id) ON DELETE SET NULL ON UPDATE CASCADE;