package microservice.cloud.discount.couponSales.domain.entity;

import microservice.cloud.discount.shared.domain.entity.AggregateRoot;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public class CouponSales extends AggregateRoot {
    private Id id;
    private Id couponId;
    private Integer sales;

    public CouponSales(Id id, Id couponId, Integer sales) {
        this.id = id;
        this.couponId = couponId;
        this.sales = sales;
    }

    public static CouponSales factoryCouponSales(Id id, Id couponId) {
        return new CouponSales(id, couponId, 0);
    }

    public void incrementSales() {
        this.sales++;
    }

    public Id id() {
        return id;
    }

    public Id couponId() {
        return couponId;
    }

    public Integer sales() {
        return sales;
    }
}
