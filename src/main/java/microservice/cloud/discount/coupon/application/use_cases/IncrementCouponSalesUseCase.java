package microservice.cloud.discount.coupon.application.use_cases;

import microservice.cloud.discount.coupon.domain.entity.Coupon;
import microservice.cloud.discount.coupon.domain.repository.CouponRepository;
import microservice.cloud.discount.shared.application.ports.out.EventPublisher;
import microservice.cloud.discount.shared.domain.value_objects.Id;

public class IncrementCouponSalesUseCase {
    private final CouponRepository couponRepository;
    private final EventPublisher eventPublisher;

    public IncrementCouponSalesUseCase(CouponRepository couponRepository, EventPublisher eventPublisher) {
        this.couponRepository = couponRepository;
        this.eventPublisher = eventPublisher;
    }

    public void execute(Id id) {
        Coupon coupon = couponRepository.pessimisticUpdate(id, (c) -> {
            c.incrementSales();
        });

        if(!coupon.getEvents().isEmpty()) {
            eventPublisher.publish(coupon.getEvents());
        }
    }
}
