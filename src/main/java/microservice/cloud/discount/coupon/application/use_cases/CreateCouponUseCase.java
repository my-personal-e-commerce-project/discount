package microservice.cloud.discount.coupon.application.use_cases;

import java.time.LocalDateTime;

import microservice.cloud.discount.coupon.domain.entity.Coupon;
import microservice.cloud.discount.coupon.domain.repository.CouponRepository;
import microservice.cloud.discount.coupon.domain.value_objects.CouponCode;
import microservice.cloud.discount.coupon.domain.value_objects.CouponVisibility;
import microservice.cloud.discount.shared.application.ports.out.EventPublisher;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public class CreateCouponUseCase {
    private final EventPublisher eventPublisher;
    private final CouponRepository couponRepository;

    public CreateCouponUseCase(EventPublisher eventPublisher, CouponRepository couponRepository) {
        this.eventPublisher = eventPublisher;
        this.couponRepository = couponRepository;
    }

    public void execute(Id id, Id discountId, CouponCode code,  CouponVisibility visibility, LocalDateTime expiredAt) {
        Coupon coupon = Coupon.factoryCoupon(id, discountId, code, visibility, expiredAt);

        couponRepository.create(coupon);

        if(coupon != null && !coupon.getEvents().isEmpty()) {
            eventPublisher.publish(coupon.getEvents());
        }
    }
}
