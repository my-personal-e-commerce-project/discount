CREATE TABLE IF NOT EXISTS coupons (
    id VARCHAR(255) PRIMARY KEY UNIQUE NOT NULL,
    discount_id VARCHAR(255) NOT NULL,
    sales int DEFAULT 0 NOT NULL,
    max_sales int DEFAULT NULL,
    code VARCHAR(255) NOT NULL UNIQUE,
    visibility VARCHAR(255) DEFAULT 'PRIVATE' NOT NULL,
    expired_at timestamp DEFAULT now(),
    
    CONSTRAINT fk_discount_id
    FOREIGN KEY (discount_id)
    REFERENCES discounts(id)
);

CREATE OR REPLACE FUNCTION fn_build_coupon_outbox()
RETURNS TRIGGER AS $$
DECLARE
    v_payload JSONB;
BEGIN
    IF (TG_OP = 'DELETE') THEN
        SELECT jsonb_build_object(
            'id', NEW.id,
            'discountId', NEW.discount_id,
            'code', NEW.code,
            'expiredAt', NEW.expired_at
        ) INTO v_payload;

        INSERT INTO outbox (aggregate_type, aggregate_id, type, payload, created_at)
        VALUES ('coupons', OLD.id, 'COUPON_DELETED', v_payload, now());
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;



CREATE TRIGGER trg_coupon_changes
AFTER DELETE ON coupons
FOR EACH ROW EXECUTE FUNCTION fn_build_coupon_outbox();
