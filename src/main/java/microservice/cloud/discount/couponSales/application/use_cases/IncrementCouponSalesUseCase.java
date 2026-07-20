package microservice.cloud.discount.couponSales.application.use_cases;

import microservice.cloud.discount.coupon.application.use_cases.BlockCouponUseCase;
import microservice.cloud.discount.coupon.domain.entity.Coupon;
import microservice.cloud.discount.coupon.domain.repository.CouponRepository;
import microservice.cloud.discount.couponSales.domain.repository.CouponSalesRepository;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public class IncrementCouponSalesUseCase {
    private final CouponSalesRepository couponSalesRepository;
    private final BlockCouponUseCase blockedCouponUseCase;
    private final CouponRepository couponRepository;

    public IncrementCouponSalesUseCase(CouponSalesRepository couponSalesRepository, BlockCouponUseCase blockCouponUseCase, CouponRepository couponRepository) {
        this.couponSalesRepository = couponSalesRepository;
        this.blockedCouponUseCase = blockCouponUseCase;
        this.couponRepository = couponRepository;
    }

    public void execute(Id id) {
        Coupon coupon = couponRepository.findById(id);

        couponSalesRepository
            .pessimisticUpdate(id, (cs) -> {
                boolean isBlocked = coupon.maxSalesReached(cs.sales());

                if (isBlocked) {
                    blockedCouponUseCase.execute(id);
                    return;
                }

                cs.incrementSales();
            });
    }
}
