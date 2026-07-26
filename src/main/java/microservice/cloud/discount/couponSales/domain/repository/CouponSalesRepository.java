package microservice.cloud.discount.couponSales.domain.repository;

import microservice.cloud.discount.couponSales.domain.entity.CouponSales;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public interface CouponSalesRepository {
    public CouponSales findByCouponId(Id couponId);
    public void updateIfExists(Id id, CouponSales couponSales);
}
