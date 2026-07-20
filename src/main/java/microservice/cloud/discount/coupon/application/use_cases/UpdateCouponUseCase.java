package microservice.cloud.discount.coupon.application.use_cases;

import java.time.LocalDateTime;

import microservice.cloud.discount.coupon.domain.entity.Coupon;
import microservice.cloud.discount.coupon.domain.repository.CouponRepository;
import microservice.cloud.discount.coupon.domain.value_objects.CouponCode;
import microservice.cloud.discount.coupon.domain.value_objects.CouponVisibility;
import microservice.cloud.discount.shared.application.ports.out.EventPublisher;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public class UpdateCouponUseCase {
    private final CouponRepository couponRepository;
    private final EventPublisher eventPublisher;

    public UpdateCouponUseCase(
        CouponRepository couponRepository,
        EventPublisher eventPublisher
    ) {
        this.couponRepository = couponRepository;
        this.eventPublisher = eventPublisher;
    }

    public void execute(
        Id id,
        Id discountId,
        CouponCode code,
        CouponVisibility visibility,
        LocalDateTime expiredAt
    ) {
        Coupon coupon = couponRepository.findById(id);

        coupon.update(
            discountId,
            code,
            visibility,
            expiredAt
        );

        if(!coupon.getEvents().isEmpty()) {
            eventPublisher.publish(coupon.getEvents());
        }
    }
}
