package microservice.cloud.discount.couponSales.domain.entity;

import microservice.cloud.discount.shared.domain.entity.AggregateRoot;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public class CouponSales extends AggregateRoot {

    private Id id;
    private Integer sales;

    public CouponSales(Id id, Integer sales) {
        this.id = id;
        this.sales = sales;
    }

    public static CouponSales factoryCouponSales(Id id) {
        return new CouponSales(id, 0);
    }

    public void incrementSales() {
        this.sales++;
    }

    public Id id() {
        return id;
    }

    public Integer sales() {
        return sales;
    }
}
