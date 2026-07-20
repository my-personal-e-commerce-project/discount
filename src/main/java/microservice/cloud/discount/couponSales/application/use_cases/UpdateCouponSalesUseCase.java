package microservice.cloud.discount.couponSales.application.use_cases;

import microservice.cloud.discount.coupon.domain.repository.CouponSalesRepository;
import microservice.cloud.discount.couponSales.domain.entity.CouponSales;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public class UpdateCouponSalesUseCase {
    private final CouponSalesRepository couponSalesRepository;
    
    public UpdateCouponSalesUseCase(CouponSalesRepository couponSalesRepository) {
        this.couponSalesRepository = couponSalesRepository;
    }

    public void execute(Id discountId, Integer maxSales) {
        couponSalesRepository.pessimisticUpdate(discountId, (CouponSales couponSales) -> { 
            couponSales.updateMaxSales(maxSales);
        });
    }
}
