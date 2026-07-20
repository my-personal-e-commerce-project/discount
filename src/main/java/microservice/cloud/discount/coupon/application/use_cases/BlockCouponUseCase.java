package microservice.cloud.discount.coupon.application.use_cases;

import microservice.cloud.discount.coupon.domain.entity.Coupon;
import microservice.cloud.discount.coupon.domain.repository.CouponRepository;
import microservice.cloud.discount.shared.application.ports.out.EventPublisher;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public class BlockCouponUseCase {
    private final CouponRepository couponRepository;
    private final EventPublisher eventPublisher;

    public BlockCouponUseCase(CouponRepository couponRepository, EventPublisher eventPublisher) {
        this.couponRepository = couponRepository;
        this.eventPublisher = eventPublisher;
    }

    public void execute(Id id) {
        Coupon coupon = couponRepository.findById(id);

        coupon.block();

        couponRepository.update(coupon);

        if(!coupon.getEvents().isEmpty() && coupon.getEvents() != null) {
            eventPublisher.publish(coupon.getEvents());
        }
    }
}
