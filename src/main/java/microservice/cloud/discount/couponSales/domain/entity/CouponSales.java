package microservice.cloud.discount.couponSales.domain.entity;

import microservice.cloud.discount.couponSales.domain.event.CouponSalesLimitReached;
import microservice.cloud.discount.shared.domain.entity.AggregateRoot;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public class CouponSales extends AggregateRoot {

    private Id id;
    private Id couponId;
    private Integer sales;
    private Integer maxSales;

    public CouponSales(Id id, Id couponId, Integer sales, Integer maxSales) {
        this.id = id;
        this.couponId = couponId;
        this.sales = sales;
        this.maxSales = maxSales;
    }

    public static CouponSales factoryCouponSales(Id id, Id couponId, Integer maxSales) {
        return new CouponSales(id, couponId, 0, maxSales);
    }

    public void incrementSales() {
        this.sales = this.sales + 1;

        if(this.sales >= this.maxSales) {
            this.publishEvent(
                new CouponSalesLimitReached(id.value())
            );
        }
    }

    public void updateMaxSales(Integer maxSales) {
        this.maxSales = maxSales;
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

    public Integer maxSales() {
        return maxSales;
    }
}
