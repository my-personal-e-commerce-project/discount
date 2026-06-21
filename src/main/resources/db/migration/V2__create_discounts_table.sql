CREATE TABLE discounts (
    id VARCHAR(255) PRIMARY KEY UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL,
    discount_type VARCHAR(255) NOT NULL,
    decrement_value DOUBLE PRECISION DEFAULT NULL,
    percentage_value DOUBLE PRECISION DEFAULT NULL,

    global_categories BOOLEAN DEFAULT NULL,

    min_price DOUBLE PRECISION DEFAULT NULL,
    max_price DOUBLE PRECISION DEFAULT NULL,
    
    min_stock int DEFAULT NULL,
    max_stock int DEFAULT NULL,

    expired_at timestamp DEFAULT now()
);

CREATE TABLE discount_categories (
    discount_id VARCHAR(255) NOT NULL,
    category_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (discount_id, category_id),
    CONSTRAINT fk_discount FOREIGN KEY (discount_id) REFERENCES discounts (id) ON DELETE CASCADE
);

CREATE INDEX idx_category_id ON discount_categories (category_id);



CREATE OR REPLACE FUNCTION fn_build_discount_outbox()
RETURNS TRIGGER AS $$
DECLARE
    v_payload JSONB;
BEGIN
    IF (TG_OP = 'DELETE') THEN
        INSERT INTO outbox (aggregate_type, aggregate_id, type, payload, created_at)
        VALUES ('discount', OLD.id, 'DISCOUNT_DELETED', jsonb_build_object('id', OLD.id, 'deleted', true), now());
    ELSE
        SELECT jsonb_build_object(
            'id', NEW.id,
            'name', NEW.name,
            'slug', NEW.slug,
            'discountType', NEW.discount_type,
            'decrementValue', NEW.decrement_value,
            'percentageValue', NEW.percentage_value,
            'globalCategories', NEW.global_categories,
            'minPrice', NEW.min_price,
            'maxPrice', NEW.max_price,
            'minStock', NEW.min_stock,
            'maxStock', NEW.max_stock,
            'expiredAt', NEW.expired_at,
            'allowedCategories', (
                SELECT coalesce(
                    jsonb_agg(pc.category_id), 
                    '[]'::jsonb
                )
                FROM discount_categories pc
                WHERE pc.discount_id = NEW.id
            )
        ) INTO v_payload;

        IF (TG_OP = 'INSERT') THEN
            INSERT INTO outbox (aggregate_type, aggregate_id, type, payload, created_at)
            VALUES ('discount', NEW.id, 'DISCOUNT_CREATED', v_payload, now());
        ELSE
            INSERT INTO outbox (aggregate_type, aggregate_id, type, payload, created_at)
            VALUES ('discount', NEW.id, 'DISCOUNT_UPDATED', v_payload, now());
        END IF;
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;



CREATE TRIGGER trg_discount_changes
AFTER INSERT OR UPDATE OR DELETE ON discounts
FOR EACH ROW EXECUTE FUNCTION fn_build_discount_outbox();



CREATE OR REPLACE FUNCTION fn_trigger_discount_refresh_from_cat()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE discounts SET id = id WHERE id = COALESCE(NEW.discount_id, OLD.discount_id);
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_refresh_from_disc_cat AFTER INSERT OR UPDATE OR DELETE ON discount_categories FOR EACH ROW EXECUTE FUNCTION fn_trigger_discount_refresh_from_cat();
