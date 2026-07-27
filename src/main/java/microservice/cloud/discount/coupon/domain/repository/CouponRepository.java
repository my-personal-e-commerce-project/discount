package microservice.cloud.discount.coupon.domain.repository;

import java.util.function.Consumer;

import microservice.cloud.discount.coupon.domain.entity.Coupon;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public interface CouponRepository {

    public Coupon findById(Id id);
    public void createCouponAndSales(Coupon coupon, Id couponSalesId);
    public Coupon updateIfExists(Id id, Consumer<Coupon> function);
    public void deleteIfExists(Id id);
}
