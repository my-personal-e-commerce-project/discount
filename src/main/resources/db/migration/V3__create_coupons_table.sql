CREATE TABLE IF NOT EXISTS coupons (
    id VARCHAR(255) PRIMARY KEY UNIQUE NOT NULL,
    discount_id VARCHAR(255) NOT NULL,
    sales int DEFAULT 0 NOT NULL,
    max_sales int DEFAULT 1 NOT NULL,
    code VARCHAR(255) NOT NULL,
    visibility VARCHAR(255) DEFAULT 'PRIVATE' NOT NULL,
    expired_at timestamp DEFAULT now(),
    
    CONSTRAINT fk_discount_id
    FOREIGN KEY (discount_id)
    REFERENCES discounts(id)
);
