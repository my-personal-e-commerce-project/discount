package microservice.cloud.discount.coupon.domain.entity;

import java.time.LocalDateTime;

import microservice.cloud.discount.coupon.domain.event.CouponIsNotPublic;
import microservice.cloud.discount.coupon.domain.event.CouponPublished;
import microservice.cloud.discount.coupon.domain.value_objects.CouponCode;
import microservice.cloud.discount.coupon.domain.value_objects.CouponVisibility;
import microservice.cloud.discount.shared.domain.entity.AggregateRoot;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public class Coupon extends AggregateRoot {
    private Id id;
    private Id discountId;
    private Integer sales;
    private CouponCode code;
    private CouponVisibility visibility;
    private Integer maxSales;
    private LocalDateTime expiredAt;

    public Coupon(Id id, Id discountId, CouponCode code, CouponVisibility visibility, Integer maxSales, LocalDateTime expiredAt) {
        if(id == null) {
            throw new IllegalArgumentException("Coupon id cannot be null");
        }
        
        this.id = id;
        this.discountId= discountId;
        this.code = code;
        this.visibility = visibility;
        this.maxSales = maxSales;
        this.expiredAt = expiredAt;
    }

    public static Coupon factoryCoupon(Id id, Id discountId, CouponCode code, CouponVisibility visibility, Integer maxSales, LocalDateTime expiredAt) {
        Coupon coupon = new Coupon(id, discountId, code, visibility, maxSales, expiredAt);

        coupon.sales = 0;
        
        if(visibility.equals(CouponVisibility.PUBLIC)) {
            coupon.publishEvent(
                new CouponPublished(id.value(), code.value(), id.value())
            );
        }

        return coupon;
    }

    public void update(Id discountId, CouponCode code, CouponVisibility visibility, Integer maxSales, LocalDateTime expiredAt) {
        this.discountId = discountId;
        this.code = code;
        this.maxSales = maxSales;
        this.expiredAt = expiredAt;

        if(this.visibility.equals(CouponVisibility.PUBLIC) && !visibility.equals(visibility)) {
            this.publishEvent(
                new CouponIsNotPublic(id.value())
            );
        }

        this.visibility = visibility;
    }

    public void incrementSales() {
        if(this.sales >= this.maxSales) {
            this.visibility = CouponVisibility.HIDDEN;

            this.publishEvent(
                new CouponIsNotPublic(id.value())
            );
           
            return;
        }

        this.sales = this.sales + 1;
    }

    public Integer sales() {
        return sales;
    }

    public Id id() {
        return id;
    }

    public Id discountId() {
        return discountId;
    }

    public CouponCode code() {
        return code;
    }

    public CouponVisibility visibility() {
        return visibility;
    }

    public Integer maxSales() {
        return maxSales;
    }

    public LocalDateTime expiredAt() {
        return expiredAt;
    }
}
