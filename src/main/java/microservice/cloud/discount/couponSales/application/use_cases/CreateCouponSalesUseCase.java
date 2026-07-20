package microservice.cloud.discount.couponSales.application.use_cases;

import microservice.cloud.discount.coupon.domain.repository.CouponSalesRepository;
import microservice.cloud.discount.couponSales.domain.entity.CouponSales;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public class CreateCouponSalesUseCase {
    private final CouponSalesRepository couponSalesRepository;

    public CreateCouponSalesUseCase(CouponSalesRepository couponSalesRepository) {
        this.couponSalesRepository = couponSalesRepository;
    }
    
    public void execute(Id id, Id couponId, Integer maxSales) {
        CouponSales couponSales = CouponSales.factoryCouponSales(id, couponId, maxSales);

        couponSalesRepository.create(couponSales);
    }
}
