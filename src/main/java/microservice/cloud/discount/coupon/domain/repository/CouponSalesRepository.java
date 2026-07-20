package microservice.cloud.discount.coupon.domain.repository;

import java.util.function.Consumer;

import microservice.cloud.discount.couponSales.domain.entity.CouponSales;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public interface CouponSalesRepository {
    public CouponSales pessimisticUpdate(Id discountId, Consumer<CouponSales> function);
    public void create(CouponSales couponSales);
}
