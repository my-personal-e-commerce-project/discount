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
    private CouponCode code;
    private Integer maxSales;
    private CouponVisibility visibility;
    private LocalDateTime expiredAt;

    public Coupon(Id id, Id discountId, CouponCode code, Integer maxSales, CouponVisibility visibility, LocalDateTime expiredAt) {
        if(id == null) {
            throw new IllegalArgumentException("Coupon id cannot be null");
        }
       
        if(discountId == null) {
            throw new IllegalArgumentException("Discount id cannot be null");
        }

        if(code == null) {
            throw new IllegalArgumentException("Coupon code cannot be null");
        } 

        this.id = id;
        this.discountId= discountId;
        this.code = code;
        this.maxSales = maxSales;
        this.visibility = visibility;
        this.expiredAt = expiredAt;
    }

    public static Coupon factoryCoupon(Id id, Id discountId, CouponCode code, Integer maxSales, CouponVisibility visibility, LocalDateTime expiredAt) {
        Coupon coupon = new Coupon(id, discountId, code, maxSales, visibility, expiredAt);
        
        if(visibility.equals(CouponVisibility.PUBLIC)) {
            coupon.publishEvent(
                new CouponPublished(id.value(), code.value(), discountId.value(), expiredAt)
            );
        }

        return coupon;
    }

    public boolean maxSalesReached(Integer sales) {
        return this.maxSales >= sales;
    }

    public void block() {
        this.visibility = CouponVisibility.BLOCKED;
        this.publishEvent(
            new CouponIsNotPublic(id.value(), code.value(), discountId.value(), expiredAt)
        );
    }

    public void update(Id discountId, CouponCode code, Integer maxSales, CouponVisibility visibility, LocalDateTime expiredAt) {
        this.discountId = discountId;
        this.code = code;
        this.maxSales = maxSales;
        this.expiredAt = expiredAt;

        if(this.visibility.equals(CouponVisibility.PUBLIC) && !this.visibility.equals(visibility)) {
            this.publishEvent(
                new CouponIsNotPublic(id.value(), code.value(), discountId.value(), expiredAt)
            );
        }

        this.visibility = visibility;
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

    public Integer maxSales() {
        return maxSales;
    }

    public CouponVisibility visibility() {
        return visibility;
    }

    public LocalDateTime expiredAt() {
        return expiredAt;
    }
}
