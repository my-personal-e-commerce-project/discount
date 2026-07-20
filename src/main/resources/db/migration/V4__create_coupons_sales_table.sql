CREATE TABLE coupons_sales (
    id VARCHAR(255) PRIMARY KEY UNIQUE NOT NULL,
    coupon_id VARCHAR(255) NOT NULL,
    sales int DEFAULT 0 NOT NULL,
    max_sales int DEFAULT NULL,
    FOREIGN KEY (coupon_id) REFERENCES coupons(id)
);
