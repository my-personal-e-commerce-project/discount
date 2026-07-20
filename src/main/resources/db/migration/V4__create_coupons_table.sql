CREATE TABLE IF NOT EXISTS coupons (
    id VARCHAR(255) PRIMARY KEY UNIQUE NOT NULL,
    discount_id VARCHAR(255) NOT NULL,
    coupons_sales_id VARCHAR(255) NOT NULL,
    sales int DEFAULT 0 NOT NULL,
    max_sales int DEFAULT NULL,
    code VARCHAR(255) NOT NULL UNIQUE,
    visibility VARCHAR(255) DEFAULT 'PRIVATE' NOT NULL,
    expired_at timestamp DEFAULT now(),
    
    CONSTRAINT fk_discount_id
    FOREIGN KEY (discount_id)
    REFERENCES discounts(id),

    CONSTRAINT fk_coupons_sales_id
    FOREIGN KEY (coupons_sales_id)
    REFERENCES coupons_sales(id)
    ON DELETE SET NULL
);
