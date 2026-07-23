package microservice.cloud.discount.couponSales.domain.repository;

import java.util.function.Consumer;

import microservice.cloud.discount.couponSales.domain.entity.CouponSales;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public interface CouponSalesRepository {
    public CouponSales findByCouponId(Id couponId);
    public CouponSales pessimisticUpdate(Id discountId, Consumer<CouponSales> function);
}
