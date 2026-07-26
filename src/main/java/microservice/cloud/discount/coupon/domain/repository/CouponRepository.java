package microservice.cloud.discount.coupon.domain.repository;

import microservice.cloud.discount.coupon.domain.entity.Coupon;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public interface CouponRepository {

    public Coupon findById(Id id);
    public void createCouponAndSales(Coupon coupon, Id couponSalesId);
    public void updateIfExists(Coupon coupon);
    public void deleteIfExists(Id id);
}
