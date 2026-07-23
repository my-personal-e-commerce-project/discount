CREATE TABLE coupons_sales (
    id VARCHAR(255) PRIMARY KEY UNIQUE NOT NULL,
    coupon_id VARCHAR(255) UNIQUE NOT NULL,
    sales int DEFAULT 0 NOT NULL,

    CONSTRAINT fk_coupon_id
    FOREIGN KEY (coupon_id)
    REFERENCES coupons(id)
    ON DELETE CASCADE

)
