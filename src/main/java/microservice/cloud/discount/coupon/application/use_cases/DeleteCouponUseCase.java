package microservice.cloud.discount.coupon.application.use_cases;

import microservice.cloud.discount.coupon.domain.repository.CouponRepository;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public class DeleteCouponUseCase {

    private final CouponRepository couponRepository;

    public DeleteCouponUseCase(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public void execute(Id id) {
        couponRepository.delete(id);
    }
}
