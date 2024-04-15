CREATE TABLE coupons (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(255) NOT NULL UNIQUE,
    discount_in_percent INT NOT NULL,
    expires_at DATETIME NOT NULL
);

CREATE INDEX idx_coupons_code ON coupons (code);